package net.javaguides.springboot.service;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeService) {
        this.employeeRepository = employeeService;
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
    }

    public Employee save(Employee employee) {
        if (employee.getLastTouched() == null) {
            employee.setLastTouched(new Date());
        }
        return employeeRepository.save(employee);
    }

    public boolean delete(Employee employeeBoundary) {
        var id = employeeBoundary.getId();
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
        employeeRepository.delete(employee);
        return true;
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

}
