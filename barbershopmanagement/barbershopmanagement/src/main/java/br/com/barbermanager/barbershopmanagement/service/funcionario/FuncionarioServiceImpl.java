package br.com.barbermanager.barbershopmanagement.service.funcionario;

import br.com.barbermanager.barbershopmanagement.model.Funcionario;
import br.com.barbermanager.barbershopmanagement.repository.FuncionarioRepository;
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
public class FuncionarioServiceImpl implements FuncionarioService{

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Override
    public Boolean funcionarioExiste(Integer id) {
        return this.funcionarioRepository.existsById(id);
    }

    @Override
    public Funcionario criarFuncionario(Funcionario novoFuncionario) {

        for(Funcionario funcionario : this.funcionarioRepository.findAll()){
            if(funcionario.getCpf().equals(novoFuncionario.getCpf())){
                return null;
            }
        }

        return this.funcionarioRepository.save(novoFuncionario);
    }

    @Override
    public List<Funcionario> todosFuncionarios() {
        return this.funcionarioRepository.findAll();
    }

    @Override
    public Funcionario buscarFuncionarioPeloId(Integer id) {
        if(this.funcionarioRepository.existsById(id)){
            return this.funcionarioRepository.getById(id);
        }
        return null;
    }

    @Override
    public List<Funcionario> funcionariosPorBarbearia(Integer id) {
        List<Funcionario> funcionarios = new ArrayList<>();
        for( Funcionario funcionario : this.funcionarioRepository.findAll()){
            if(funcionario.getBarbearia().getId().equals(id)){
                funcionarios.add(funcionario);
            }
        }
        return funcionarios;

    }

    @Override
    public Boolean deletarFuncionario(Integer id) {
        if(this.funcionarioRepository.existsById(id)){
            this.funcionarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Funcionario atualizarFuncionario(Integer id, Funcionario camposAtualizados) {
        if (this.funcionarioExiste(id)) {
            Funcionario funcionario = this.buscarFuncionarioPeloId(id);
            BeanUtils.copyProperties(camposAtualizados, funcionario, buscarCampoVazios(camposAtualizados));
            return this.funcionarioRepository.save(funcionario);
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
