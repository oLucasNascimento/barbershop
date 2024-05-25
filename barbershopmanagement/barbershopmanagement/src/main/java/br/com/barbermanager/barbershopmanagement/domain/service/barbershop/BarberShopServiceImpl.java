package br.com.barbermanager.barbershopmanagement.domain.service.barbershop;

import br.com.barbermanager.barbershopmanagement.domain.model.BarberShop;
import br.com.barbermanager.barbershopmanagement.domain.model.Client;
import br.com.barbermanager.barbershopmanagement.domain.model.Employee;
import br.com.barbermanager.barbershopmanagement.domain.model.Item;
import br.com.barbermanager.barbershopmanagement.domain.repository.BarberShopRepository;
import br.com.barbermanager.barbershopmanagement.domain.service.employee.EmployeeService;
import br.com.barbermanager.barbershopmanagement.domain.service.item.ItemService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

@Service
public class BarberShopServiceImpl implements BarberShopService {

    @Autowired
    private BarberShopRepository barberShopRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ItemService itemService;

    @Override
    public Boolean barberShopExists(Integer barberShopId) {
        return this.barberShopRepository.existsById(barberShopId);
    }

    @Override
    public BarberShop createBarberShop(BarberShop newBarberShop) {
        if ((this.barberShopRepository.findByEmail(newBarberShop.getEmail())) == null) {
            return this.barberShopRepository.save(newBarberShop);
        }
        return null;
    }

    @Override
    public List<BarberShop> allBarberShops() {
        return this.barberShopRepository.findAll();
    }

    @Override
    public BarberShop barberShopById(Integer barberShopId) {
        if (this.barberShopRepository.existsById(barberShopId)) {
            return this.barberShopRepository.getById(barberShopId);
        }
        return null;
    }

    @Override
    public void deleteBarberShop(Integer barberShopId) {
        this.barberShopRepository.deleteById(barberShopId);
    }

    @Override
    @Transactional
    public BarberShop updateBarberShop(Integer barberShopId, BarberShop updatedBarberShop) {
        if ((this.barberShopExists(barberShopId))) {
            if (this.barberShopRepository.findByEmail(updatedBarberShop.getEmail()) == null) {
                BarberShop barberShop = this.barberShopById(barberShopId);
                if ((updatedBarberShop.getEmployees() != null)) {
                    barberShop = this.hireEmployee(barberShopId, updatedBarberShop.getEmployees());
                }
                if ((updatedBarberShop.getItems() != null)) {
                    barberShop = this.insertItem(barberShopId, updatedBarberShop.getItems());
                }
                BeanUtils.copyProperties(updatedBarberShop, barberShop, searchEmptyFields(updatedBarberShop));
                return this.barberShopRepository.save(barberShop);
            }
        }
        return null;
    }

    private BarberShop insertItem(Integer barberShopItem, List<Item> newItems) {
        BarberShop barberShop = this.barberShopById(barberShopItem);
        for (Item itemUpdt : newItems) {
            Item existingItem = this.itemService.itemById(itemUpdt.getItemId());
            existingItem.setBarberShop(barberShop);
            this.itemService.updateItem(existingItem.getItemId(), existingItem);
        }
        return barberShop;
    }

    @Override
    public BarberShop udpateClientAtBarberShop(Integer barberShopId, BarberShop updatedClients) {
        BarberShop barberShop = this.barberShopById(barberShopId);
        Set<Client> clients = barberShop.getClients();
        for (Client client : updatedClients.getClients()) {
            clients.add(client);
        }
        barberShop.setClients(clients);
        return this.barberShopRepository.save(barberShop);
    }

    @Override
    public void removeClient(Integer barberShopId, Integer clientId) {
        BarberShop barberShop = this.barberShopById(barberShopId);
        if ((barberShop.getClients() != null)) {
            Set<Client> clients = barberShop.getClients();
            Set<Client> removedClients = new HashSet<>();
            for (Client client : barberShop.getClients()) {
                if (client.getClientId().equals(clientId)) {
                    removedClients.add(client);
                }
            }
            clients.removeAll(removedClients);
            barberShop.setClients(clients);
            this.udpateClientAtBarberShop(barberShopId, barberShop);
        }
    }

    @Override
    public Boolean dismissEmployee(Integer barberShopId, Integer employeeId) {
        BarberShop barberShop = this.barberShopById(barberShopId);
        if ((barberShop.getEmployees() != null)) {
            Employee dismissedEmployee = this.employeeService.EmployeeById(employeeId);
            if (barberShop.getEmployees().contains(dismissedEmployee)) {
                dismissedEmployee.setBarberShop(null);
                this.employeeService.updateEmployee(employeeId, dismissedEmployee);
                return true;
            }
        }
        return false;
    }

    private BarberShop hireEmployee(Integer barberShopId, List<Employee> updatedEmployees) {
        BarberShop barberShop = this.barberShopById(barberShopId);
        for (Employee employeeUpdt : updatedEmployees) {
            Employee existingEmployee = this.employeeService.EmployeeById(employeeUpdt.getEmployeeId());
            existingEmployee.setBarberShop(barberShop);
            this.employeeService.updateEmployee(existingEmployee.getEmployeeId(), existingEmployee);
        }
        return barberShop;
    }

    private String[] searchEmptyFields(Object source) {
        BeanWrapper src = new BeanWrapperImpl(source);
        Set<String> emptyFields = new HashSet<>();
        for (PropertyDescriptor pd : src.getPropertyDescriptors()) {
            if (src.getPropertyValue(pd.getName()) == null) {
                emptyFields.add(pd.getName());
            }
        }
        String[] result = new String[emptyFields.size()];
        return emptyFields.toArray(result);
    }
}