package br.com.barbermanager.barbershopmanagement.controller;

import br.com.barbermanager.barbershopmanagement.model.Barbearia;
import br.com.barbermanager.barbershopmanagement.model.Cliente;
import br.com.barbermanager.barbershopmanagement.service.cliente.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/novo")
    public ResponseEntity<Cliente> novoCliente(@RequestBody Cliente novoCliente) {
        if ((this.clienteService.criarCliente(novoCliente)) != null) {
            return ResponseEntity.ok(novoCliente);
        }
        return ResponseEntity.status(409).build();
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Cliente>> todosClientes() {
        List<Cliente> clientes = this.clienteService.todosClientes();
        if (clientes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clientes);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity excluirCliente(@PathVariable Integer id) {
        if (this.clienteService.deletarCliente(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PatchMapping("/atualizar/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable Integer id, @RequestBody Cliente clienteAtualizado) {
        if ((this.clienteService.atualizarCliente(id, clienteAtualizado)) != null) {
            return ResponseEntity.ok(this.clienteService.buscarClientePeloId(id));
        }
        return ResponseEntity.badRequest().build();
    }
}