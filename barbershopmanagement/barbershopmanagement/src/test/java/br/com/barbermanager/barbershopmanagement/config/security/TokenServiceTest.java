package br.com.barbermanager.barbershopmanagement.config.security;

import br.com.barbermanager.barbershopmanagement.domain.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenServiceTest {

    private String token;
    private TokenService tokenService = new TokenService();

    private User user = new User();

    @BeforeEach
    void setUp() {
        this.startUserAndToken();
    }

    @Test
    void whenGenerateTokenThenReturnAnToken() {
        String token = this.tokenService.generateToken(this.user);
        assertNotNull(token);
        assertFalse(this.tokenService.validateToken(this.token).equals("null"));
    }

    @Test
    void whenGenerateTokenThenThrowAnRuntimeException() {
        try {
            this.tokenService.setSecret(null);
            this.tokenService.generateToken(this.user);
            fail();
        } catch (Exception ex) {
            assertEquals(RuntimeException.class, ex.getClass());
            assertEquals("Error while generation token", ex.getMessage());
        }
    }

    @Test
    void whenValidateTokenThenReturnTrue() {
        boolean valid = !(this.tokenService.validateToken(this.token).equals("null"));
        assertTrue(valid);
    }

    @Test
    void whenValidateTokenThenReturnFalse() {
        this.tokenService.setSecret("secret-error");
        boolean valid = this.tokenService.validateToken(this.token).equals("null");
        assertTrue(valid);
    }

    private void startUserAndToken() {
        user.setLogin("login");
        user.setPassword("password");
        this.tokenService.setSecret("secret-tests");
        this.token = this.tokenService.generateToken(this.user);
    }

}