package br.com.barbermanager.barbershopmanagement.controller;

import br.com.barbermanager.barbershopmanagement.model.Servico;
import br.com.barbermanager.barbershopmanagement.service.servico.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servico")
public class ServicoController {

    @Autowired
    private ServicoService servicoService;

    @PostMapping("/novo")
    public ResponseEntity<Servico> novoServico(@RequestBody Servico novoServico) {
        if ((this.servicoService.criarServico(novoServico)) != null) {
            return ResponseEntity.ok(novoServico);
        }
        return ResponseEntity.status(409).build();
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Servico>> todosServicos() {
        List<Servico> servicos = this.servicoService.todosServicos();
        if (servicos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(servicos);
    }

    @GetMapping("/barbearia/{idBarbearia}")
    public ResponseEntity<List<Servico>> servicosPorBarbearia(@PathVariable Integer idBarbearia){
        List<Servico> servicos = this.servicoService.servicosPorBarbearia(idBarbearia);
        if(servicos.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(servicos);
    }

    @DeleteMapping("/deletar/{idServico}")
    public ResponseEntity excluirServico(@PathVariable Integer idServico) {
        if (this.servicoService.deletarServico(idServico)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PatchMapping("/atualizar/{idServico}")
    public ResponseEntity<Servico> atualizarServico(@PathVariable Integer idServico, @RequestBody Servico servicoAtualizado) {
        if ((this.servicoService.atualizarServico(idServico, servicoAtualizado)) != null) {
            return ResponseEntity.ok(this.servicoService.buscarServicoPeloId(idServico));
        }
        return ResponseEntity.badRequest().build();
    }

}
