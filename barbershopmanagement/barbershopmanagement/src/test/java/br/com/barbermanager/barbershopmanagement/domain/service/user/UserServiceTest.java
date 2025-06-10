package br.com.barbermanager.barbershopmanagement.domain.service.user;

import br.com.barbermanager.barbershopmanagement.domain.model.user.AuthenticationDTO;
import br.com.barbermanager.barbershopmanagement.domain.model.user.RegisterDTO;
import br.com.barbermanager.barbershopmanagement.domain.model.user.User;
import br.com.barbermanager.barbershopmanagement.domain.model.user.UserRole;
import br.com.barbermanager.barbershopmanagement.domain.repository.UserRepository;
import br.com.barbermanager.barbershopmanagement.exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private AuthenticationDTO authenticationDTO;

    @InjectMocks
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.startDomains();
    }

    @Test
    void whenDologinThenReturnAuthenticated() {
        Authentication authMock = Mockito.mock(Authentication.class);
        when(this.authenticationManager.authenticate(any())).thenReturn(authMock);
        Authentication auth = this.userService.login(this.authenticationDTO);
        assertEquals(authMock, auth);
    }

    @Test
    void whenTryloginWithLoginOrPasswordInvalidThenThrowAnBadRequestException() {
        when(this.authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Credentials Invalid"));
        try{
            this.userService.login(this.authenticationDTO);
            fail();
        } catch (Exception ex){
        assertEquals(BadRequestException.class, ex.getClass());
        assertEquals("Email/Username invalid.", ex.getMessage());
        }
    }

    @Test
    void whenRegisterThenReturnSuccess() {
        when(this.userRepository.findByLogin(any())).thenReturn(null);
        when(this.userRepository.save(any())).thenReturn(new User());
        assertTrue(this.userService.register(new RegisterDTO("login","password", UserRole.CLIENT)));
    }

    @Test
    void whenRegisterAnUserExistingThenThrowAnAkreadyExistsException() {
        when(this.userRepository.findByLogin(any())).thenReturn(null);
        when(this.userRepository.save(any())).thenReturn(new User());
        assertTrue(this.userService.register(new RegisterDTO("login","password", UserRole.CLIENT)));
    }

    void startDomains(){
        this.authenticationDTO = new AuthenticationDTO("login","password");
    }
}