package com.luv2code.srpingboot.cruddemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.srpingboot.cruddemo.entity.Employee;
import com.luv2code.srpingboot.cruddemo.service.EmployeeService;

@RestController
public class EmployeeRestController {
	
	//inject employee dao
	
	private EmployeeService employeeService;
	
	
	@Autowired
	public EmployeeRestController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	
	
	//expose "/employees" and return list of employees
	
	@GetMapping("/employees")
	public List<Employee> findAll(){
		return employeeService.findAll();
	}
	
	//expose "/employees/{employeeId} to return a single employee
	
	@GetMapping("/employees/{employeeId}")
	public Employee getEmployee(@PathVariable int employeeId) {
		Employee theEmployee = employeeService.findById(employeeId);
		if(theEmployee==null) {
			throw new RuntimeException("Emplyee id not found - "+employeeId);
		}
		return theEmployee;
		
	}
	
	//add mapping for past /employees
	@PostMapping("/employees")
	public Employee addEmployee(@RequestBody Employee theEmployee) {
		//just in case if the user pass an id in JSON...set id to 0
		//this is to force a save of new item...instead of update
		theEmployee.setId(0);
		employeeService.save(theEmployee);
		return theEmployee;
		
	}
	//add mapping for PUT/Employees - update existing employee
	
	@PutMapping("/employees")
	public Employee updateEmployee(@RequestBody Employee theEmployee) {
		employeeService.save(theEmployee);
		return theEmployee;
	}
	
	@DeleteMapping("/employees/{employeeId}")
	public String deleteEmployee(@PathVariable int employeeId) {
		Employee tempEmployee = employeeService.findById(employeeId);
		
		if(tempEmployee==null) {
			throw new RuntimeException("Employee id not found = "+employeeId);
		}
		//delete employee
		employeeService.deleteById(employeeId);
		return "Deleted employee with id - "+employeeId;
	}
	

}
