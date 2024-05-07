package br.com.barbermanager.barbershopmanagement.controller;

import br.com.barbermanager.barbershopmanagement.model.Barbearia;
import br.com.barbermanager.barbershopmanagement.model.Funcionario;
import br.com.barbermanager.barbershopmanagement.service.barbearia.BarbeariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/barbearia")
public class BarbeariaController {

    @Autowired
    private BarbeariaService barbeariaService;

    @PostMapping("/nova")
    public ResponseEntity<Barbearia> novaBarbearia(@RequestBody Barbearia novaBarbearia) {
        if ((this.barbeariaService.criarBarbearia(novaBarbearia)) != null) {
            return ResponseEntity.ok(novaBarbearia);
        }
        return ResponseEntity.status(409).build();
    }

    @GetMapping("/todas")
    public ResponseEntity<List<Barbearia>> todasBarbearias() {
        List<Barbearia> barbearias = this.barbeariaService.todasBarbearias();
        if (barbearias.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(barbearias);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity excluirBarbearia(@PathVariable Integer id) {
        if ((this.barbeariaService.barbeariaExiste(id))) {
            this.barbeariaService.deletarBarbearia(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/demitir/{idBarbearia}/{idFuncionario}")
    public ResponseEntity demitirFuncionario(@PathVariable Integer idBarbearia, @PathVariable Integer idFuncionario) {
        if ((this.barbeariaService.barbeariaExiste(idBarbearia))) {
            this.barbeariaService.demitirFuncionario(idBarbearia, idFuncionario);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/atualizar/{id}")
    public ResponseEntity<Barbearia> atualizarBarbearia(@PathVariable Integer id, @RequestBody Barbearia barbeariaAtualizada) {
        if ((this.barbeariaService.atualizarBarbearia(id, barbeariaAtualizada)) != null) {
            return ResponseEntity.ok(this.barbeariaService.buscarBarbeariaPeloId(id));
        }
        return ResponseEntity.badRequest().build();
    }

    @PatchMapping("/inserir-cliente/{id}")
    public ResponseEntity<Barbearia> inserirNovoCliente(@PathVariable Integer id, @RequestBody Barbearia barbeariaAtualizada) {
        if ((this.barbeariaService.inserirCliente(id, barbeariaAtualizada)) != null) {
            return ResponseEntity.ok(this.barbeariaService.buscarBarbeariaPeloId(id));
        }
        return ResponseEntity.badRequest().build();
    }

}
