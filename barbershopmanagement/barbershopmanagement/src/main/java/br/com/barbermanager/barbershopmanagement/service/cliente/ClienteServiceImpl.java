package br.com.barbermanager.barbershopmanagement.service.cliente;

import br.com.barbermanager.barbershopmanagement.model.Barbearia;
import br.com.barbermanager.barbershopmanagement.model.Cliente;
import br.com.barbermanager.barbershopmanagement.repository.ClienteRepository;
import br.com.barbermanager.barbershopmanagement.service.barbearia.BarbeariaService;
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

    @Autowired
    private BarbeariaService barbeariaService;

    @Override
    public Boolean clienteExiste(Integer id) {
        return this.clienteRepository.existsById(id);
    }

    @Override
    public Cliente criarCliente(Cliente novoCliente) {
        if ((this.clienteRepository.findByCpf(novoCliente.getCpf())) == null) {
            return this.clienteRepository.save(novoCliente);
        }
        return null;
    }

    @Override
    public List<Cliente> todosClientes() {
        return this.clienteRepository.findAll();
    }

    @Override
    public Cliente buscarClientePeloId(Integer id) {
        if (this.clienteRepository.existsById(id)) {
            return this.clienteRepository.getById(id);

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
    public Cliente atualizarCliente(Integer id, Cliente camposAtualizados) {
        if (this.clienteExiste(id)) {
            Cliente cliente = this.buscarClientePeloId(id);
            if (this.clienteRepository.findByCpf(camposAtualizados.getCpf()) == null) {
                if (camposAtualizados.getBarbearias() != null) {
                    cliente = this.atualizarBarbearias(id, camposAtualizados);
                }
                BeanUtils.copyProperties(camposAtualizados, cliente, buscarCampoVazios(camposAtualizados));
                return this.clienteRepository.save(cliente);
            }
        }
        return null;
    }

    private Cliente atualizarBarbearias(Integer idCliente, Cliente clienteAtualizado) {
        Cliente cliente = this.buscarClientePeloId(idCliente);
        for (Barbearia barbeariaAtt : clienteAtualizado.getBarbearias()) {
            Set<Cliente> clientes = new HashSet<>();
            clientes.add(cliente);
            Barbearia barbearia = this.barbeariaService.buscarBarbeariaPeloId(barbeariaAtt.getId());
            barbearia.setClientes(clientes);
            this.barbeariaService.atualizarClienteNaBarbearia(barbearia.getId(), barbearia);
        }
        return cliente;
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
