package br.com.barbermanager.barbershopmanagement.domain.repository;

import br.com.barbermanager.barbershopmanagement.api.request.scheduling.SchedulingRequest;
import br.com.barbermanager.barbershopmanagement.domain.model.Scheduling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SchedulingRepository extends JpaRepository<Scheduling, Integer> {

    @Query("SELECT s FROM Scheduling s WHERE s.barberShop.barberShopId = :barberShopId AND s.employee.employeeId = :employeeId AND s.schedulingTime = :schedulingTime")
    List<Scheduling> schedulingExists(Integer barberShopId, Integer employeeId, LocalDateTime schedulingTime);

}