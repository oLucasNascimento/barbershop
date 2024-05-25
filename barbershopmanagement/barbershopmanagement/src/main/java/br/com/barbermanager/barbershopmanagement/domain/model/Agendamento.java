//package br.com.barbermanager.barbershopmanagement.model;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import jakarta.persistence.*;
//
//import java.io.Serializable;
//
//@Entity
//public class Agendamento implements Serializable {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer idAgendamento;
//
//    @ManyToOne
//    @JoinColumn(name = "fk_cliente")
//    @JsonIgnoreProperties("agendamentos")
//    private Client cliente;
//
//    @ManyToOne
//    @JoinColumn(name = "fk_barbearia")
//    @JsonIgnoreProperties("agendamentos")
//    private BarberShop barbearia;
//
//    @ManyToOne
//    @JoinColumn(name = "fk_funcionario")
//    @JsonIgnoreProperties("agendamentos")
//    private Employee funcionario;
//
//    @ManyToOne
//    @JoinColumn(name = "fk_servico")
//    @JsonIgnoreProperties("agendamentos")
//    private Item servico;
//
////    private String hora;
//
//    public Client getCliente() {
//        return cliente;
//    }
//
//    public void setCliente(Client cliente) {
//        this.cliente = cliente;
//    }
//
//    public BarberShop getBarbearia() {
//        return barbearia;
//    }
//
//    public void setBarbearia(BarberShop barbearia) {
//        this.barbearia = barbearia;
//    }
//
//    public Employee getFuncionario() {
//        return funcionario;
//    }
//
//    public void setFuncionario(Employee funcionario) {
//        this.funcionario = funcionario;
//    }
//
//    public Item getServico() {
//        return servico;
//    }
//
//    public void setServico(Item servico) {
//        this.servico = servico;
//    }
////
////    public String getHora() {
////        return hora;
////    }
////
////    public void setHora(String hora) {
////        this.hora = hora;
////    }
//}
