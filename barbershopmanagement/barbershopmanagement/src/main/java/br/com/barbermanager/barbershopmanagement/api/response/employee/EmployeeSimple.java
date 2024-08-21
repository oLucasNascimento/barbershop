package br.com.barbermanager.barbershopmanagement.api.response.employee;


import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;

public class EmployeeSimple {

    private Integer employeeId;
    private String name;
    private String phone;
    private StatusEnum status;
    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
