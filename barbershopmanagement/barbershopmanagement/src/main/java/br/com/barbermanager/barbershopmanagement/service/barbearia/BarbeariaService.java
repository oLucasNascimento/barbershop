package br.com.barbermanager.barbershopmanagement.service.barbearia;

import br.com.barbermanager.barbershopmanagement.model.Barbearia;
import br.com.barbermanager.barbershopmanagement.model.Funcionario;

import java.util.List;
import java.util.Map;

public interface BarbeariaService {

    public Boolean barbeariaExiste(Integer id);
    public Barbearia criarBarbearia(Barbearia novaBarbearia);
    public List<Barbearia> todasBarbearias();
    public Boolean deletarBarbearia(Integer id);
    public Barbearia atualizarBarbearia(Integer id, Barbearia camposAtualizados);
    public Barbearia buscarBarbeariaPeloId(Integer id);

}
