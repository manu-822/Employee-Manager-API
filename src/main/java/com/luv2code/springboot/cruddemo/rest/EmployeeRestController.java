package com.luv2code.springboot.cruddemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeRestController 
{
	
	private EmployeeService employeeService;
	
	//quick solution: inject employee dao directly, later we'll add the service layer
	@Autowired
	public EmployeeRestController(EmployeeService theEmployeeService)
	{
		employeeService=theEmployeeService;
	}
	
	//expose "/employees" adn return list of employees
	@GetMapping("/employees")
	public List<Employee> findAll()
	{
		return employeeService.findAll();
	}
	
	//add mapping for GET /employees/{employeeId}
	@GetMapping("/employees/{employeeId}")
	public Employee getEmployee(@PathVariable int employeeId)
	{
		Employee theEmployee= employeeService.findById(employeeId);
		
		if(theEmployee==null)
		{
			throw new RuntimeException("Employee id not found - "+employeeId);
		}
		
		return theEmployee;
	}
	
	// add mapping for POST /employees - add new employee
	@PostMapping("/employees")
	public Employee addEmployees(@RequestBody Employee theEmployee)
	{
		//also just in case they pass an id in JSON... set id to 0
		theEmployee.setId(0);
		
		employeeService.save(theEmployee);
		
		return theEmployee;
	}
	
	// add PUT Mapping for /employee - update an existing employee
	@PutMapping("/employees")
	public Employee updateEmployee(@RequestBody Employee theEmployee)
	{
		employeeService.save(theEmployee);
		
		return theEmployee;
	}
	
	//add DELETE mapping /employee/{employeeId} - delete a Employee using id
	@DeleteMapping("/employees/{employeeId}")
	public String deleteEmployee(@PathVariable int employeeId)
	{
		Employee tempEmployee = employeeService.findById(employeeId);
		
		//throw an exception if null (employee doesnt exist)
		
		if(tempEmployee == null)
		{
			throw new RuntimeException("no such employee exists with id - " + employeeId);
		}
		
		employeeService.deleteById(employeeId);
		
		return "employee with id " + employeeId +" deleted successfully";
	}
}











