package br.com.barbermanager.barbershopmanagement.service.cliente;

import br.com.barbermanager.barbershopmanagement.model.Cliente;

import java.util.List;

public interface ClienteService {

    public Boolean clienteExiste(Integer id);

    public Cliente criarCliente(Cliente novoCliente);

    public List<Cliente> todosClientes();

    public Cliente buscarClientePeloId(Integer id);

    public Boolean deletarCliente(Integer id);

    public Cliente atualizarCliente(Integer id, Cliente camposAtualizados);

}
