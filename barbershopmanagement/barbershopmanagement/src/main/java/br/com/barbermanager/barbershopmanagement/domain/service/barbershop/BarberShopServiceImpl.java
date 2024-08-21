package br.com.barbermanager.barbershopmanagement.domain.service.barbershop;

import br.com.barbermanager.barbershopmanagement.api.mapper.BarberShopMapper;
import br.com.barbermanager.barbershopmanagement.api.mapper.EmployeeMapper;
import br.com.barbermanager.barbershopmanagement.api.mapper.ItemMapper;
import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopResponse;
import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopSimple;
import br.com.barbermanager.barbershopmanagement.domain.model.*;
import br.com.barbermanager.barbershopmanagement.domain.repository.BarberShopRepository;
import br.com.barbermanager.barbershopmanagement.domain.service.employee.EmployeeService;
import br.com.barbermanager.barbershopmanagement.domain.service.item.ItemService;
import br.com.barbermanager.barbershopmanagement.exception.AlreadyExistsException;
import br.com.barbermanager.barbershopmanagement.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
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

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public Boolean barberShopExists(Integer barberShopId) {
        return this.barberShopRepository.existsById(barberShopId);
    }

    @Override
    public BarberShopResponse createBarberShop(BarberShopRequest newBarberShop) {
        if ((this.barberShopRepository.findByEmail(newBarberShop.getEmail())) == null) {
            return this.barberShopMapper.toBarberShopResponse((this.barberShopRepository.save((this.barberShopMapper.toBarberShop(newBarberShop)))));
        }
        throw new AlreadyExistsException("Barber Shop with email '" + newBarberShop.getEmail() + "' already exists.");
    }

    @Override
    public List<BarberShopSimple> allBarberShops() {
        List<BarberShopSimple> barbershops = this.barberShopMapper.toBarberShopSimpleList((this.barberShopRepository.findAll()));
        if (barbershops.isEmpty()) {
            throw new NotFoundException("There aren't barbershops to show");
        }
        return barbershops;
    }

    @Override
    public BarberShopResponse barberShopById(Integer barberShopId) {
        if (this.barberShopRepository.existsById(barberShopId)) {
            return this.barberShopMapper.toBarberShopResponse((this.barberShopRepository.getById(barberShopId)));
        }
        throw new NotFoundException("Barber Shop with ID '" + barberShopId + "' not found.");
    }

    @Override
    public void deleteBarberShop(Integer barberShopId) {
        if ((this.barberShopExists(barberShopId))) {
            this.barberShopRepository.deleteById(barberShopId);
        }
        throw new NotFoundException("Barber Shop with ID '" + barberShopId + "' not found.");
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
                BeanUtils.copyProperties((this.barberShopMapper.toBarberShop(updatedBarberShop)), barberShop, this.searchEmptyFields(updatedBarberShop));
                this.barberShopMapper.toBarberShopResponse((this.barberShopRepository.save(barberShop)));
                return this.barberShopMapper.toBarberShopResponse((this.barberShopRepository.getById(barberShopId)));
            }
            throw new AlreadyExistsException("Barber Shop with email '" + updatedBarberShop.getEmail() + "' already exists.");
        }
        throw new NotFoundException("Barber Shop with ID '" + barberShopId + "' not found.");
    }

    private BarberShop insertItem(Integer barberShopId, List<Item> newItems) {
        BarberShop barberShop = this.barberShopRepository.getById(barberShopId);
        for (Item itemUpdt : newItems) {
            Item existingItem = this.itemMapper.toItem(this.itemService.itemById(itemUpdt.getItemId()));
            existingItem.setBarberShop(barberShop);
            this.itemService.updateItem(existingItem.getItemId(), (this.itemMapper.toItemRequest(existingItem)));
        }
        return barberShop;
    }

    @Override
    public BarberShopResponse udpateClientAtBarberShop(Integer barberShopId, BarberShopRequest updatedClients) {
        if (this.barberShopExists(barberShopId)) {
            BarberShop barberShop = this.barberShopRepository.getById(barberShopId);
            BarberShop barberShopUpdt = this.barberShopMapper.toBarberShop(updatedClients);
            List<Client> clients = barberShop.getClients();
            for (Client client : barberShopUpdt.getClients()) {
                if (!(clients.stream().anyMatch(existingClient -> existingClient.getClientId().equals(client.getClientId())))) {
                    clients.add(client);
                }
            }
            barberShop.setClients(clients);
            return this.barberShopMapper.toBarberShopResponse((this.barberShopRepository.save(barberShop)));
        }
        throw new NotFoundException("Barber Shop with ID '" + barberShopId + "' not found.");
    }

    @Override
    public void removeClient(Integer barberShopId, Integer clientId) {
        if ((this.barberShopExists(barberShopId))) {
            BarberShop barberShop = this.barberShopRepository.getById(barberShopId);
            if ((barberShop.getClients() != null)) {
                List<Client> clients = barberShop.getClients();
                List<Client> removedClients = new ArrayList<>();

                for (Client client : barberShop.getClients()) {
                    if (client.getClientId().equals(clientId)) {
                        removedClients.add(client);
                    }
                }
                clients.removeAll(removedClients);
                barberShop.setClients(clients);
                this.udpateClientAtBarberShop(barberShopId, (this.barberShopMapper.toBarberShopRequest(barberShop)));
                if (removedClients.isEmpty()) {
                    throw new NotFoundException("Client with ID '" + clientId + "' doesn't belong at the barbershop with ID '" + barberShopId + "'.");
                }
                return;
            }
            throw new NotFoundException("Barber Shop hasn't clients to be removed.");
        }
        throw new NotFoundException("Barber Shop with ID '" + barberShopId + "' not found.");
    }

    @Override
    public void dismissEmployee(Integer barberShopId, Integer employeeId) {
        if (this.barberShopExists(barberShopId)) {
            BarberShop barberShop = this.barberShopRepository.getById(barberShopId);
            if (!(barberShop.getEmployees().isEmpty())) {
                Employee dismissedEmployee = this.employeeMapper.toEmployee(this.employeeService.employeeById(employeeId));
                for (Employee employees : barberShop.getEmployees()) {
                    if (employees.getEmployeeId().equals(dismissedEmployee.getEmployeeId())) {
                        this.employeeService.removeBarberShop(employeeId);
                        return;
                    }
                }
                throw new NotFoundException("Employee with ID '" + employeeId + "' was not found at the barbershop.");
            }
            throw new NotFoundException("Theren't employees at the barbershop.");
        }
        throw new NotFoundException("Barber Shop with ID '" + barberShopId + "' not found.");
    }

    private BarberShop hireEmployee(Integer barberShopId, List<Employee> updatedEmployees) {
        BarberShop barberShop = this.barberShopRepository.getById(barberShopId);
        for (Employee employeeUpdt : updatedEmployees) {
            Employee existingEmployee = this.employeeMapper.toEmployee((this.employeeService.employeeById(employeeUpdt.getEmployeeId())));
            existingEmployee.setBarberShop(barberShop);
            this.employeeService.updateEmployee(existingEmployee.getEmployeeId(), (this.employeeMapper.toEmployeeRequest(existingEmployee)));
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