package br.com.barbermanager.barbershopmanagement.domain.service.authorization;

import br.com.barbermanager.barbershopmanagement.domain.model.user.User;
import br.com.barbermanager.barbershopmanagement.domain.model.user.UserRole;
import br.com.barbermanager.barbershopmanagement.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;

class AuthorizationServiceTest {

    private User user = new User("lucas","senha", UserRole.ADMIN);

    @InjectMocks
    private AuthorizationService authorizationService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenLoadByUsernameThenReturnAnUsername(){
        when(this.userRepository.findByLogin(any())).thenReturn(this.user);

        User userVerify = (User) this.authorizationService.loadUserByUsername("lucas");

        assertEquals(userVerify.getUsername(), this.user.getUsername());
    }

}