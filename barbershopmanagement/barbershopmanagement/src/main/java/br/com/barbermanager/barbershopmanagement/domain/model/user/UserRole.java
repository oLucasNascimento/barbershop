package br.com.barbermanager.barbershopmanagement.domain.model.user;

public enum UserRole {

    BARBERSHOP("barber_shop"),
    CLIENT("client"),
    EMPLOYEE("employee");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole(){
        return this.role;
    }

}
