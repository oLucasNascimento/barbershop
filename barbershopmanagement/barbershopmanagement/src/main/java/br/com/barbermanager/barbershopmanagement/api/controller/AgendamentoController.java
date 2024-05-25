//package br.com.barbermanager.barbershopmanagement.controller;
//
//import br.com.barbermanager.barbershopmanagement.model.Agendamento;
//import br.com.barbermanager.barbershopmanagement.service.agendamento.AgendamentoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/agendamento")
//public class AgendamentoController {
//
//    @Autowired
//    private AgendamentoService agendamentoService;
//
//    @PostMapping("/novo")
//    public ResponseEntity<Agendamento> novoAgendamento(@RequestBody Agendamento novoAgendamento) {
//        if ((this.agendamentoService.novoAgendamento(novoAgendamento)) != null) {
//            return ResponseEntity.ok(novoAgendamento);
//        }
//        return ResponseEntity.status(409).build();
//
//    }
//
//    @GetMapping("/todos")
//    public ResponseEntity<List<Agendamento>> todosAgendamentos(){
//        List<Agendamento> agendamentos = this.agendamentoService.todosAgendamentos();
//        if(!(agendamentos.isEmpty())){
//            return ResponseEntity.ok(agendamentos);
//        }
//        return ResponseEntity.notFound().build();
//    }
//
//
//}
