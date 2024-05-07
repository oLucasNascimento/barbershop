package br.com.barbermanager.barbershopmanagement.repository;

import br.com.barbermanager.barbershopmanagement.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    @Query("SELECT c FROM Cliente c WHERE c.cpf = :cpf")
    Cliente findByCpf(String cpf);

}
