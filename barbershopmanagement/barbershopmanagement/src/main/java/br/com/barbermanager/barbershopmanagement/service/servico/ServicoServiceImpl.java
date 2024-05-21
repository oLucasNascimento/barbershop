package br.com.barbermanager.barbershopmanagement.service.servico;

import br.com.barbermanager.barbershopmanagement.model.Funcionario;
import br.com.barbermanager.barbershopmanagement.model.Servico;
import br.com.barbermanager.barbershopmanagement.repository.ServicoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ServicoServiceImpl implements ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    @Override
    public Boolean servicoExiste(Integer idServico) {
        return this.servicoRepository.existsById(idServico);
    }

    @Transactional
    @Override
    public Servico criarServico(Servico novoServico) {

        if((servicoRepository.servicoExistente(novoServico.getNome(), novoServico.getValor())) == null){
            return this.servicoRepository.save(novoServico);
        }
        return null;
    }

    @Override
    public List<Servico> todosServicos() {
        return this.servicoRepository.findAll();
    }

    @Override
    public Servico buscarServicoPeloId(Integer idServico) {
        if (this.servicoRepository.existsById(idServico)) {
            return this.servicoRepository.getById(idServico);
        }
        return null;
    }

    @Override
    public List<Servico> servicosPorBarbearia(Integer idBarbearia) {
        return this.servicoRepository.serviceByBarber(idBarbearia);
    }

    @Override
    public Boolean deletarServico(Integer idServico) {
        if (this.servicoRepository.existsById(idServico)) {
            this.servicoRepository.deleteById(idServico);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Servico atualizarServico(Integer idServico, Servico servicoAtualizado) {
        if (this.servicoExiste(idServico)) {
            Servico servico = this.buscarServicoPeloId(idServico);
            BeanUtils.copyProperties(servicoAtualizado, servico, buscarCampoVazios(servicoAtualizado));
            return this.servicoRepository.save(servico);
        }
        return null;
    }

    private String[] buscarCampoVazios(Servico source) {
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
