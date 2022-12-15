package com.mdt.enocachallenge.dto.employee;

import com.mdt.enocachallenge.model.Company;
import lombok.Data;

@Data
public class EmployeeRequest {
    private Long company_id;
    private String firstname;
    private String lastname;
    private String phone;
    private String address;
    private Double salary;

}
