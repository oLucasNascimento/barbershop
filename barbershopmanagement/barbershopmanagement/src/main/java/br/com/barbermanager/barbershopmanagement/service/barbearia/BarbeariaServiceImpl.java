package br.com.barbermanager.barbershopmanagement.service.barbearia;

import br.com.barbermanager.barbershopmanagement.model.Barbearia;
import br.com.barbermanager.barbershopmanagement.model.Cliente;
import br.com.barbermanager.barbershopmanagement.model.Funcionario;
import br.com.barbermanager.barbershopmanagement.repository.BarbeariaRepository;
import br.com.barbermanager.barbershopmanagement.service.cliente.ClienteService;
import br.com.barbermanager.barbershopmanagement.service.funcionario.FuncionarioService;
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

//    @Autowired
//    private ClienteService clienteService;

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
    public void demitirFuncionario(Integer idBarbearia, Integer idFuncionario) {
        Barbearia barbearia = this.buscarBarbeariaPeloId(idBarbearia);
        if ((barbearia.getFuncionarios() != null)) {
            Funcionario funcionarioDemitido = this.funcionarioService.buscarFuncionarioPeloId(idFuncionario);
            funcionarioDemitido.setBarbearia(null);
            this.funcionarioService.atualizarFuncionario(idFuncionario, funcionarioDemitido);
        }
    }

    @Override
    @Transactional
    public Barbearia atualizarBarbearia(Integer id, Barbearia camposAtualizados) {
        if (this.barbeariaExiste(id)) {
            if (this.barbeariaRepository.findByEmail(camposAtualizados.getEmail()) == null) {
                Barbearia barbearia = this.buscarBarbeariaPeloId(id);
                if ((camposAtualizados.getFuncionarios() != null)) {
                    barbearia = this.admitirFuncionario(id, camposAtualizados.getFuncionarios());
                }
                BeanUtils.copyProperties(camposAtualizados, barbearia, buscarCampoVazios(camposAtualizados));
                return this.barbeariaRepository.save(barbearia);
            }
        }
        return null;
    }

    @Override
    public Barbearia inserirCliente(Integer id, Barbearia clientesAtualizados) {
        Barbearia barbearia = this.buscarBarbeariaPeloId(id);
        if ((clientesAtualizados.getClientes() != null)) {
            Set<Cliente> clientes = barbearia.getClientes();
            for (Cliente cliente : clientesAtualizados.getClientes()) {
                clientes.add(cliente);
            }
            barbearia.setClientes(clientes);
            return this.barbeariaRepository.save(barbearia);
        }
        return null;
    }

    @Override
    public void removerCliente(Integer idBarbearia, Integer idCliente) {
//        Barbearia barbearia = this.buscarBarbeariaPeloId(idBarbearia);
//        if ((barbearia.getClientes() != null)) {
//            Cliente clienteRemovido = this.clienteService.buscarClientePeloId(idCliente);
//            clienteRemovido.getBarbearias().remove(this.buscarBarbeariaPeloId(idBarbearia));
//            this.clienteService.atualizarCliente(idCliente, clienteRemovido);
//        }
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