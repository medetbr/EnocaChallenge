package com.mdt.enocachallenge.dto.employee;

import com.mdt.enocachallenge.dto.company.CompanyResponse;
import lombok.Data;

@Data
public class EmployeeResponse {
    private CompanyResponse company;
    private String firstname;
    private String lastname;
    private String phone;
    private String address;
    private Double salary;

}
