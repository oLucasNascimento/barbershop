package br.com.barbermanager.barbershopmanagement.service.cliente;

import br.com.barbermanager.barbershopmanagement.model.Cliente;
import br.com.barbermanager.barbershopmanagement.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Boolean clienteExiste(Integer id) {
        return this.clienteRepository.existsById(id);
    }

    @Override
    public Cliente criarCliente(Cliente novoFuncionario) {
        for (Cliente cliente : this.clienteRepository.findAll()) {
            if (cliente.getCpf().equals(novoFuncionario.getCpf())) {
                return null;
            }
        }
        return this.clienteRepository.save(novoFuncionario);
    }

    @Override
    public List<Cliente> todosClientes() {
        return this.clienteRepository.findAll();
    }

    @Override
    public Cliente buscarClientePeloId(Integer id) {
        if (this.clienteRepository.existsById(id)) {
            this.clienteRepository.getById(id);
        }
        return null;
    }

    @Override
    public Boolean deletarCliente(Integer id) {
        if (this.clienteRepository.existsById(id)) {
            this.clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Cliente atualizarCliente(Integer id,Cliente camposAtualizados) {
        if (this.clienteRepository.existsById(id)) {
            Cliente cliente = this.clienteRepository.getById(id);
            BeanUtils.copyProperties(camposAtualizados, cliente, buscarCampoVazios(camposAtualizados));
            return this.clienteRepository.save(cliente);
        }
        return null;
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
