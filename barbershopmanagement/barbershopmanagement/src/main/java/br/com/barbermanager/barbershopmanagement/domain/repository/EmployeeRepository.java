package br.com.barbermanager.barbershopmanagement.domain.repository;

import br.com.barbermanager.barbershopmanagement.domain.model.Employee;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query("SELECT e FROM Employee e WHERE e.cpf = :cpf")
    Employee findByCpf(String cpf);

    @Query("SELECT e FROM Employee e WHERE e.status = :status")
    List<Employee> findEmployeesByStatus(StatusEnum status);

    @Query("SELECT e FROM Employee e WHERE e.employeeId = :employeeId")
    Employee getEmployeeById(Integer employeeId);

    @Query("SELECT e FROM Employee e WHERE e.barberShop.barberShopId = :barberShopId")
    List<Employee> findEmployeesByBarberShop(Integer barberShopId);

}
