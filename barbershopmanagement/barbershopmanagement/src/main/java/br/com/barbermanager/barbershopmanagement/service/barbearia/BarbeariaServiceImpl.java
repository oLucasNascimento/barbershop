package br.com.barbermanager.barbershopmanagement.service.barbearia;

import br.com.barbermanager.barbershopmanagement.model.Barbearia;
import br.com.barbermanager.barbershopmanagement.repository.BarbeariaRepository;
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

    @Override
    public Boolean barbeariaExiste(Integer id) {
        return this.barbeariaRepository.existsById(id);
    }

    @Override
    public Barbearia criarBarbearia(Barbearia novaBarbearia) {

        for (Barbearia barbearia : this.barbeariaRepository.findAll()) {
            if (barbearia.getEmail().equals(novaBarbearia.getEmail())) {
                return null;
            }
        }
        return this.barbeariaRepository.save(novaBarbearia);
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
    public Boolean deletarBarbearia(Integer id) {
        if (this.barbeariaRepository.existsById(id)) {
            this.barbeariaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Barbearia atualizarBarbearia(Integer id, Barbearia camposAtualizados) {
        if (this.barbeariaExiste(id)) {
            Barbearia barbearia = this.buscarBarbeariaPeloId(id);
            BeanUtils.copyProperties(camposAtualizados, barbearia, buscarCampoVazios(camposAtualizados));
            return this.barbeariaRepository.save(barbearia);
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