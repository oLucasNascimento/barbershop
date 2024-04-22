package br.com.barbermanager.barbershopmanagement.repository;

import br.com.barbermanager.barbershopmanagement.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
}
