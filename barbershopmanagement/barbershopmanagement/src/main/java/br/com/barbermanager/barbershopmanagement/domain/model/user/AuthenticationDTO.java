package br.com.barbermanager.barbershopmanagement.domain.model.user;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(
        @NotBlank(message = "{barber.login.not.blank}")
        String login,
        @NotBlank(message = "{barber.password.not.blank}")
        String password){
}
