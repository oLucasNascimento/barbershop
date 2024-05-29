package br.com.barbermanager.barbershopmanagement.domain.service.barbershop;

import br.com.barbermanager.barbershopmanagement.api.mapper.BarberShopMapper;
import br.com.barbermanager.barbershopmanagement.api.mapper.EmployeeMapper;
import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.api.response.barber.BarberShopResponse;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeResponse;
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

    @Autowired
    private BarberShopMapper barberShopMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Boolean barberShopExists(Integer barberShopId) {
        return this.barberShopRepository.existsById(barberShopId);
    }

    @Override
    public BarberShopResponse createBarberShop(BarberShopRequest newBarberShop) {
        if ((this.barberShopRepository.findByEmail(newBarberShop.getEmail())) == null) {
            return this.barberShopMapper.toBarberShopResponse((this.barberShopRepository.save((this.barberShopMapper.toBarberShop(newBarberShop)))));
        }
        return null;
    }

    @Override
    public List<BarberShopResponse> allBarberShops() {
        return this.barberShopMapper.toBarberShopResponseList((this.barberShopRepository.findAll()));
    }

    @Override
    public BarberShopResponse barberShopById(Integer barberShopId) {
        if (this.barberShopRepository.existsById(barberShopId)) {
            return this.barberShopMapper.toBarberShopResponse((this.barberShopRepository.getById(barberShopId)));
        }
        return null;
    }

    @Override
    public void deleteBarberShop(Integer barberShopId) {
        this.barberShopRepository.deleteById(barberShopId);
    }

    @Override
    @Transactional
    public BarberShopResponse updateBarberShop(Integer barberShopId, BarberShopRequest updatedBarberShop) {
        if ((this.barberShopExists(barberShopId))) {
            if (this.barberShopRepository.findByEmail(updatedBarberShop.getEmail()) == null) {
                BarberShop barberShop = this.barberShopRepository.getById(barberShopId);
                BarberShop barberShopUpdt = this.barberShopMapper.toBarberShop(updatedBarberShop);
                if ((barberShopUpdt.getEmployees() != null)) {
                    barberShop = this.hireEmployee(barberShopId, barberShopUpdt.getEmployees());
                }
                if ((barberShopUpdt.getItems() != null)) {
                    barberShop = this.insertItem(barberShopId, barberShopUpdt.getItems());
                }
                BeanUtils.copyProperties(barberShopUpdt, barberShop, searchEmptyFields(barberShopUpdt));
                BarberShopResponse barberShopResponse = this.barberShopMapper.toBarberShopResponse((this.barberShopRepository.save(barberShop)));
                return barberShopResponse;
            }
        }
        return null;
    }

    private BarberShop insertItem(Integer barberShopItem, List<Item> newItems) {
//        BarberShop barberShop = this.barberShopById(barberShopItem);
        BarberShop barberShop = this.barberShopRepository.getById(barberShopItem);
        for (Item itemUpdt : newItems) {
            Item existingItem = this.itemService.itemById(itemUpdt.getItemId());
            existingItem.setBarberShop(barberShop);
            this.itemService.updateItem(existingItem.getItemId(), existingItem);
        }
        return barberShop;
    }

    @Override
    public BarberShopResponse udpateClientAtBarberShop(Integer barberShopId, BarberShopRequest updatedClients) {
//        BarberShop barberShop = this.barberShopById(barberShopId);
        BarberShop barberShop = this.barberShopRepository.getById(barberShopId);
        BarberShop barberShopUpdt = this.barberShopMapper.toBarberShop(updatedClients);
        Set<Client> clients = barberShop.getClients();
        for (Client client : barberShopUpdt.getClients()) {
            clients.add(client);
        }
        barberShop.setClients(clients);
        return this.barberShopMapper.toBarberShopResponse((this.barberShopRepository.save(barberShop)));
    }

    @Override
    public void removeClient(Integer barberShopId, Integer clientId) {
//        BarberShop barberShop = this.barberShopById(barberShopId);
        BarberShop barberShop = this.barberShopRepository.getById(barberShopId);
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
            this.udpateClientAtBarberShop(barberShopId, (this.barberShopMapper.toBarberShopRequest(barberShop)));
        }
    }

    @Override
    public Boolean dismissEmployee(Integer barberShopId, Integer employeeId) {
//        BarberShop barberShop = this.barberShopById(barberShopId);
        BarberShop barberShop = this.barberShopRepository.getById(barberShopId);
        if ((barberShop.getEmployees() != null)) {
            EmployeeResponse dismissedEmployee = this.employeeService.EmployeeById(employeeId);
            if (barberShop.getEmployees().contains(dismissedEmployee)) {
                dismissedEmployee.setBarberShop(null);
                this.employeeService.updateEmployee(employeeId, (this.employeeMapper.toEmployeeRequest(dismissedEmployee)));
                return true;
            }
        }
        return false;
    }

    private BarberShop hireEmployee(Integer barberShopId, List<Employee> updatedEmployees) {
//        BarberShop barberShop = this.barberShopById(barberShopId);
        BarberShopResponse barberShop = this.barberShopById(barberShopId);
        for (Employee employeeUpdt : updatedEmployees) {
            EmployeeResponse existingEmployee = this.employeeService.EmployeeById(employeeUpdt.getEmployeeId());
            existingEmployee.setBarberShop(this.barberShopMapper.toBarberShopSimple(barberShop));
            this.employeeService.updateEmployee(existingEmployee.getEmployeeId(), (this.employeeMapper.toEmployeeRequest(existingEmployee)));
        }
        return this.barberShopMapper.toBarberShop(barberShop);
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