package br.com.barbermanager.barbershopmanagement.repository;

import br.com.barbermanager.barbershopmanagement.model.Funcionario;
import br.com.barbermanager.barbershopmanagement.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico, Integer> {

    @Query("SELECT s FROM Servico s WHERE s.nome = :nome AND s.valor = :valor")
    Servico servicoExistente(String nome, Double valor);

    @Query("SELECT s FROM Servico s WHERE s.barbearia.id = :idBarbearia")
    List<Servico> serviceByBarber(Integer idBarbearia);

}
