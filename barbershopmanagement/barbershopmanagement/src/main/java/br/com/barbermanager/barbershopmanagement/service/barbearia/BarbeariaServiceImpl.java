package br.com.barbermanager.barbershopmanagement.service.barbearia;

import br.com.barbermanager.barbershopmanagement.model.Barbearia;
import br.com.barbermanager.barbershopmanagement.model.Cliente;
import br.com.barbermanager.barbershopmanagement.model.Funcionario;
import br.com.barbermanager.barbershopmanagement.model.Servico;
import br.com.barbermanager.barbershopmanagement.repository.BarbeariaRepository;
import br.com.barbermanager.barbershopmanagement.service.funcionario.FuncionarioService;
import br.com.barbermanager.barbershopmanagement.service.servico.ServicoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

@Service
public class BarbeariaServiceImpl implements BarbeariaService {

    @Autowired
    private BarbeariaRepository barbeariaRepository;

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private ServicoService servicoService;

    @Override
    public Boolean barbeariaExiste(Integer id) {
        return this.barbeariaRepository.existsById(id);
    }

    @Override
    public Barbearia criarBarbearia(Barbearia novaBarbearia) {
        if ((this.barbeariaRepository.findByEmail(novaBarbearia.getEmail())) == null) {
            return this.barbeariaRepository.save(novaBarbearia);
        }
        return null;
    }

    @Override
    public List<Barbearia> todasBarbearias() {
        return this.barbeariaRepository.findAll();
    }

    @Override
    public Barbearia buscarBarbeariaPeloId(Integer id) {
        if (this.barbeariaRepository.existsById(id)) {
            return this.barbeariaRepository.getById(id);
        }
        return null;
    }

    @Override
    public void deletarBarbearia(Integer id) {
        this.barbeariaRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Barbearia atualizarBarbearia(Integer id, Barbearia camposAtualizados) {
        if ((this.barbeariaExiste(id))) {
            if (this.barbeariaRepository.findByEmail(camposAtualizados.getEmail()) == null) {
                Barbearia barbearia = this.buscarBarbeariaPeloId(id);
                if ((camposAtualizados.getFuncionarios() != null)) {
                    barbearia = this.admitirFuncionario(id, camposAtualizados.getFuncionarios());
                }
                if ((camposAtualizados.getServicos() != null)) {
                    barbearia = this.inserirServico(id, camposAtualizados.getServicos());
                }
                BeanUtils.copyProperties(camposAtualizados, barbearia, buscarCampoVazios(camposAtualizados));
                return this.barbeariaRepository.save(barbearia);
            }
        }
        return null;
    }

    private Barbearia inserirServico(Integer idBarbearia, List<Servico> novosServicos) {
        Barbearia barbearia = this.buscarBarbeariaPeloId(idBarbearia);
        for (Servico servicoAtt : novosServicos) {
            Servico servicoExistente = this.servicoService.buscarServicoPeloId(servicoAtt.getId());
            servicoExistente.setBarbearia(barbearia);
            this.servicoService.atualizarServico(servicoExistente.getId(), servicoExistente);
        }
        return barbearia;
    }

    @Override
    public Barbearia atualizarClienteNaBarbearia(Integer id, Barbearia clientesAtualizados) {
        Barbearia barbearia = this.buscarBarbeariaPeloId(id);
        Set<Cliente> clientes = barbearia.getClientes();
        for (Cliente cliente : clientesAtualizados.getClientes()) {
            clientes.add(cliente);
        }
        barbearia.setClientes(clientes);
        return this.barbeariaRepository.save(barbearia);
    }

    @Override
    public void removerCliente(Integer idBarbearia, Integer idCliente) {
        Barbearia barbearia = this.buscarBarbeariaPeloId(idBarbearia);
        if ((barbearia.getClientes() != null)) {
            Set<Cliente> clientes = barbearia.getClientes();
            Set<Cliente> clientesRemovidos = new HashSet<>();
            for (Cliente cliente : barbearia.getClientes()) {
                if (cliente.getId().equals(idCliente)) {
                    clientesRemovidos.add(cliente);
                }
            }
            clientes.removeAll(clientesRemovidos);
            barbearia.setClientes(clientes);
            this.atualizarClienteNaBarbearia(idBarbearia, barbearia);
        }
    }

    @Override
    public Boolean demitirFuncionario(Integer idBarbearia, Integer idFuncionario) {
        Barbearia barbearia = this.buscarBarbeariaPeloId(idBarbearia);
        if ((barbearia.getFuncionarios() != null)) {
            Funcionario funcionarioDemitido = this.funcionarioService.buscarFuncionarioPeloId(idFuncionario);
            if (barbearia.getFuncionarios().contains(funcionarioDemitido)) {
                funcionarioDemitido.setBarbearia(null);
                this.funcionarioService.atualizarFuncionario(idFuncionario, funcionarioDemitido);
                return true;
            }
        }
        return false;
    }

    private Barbearia admitirFuncionario(Integer id, List<Funcionario> funcionariosAtualizados) {
        Barbearia barbearia = this.buscarBarbeariaPeloId(id);
        for (Funcionario funcionarioAtt : funcionariosAtualizados) {
            Funcionario funcionarioExistente = this.funcionarioService.buscarFuncionarioPeloId(funcionarioAtt.getId());
            funcionarioExistente.setBarbearia(barbearia);
            this.funcionarioService.atualizarFuncionario(funcionarioExistente.getId(), funcionarioExistente);
        }
        return barbearia;
    }

    private String[] buscarCampoVazios(Object source) {
        BeanWrapper src = new BeanWrapperImpl(source);
        Set<String> camposVazios = new HashSet<>();
        for (PropertyDescriptor pd : src.getPropertyDescriptors()) {
            if (src.getPropertyValue(pd.getName()) == null) {
                camposVazios.add(pd.getName());
            }
        }
        String[] resultado = new String[camposVazios.size()];
        return camposVazios.toArray(resultado);
    }
}