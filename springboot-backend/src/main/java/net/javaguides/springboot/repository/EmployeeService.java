package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Employee;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeService /*extends JpaRepository<Employee, Long>*/ {

    private static final Map<Long, Employee> list = new HashMap<>();

    static {
        list.put(1L, new Employee(1L, "Marc", "Martin", "marc.martin@gmail.com"));
        list.put(2L, new Employee(2L, "Marie", "Dupond", "dupond.martin@gmail.com"));
        list.put(3L, new Employee(3L, "Morgan", "Durand", "morgan.durand@gmail.com"));
        list.put(4L, new Employee(4L, "Mathis", "Dupuis", "mathis.dupuis@gmail.com"));
    }

    public Employee findById(Long id) {
        return list.get(id);
    }

    public Employee save(Employee employee) {
        return list.put(employee.getId(), employee);
    }

    public boolean delete(Employee employee) {
        return list.remove(employee.getId()) != null;
    }

    public List<Employee> findAll() {
        return new ArrayList<>(list.values());
    }

    public Long getNewKey() {
        return Collections.max(list.keySet()) + 1L;
    }

}
