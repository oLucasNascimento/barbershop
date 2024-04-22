package br.com.barbermanager.barbershopmanagement.repository;

import br.com.barbermanager.barbershopmanagement.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
