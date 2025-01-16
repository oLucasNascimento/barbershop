package br.com.barbermanager.barbershopmanagement.domain.model.user;

public record RegisterDTO(String login, String password, UserRole role) {
}
