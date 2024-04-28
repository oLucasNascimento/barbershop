package br.com.barbermanager.barbershopmanagement.service.funcionario;

import br.com.barbermanager.barbershopmanagement.model.Barbearia;
import br.com.barbermanager.barbershopmanagement.model.Funcionario;

import java.util.List;

public interface FuncionarioService {

    public Boolean funcionarioExiste(Integer id);

    public Funcionario criarFuncionario(Funcionario novoFuncionario);

    public List<Funcionario> todosFuncionarios();

    public Funcionario buscarFuncionarioPeloId(Integer id);

    public List<Funcionario> funcionariosPorBarbearia(Integer id);

    public Boolean deletarFuncionario(Integer id);

    public Funcionario atualizarFuncionario(Integer id, Funcionario camposAtualizados);

}
