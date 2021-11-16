package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    // get all employees

    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    //create employee rest api
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody  Employee employee){
        return employeeRepository.save(employee);
    }

    // get employee by id rest api
    @GetMapping("/employees/{id}")
    public ResponseEntity< Employee >getEmployeeById(@PathVariable Long id){
        Employee emp= employeeRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Employee not exit with the id: "+id));
    return ResponseEntity.ok(emp);
    }
    // update employee rest api
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,@RequestBody Employee employeeDetail){
        Employee emp= employeeRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Employee not exit with the id: "+id));
        emp.setFirstName(employeeDetail.getFirstName());
        emp.setLastName(employeeDetail.getLastName());
        emp.setEmailId(employeeDetail.getEmailId());
        Employee employeeUpdate = employeeRepository.save(emp);
        return ResponseEntity.ok(employeeUpdate);
    }

    // delete employee rest api
    @DeleteMapping("employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
        Employee emp= employeeRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Employee not exit with the id: "+id));
            employeeRepository.delete(emp);
            Map<String,Boolean> response = new HashMap<>();
            response.put("deleted",Boolean.TRUE);
            return ResponseEntity.ok(response);
    }
}
