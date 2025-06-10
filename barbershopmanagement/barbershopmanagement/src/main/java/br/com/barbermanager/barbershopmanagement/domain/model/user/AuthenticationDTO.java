package br.com.barbermanager.barbershopmanagement.domain.model.user;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(
        @NotBlank(message = "Um Login precisa ser informado.")
        String login,
        @NotBlank(message = "Uma Senha precisa ser informada.")
        String password){
}
