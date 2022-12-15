package com.mdt.enocachallenge.service;

import com.mdt.enocachallenge.dto.company.CompanyResponse;
import com.mdt.enocachallenge.dto.employee.EmployeeRequest;
import com.mdt.enocachallenge.dto.employee.EmployeeResponse;
import com.mdt.enocachallenge.exception.InvalidIdException;
import com.mdt.enocachallenge.model.Company;
import com.mdt.enocachallenge.model.Employee;
import com.mdt.enocachallenge.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final CompanyService companyService;
    public EmployeeResponse create(EmployeeRequest employeeRequest) {
        Company company = companyService.findById(employeeRequest.getCompany_id());
        if(company==null) throw new InvalidIdException("Geçersiz şirket id'si");
        Employee employee = Employee.builder()
                .company_id(company)
                .firstname(employeeRequest.getFirstname())
                .lastname(employeeRequest.getLastname())
                .phone(employeeRequest.getPhone())
                .address(employeeRequest.getAddress())
                .salary(employeeRequest.getSalary())
                .isDeleted(false)
                .build();
        employeeRepository.save(employee);
        EmployeeResponse employeeResponse = convertEmployeeToEmployeeResponse(employee);
        return employeeResponse;
    }

    private EmployeeResponse convertEmployeeToEmployeeResponse(Employee employee) {
        Company company = companyService.findById(employee.getCompany_id().getId());
        if(company==null) throw new InvalidIdException("Geçersiz şirket id'si");
        CompanyResponse companyResponse = companyService.convertCompanyToCompanyResponse(company);
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setFirstname(employee.getFirstname());
        employeeResponse.setLastname(employee.getLastname());
        employeeResponse.setSalary(employee.getSalary());
        employeeResponse.setPhone(employee.getPhone());
        employeeResponse.setAddress(employee.getAddress());
        employeeResponse.setCompany(companyResponse);
        return employeeResponse;
    }

    public EmployeeResponse update(Long id, EmployeeRequest newEmployee) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if(employee==null) throw new InvalidIdException("Geçersiz id girdiniz");

        Company company = companyService.findById(newEmployee.getCompany_id());
        if(company==null) throw new InvalidIdException("Geçersiz şirket id'si");

        employee.setFirstname(newEmployee.getFirstname() != null ? newEmployee.getFirstname() : employee.getFirstname());
        employee.setLastname(newEmployee.getLastname() != null ? newEmployee.getLastname() : employee.getLastname());
        employee.setAddress(newEmployee.getAddress() != null ? newEmployee.getAddress() : employee.getAddress());
        employee.setCompany_id(newEmployee.getCompany_id() != null ? company : employee.getCompany_id());
        employee.setSalary(newEmployee.getSalary() != null ? newEmployee.getSalary() : employee.getSalary());
        employee.setPhone(newEmployee.getPhone() != null ? newEmployee.getPhone() : employee.getPhone());
        employeeRepository.save(employee);

        EmployeeResponse employeeResponse = convertEmployeeToEmployeeResponse(employee);
        return employeeResponse;
    }

    public String delete(Long id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if(employee==null) throw new InvalidIdException("Geçersiz id girdiniz");
        employee.setIsDeleted(true);
       employeeRepository.save(employee);
        return "Çalışan başarılı bir şekilde silindi";
    }

    public List<EmployeeResponse> findAll() {
        List<Employee> employeeList = employeeRepository.findAllByIsDeleted(false);
        return employeeList.stream().map(this::convertEmployeeToEmployeeResponse).collect(Collectors.toList());
    }
}
