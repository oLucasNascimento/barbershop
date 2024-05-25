//package br.com.barbermanager.barbershopmanagement.domain.service.agendamento;
//
//import br.com.barbermanager.barbershopmanagement.model.Agendamento;
//import br.com.barbermanager.barbershopmanagement.repository.AgendamentoRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class AgendamentoServiceImpl implements AgendamentoService{
//
//    @Autowired
//    private AgendamentoRepository agendamentoRepository;
//
//    @Override
//    public Agendamento novoAgendamento(Agendamento novoAgendamento) {
//            return this.agendamentoRepository.save(novoAgendamento);
//    }
//
//    @Override
//    public List<Agendamento> todosAgendamentos() {
//        return this.agendamentoRepository.findAll();
//    }
//}
