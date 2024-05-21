package br.com.barbermanager.barbershopmanagement.service.servico;

import br.com.barbermanager.barbershopmanagement.model.Servico;

import java.util.List;

public interface ServicoService {

    public Boolean servicoExiste(Integer idServico);

    public Servico criarServico(Servico novoServico);

    public List<Servico> todosServicos();

    public Servico buscarServicoPeloId(Integer idServico);

    public List<Servico> servicosPorBarbearia(Integer idBarbearia);

    public Boolean deletarServico(Integer idServico);

    public Servico atualizarServico(Integer idServico, Servico servicoAtualizado);
}
