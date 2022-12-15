package com.mdt.enocachallenge.controller;

import com.mdt.enocachallenge.dto.company.CompanyRequest;
import com.mdt.enocachallenge.dto.company.CompanyResponse;
import com.mdt.enocachallenge.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping("/")
    public ResponseEntity<CompanyResponse> createCompany(@RequestBody CompanyRequest companyRequest){
        CompanyResponse company = companyService.create(companyRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(company);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponse> updateCompany(@PathVariable Long id,@RequestBody CompanyRequest newCompany){
        CompanyResponse company = companyService.update(id,newCompany);
        return ResponseEntity.status(HttpStatus.OK).body(company);
    }
    @GetMapping("/")
    public ResponseEntity<List<CompanyResponse>> getAllCompanyList(){
        List<CompanyResponse> companyList = companyService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(companyList);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        String response = companyService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
