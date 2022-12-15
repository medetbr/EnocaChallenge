package com.mdt.enocachallenge.controller;

import com.mdt.enocachallenge.dto.company.CompanyResponse;
import com.mdt.enocachallenge.dto.employee.EmployeeRequest;
import com.mdt.enocachallenge.dto.employee.EmployeeResponse;
import com.mdt.enocachallenge.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/")
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody EmployeeRequest employeeRequest){
        EmployeeResponse employeeResponse = employeeService.create(employeeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeResponse);
    }
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateCompany(@PathVariable Long id,@RequestBody EmployeeRequest newEmployee){
        EmployeeResponse employeeResponse = employeeService.update(id,newEmployee);
        return ResponseEntity.status(HttpStatus.OK).body(employeeResponse);
    }
    @GetMapping("/")
    public ResponseEntity<List<EmployeeResponse>> getAllEmployeeList(){
        List<EmployeeResponse> employeeList = employeeService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(employeeList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        String response = employeeService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
