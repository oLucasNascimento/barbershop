package br.com.barbermanager.barbershopmanagement.api.controller;

import br.com.barbermanager.barbershopmanagement.config.security.TokenService;
import br.com.barbermanager.barbershopmanagement.domain.model.user.AuthenticationDTO;
import br.com.barbermanager.barbershopmanagement.domain.model.user.User;
import br.com.barbermanager.barbershopmanagement.domain.service.user.UserService;
import br.com.barbermanager.barbershopmanagement.exception.handler.RestErrorMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    private User user = new User();
    private AuthenticationDTO authenticationDTO;
    private UsernamePasswordAuthenticationToken auth;


    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private AuthenticationController authenticationController;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private UserService userService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.startUser();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void whenValidLoginThenReturnAnToken() throws Exception {
        when(this.userService.login(any())).thenReturn(this.auth);
        when(this.tokenService.generateToken(any())).thenReturn("fake-token");
        String userJson = this.objectMapper.writeValueAsString(this.authenticationDTO);
        String responseContent = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();


        assertNotNull(responseContent);
        verify(this.userService, times(1)).login(any());
    }

    @Test
    void whenTryValidAcessWithoutLoginThenThrowAnBadRequestException() throws Exception {
        this.authenticationDTO = new AuthenticationDTO(null, "password");
        String userJson = this.objectMapper.writeValueAsString(this.authenticationDTO);
        String responseContent = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        RestErrorMessage response = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("Um Login precisa ser informado.", response.getMessage());
        assertEquals("VALIDATION_ERROR", response.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals("/auth/login", response.getPath());
        verify(this.userService, times(0)).login(any());
    }

    @Test
    void whenTryValidAcessWithoutPasswordThenThrowAnBadRequestException() throws Exception {
        this.authenticationDTO = new AuthenticationDTO("login", null);
        String userJson = this.objectMapper.writeValueAsString(this.authenticationDTO);
        String responseContent = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        RestErrorMessage response = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("Uma Senha precisa ser informada.", response.getMessage());
        assertEquals("VALIDATION_ERROR", response.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals("/auth/login", response.getPath());
        verify(this.userService, times(0)).login(any());
    }

    void startUser() {
        this.authenticationDTO = new AuthenticationDTO("login", "password");
        user.setLogin("user");
        this.auth = new UsernamePasswordAuthenticationToken(this.user, null);
    }
}