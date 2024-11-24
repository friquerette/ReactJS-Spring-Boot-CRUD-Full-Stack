package net.javaguides.springboot.controller;

import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.service.AIService;
import net.javaguides.springboot.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

	private final EmployeeService employeeService;
	private final AIService aiService;

	public EmployeeController(EmployeeService employeeService, AIService aiService) {
		this.employeeService = employeeService;
		this.aiService = aiService;
	}

	// get all employees
	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		var result = employeeService.findAll();
		if (CollectionUtils.isEmpty(result)) {
			initDatabase();
			result = employeeService.findAll();
		}
		return result;
	}

	private void initDatabase() {
		List<Employee> list = new ArrayList<>();
		list.add(new Employee("Marc", "Martin", "marc.martin@gmail.com"));
		list.add(new Employee("Marie", "Dupond", "dupond.martin@gmail.com"));
		list.add(new Employee("Morgan", "Durand", "morgan.durand@gmail.com"));
		list.add(new Employee("Mathis", "Dupuis", "mathis.dupuis@gmail.com"));
		list.forEach(employeeService::save);
	}
	
	// create employee rest api
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeService.save(employee);
	}
	
	// get employee by id rest api
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		Employee employee = employeeService.findById(id);
		return ResponseEntity.ok(employee);
	}
	
	// update employee rest api
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails){
		Employee employee = employeeService.findById(id);
		
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmailId(employeeDetails.getEmailId());
		
		Employee updatedEmployee = employeeService.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}

	// delete employee rest api
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
		Employee employee = employeeService.findById(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", employeeService.delete(employee));
		return ResponseEntity.ok(response);
	}

	// create employee rest api
	@PostMapping("/employees/ask-one-ai")
	public String askToOneAI(@RequestBody String oneQuestion) {
		return aiService.getOneQuestion(oneQuestion);
	}
	
}
