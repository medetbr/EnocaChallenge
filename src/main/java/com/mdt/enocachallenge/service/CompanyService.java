package com.mdt.enocachallenge.service;

import com.mdt.enocachallenge.dto.company.CompanyRequest;
import com.mdt.enocachallenge.dto.company.CompanyResponse;
import com.mdt.enocachallenge.exception.InvalidIdException;
import com.mdt.enocachallenge.model.Company;
import com.mdt.enocachallenge.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyResponse create(CompanyRequest companyRequest) {
        Company company = Company.builder()
                .name(companyRequest.getName())
                .city(companyRequest.getCity())
                .phone(companyRequest.getPhone())
                .address(companyRequest.getAddress())
                .isDeleted(false)
                .build();
        companyRepository.save(company);
        CompanyResponse companyResponse = convertCompanyToCompanyResponse(company);
        return companyResponse;
    }

    public CompanyResponse convertCompanyToCompanyResponse(Company company) {
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setName(company.getName());
        companyResponse.setCity(company.getCity());
        companyResponse.setPhone(company.getPhone());
        companyResponse.setAddress(company.getAddress());
        return companyResponse;
    }

    public CompanyResponse update(Long id, CompanyRequest newCompany) {
        Company company = companyRepository.findById(id).orElse(null);
        if(company==null) throw new InvalidIdException("Geçersiz şirket id'si");
        company.setAddress(newCompany.getAddress() != null ? newCompany.getAddress() : company.getAddress());
        company.setCity(newCompany.getCity() != null ? newCompany.getCity() : company.getCity());
        company.setName(newCompany.getName() != null ? newCompany.getName() : company.getName());
        company.setPhone(newCompany.getPhone() != null ? newCompany.getPhone() : company.getPhone());
        CompanyResponse companyResponse = convertCompanyToCompanyResponse(company);
        companyRepository.save(company);
        return companyResponse;
    }

    public String delete(Long id) {
        Company company = companyRepository.findById(id).orElse(null);
        if(company==null) throw new InvalidIdException("Geçersiz şirket id'si");
        company.setIsDeleted(true);
        companyRepository.save(company);
        return "Şirket başarılı bir şekilde silindi";
    }

    public Company findById(Long companyId) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if(company==null) throw new InvalidIdException("Geçersiz şirket id'si");
        return company;
    }

    public List<CompanyResponse> findAll() {
        List<Company> companyList = companyRepository.findAllByIsDeleted(false);
        return companyList.stream().map(this::convertCompanyToCompanyResponse).collect(Collectors.toList());
    }
}
