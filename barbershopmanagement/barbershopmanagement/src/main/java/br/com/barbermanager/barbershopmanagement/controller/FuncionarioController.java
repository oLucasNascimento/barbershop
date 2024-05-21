package br.com.barbermanager.barbershopmanagement.controller;


import br.com.barbermanager.barbershopmanagement.model.Funcionario;
import br.com.barbermanager.barbershopmanagement.service.funcionario.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping("/novo")
    public ResponseEntity<Funcionario> novoFuncionario(@RequestBody Funcionario novoFuncionario) {
        if ((this.funcionarioService.criarFuncionario(novoFuncionario)) != null) {
            return ResponseEntity.ok(novoFuncionario);
        }
        return ResponseEntity.status(409).build();
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Funcionario>> todosFuncionarios() {
        List<Funcionario> funcionarios = this.funcionarioService.todosFuncionarios();
        if (funcionarios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(funcionarios);
    }

    @GetMapping("/barbearia/{id}")
    public ResponseEntity<List<Funcionario>> funcionariosPorBarbearia(@PathVariable Integer id){
        List<Funcionario> funcionarios = this.funcionarioService.funcionariosPorBarbearia(id);
        if(funcionarios.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(funcionarios);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity excluirFuncionario(@PathVariable Integer id) {
        if (this.funcionarioService.deletarFuncionario(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PatchMapping("/atualizar/{id}")
    public ResponseEntity<Funcionario> atualizarFuncionario(@PathVariable Integer id, @RequestBody Funcionario funcionaroAtualizado) {
        if ((this.funcionarioService.atualizarFuncionario(id, funcionaroAtualizado)) != null) {
            return ResponseEntity.ok(this.funcionarioService.buscarFuncionarioPeloId(id));
        }
        return ResponseEntity.badRequest().build();
    }

}
