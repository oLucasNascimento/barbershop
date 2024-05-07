package br.com.barbermanager.barbershopmanagement.repository;

import br.com.barbermanager.barbershopmanagement.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

    @Query("SELECT f FROM Funcionario f WHERE f.cpf = :cpf")
    Funcionario findByCpf(String cpf);

}
