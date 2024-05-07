package br.com.barbermanager.barbershopmanagement.repository;

import br.com.barbermanager.barbershopmanagement.model.Barbearia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BarbeariaRepository extends JpaRepository<Barbearia, Integer> {

    @Query("SELECT b FROM Barbearia b WHERE b.email = :email")
    Barbearia findByEmail(String email);

}
