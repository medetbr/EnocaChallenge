package com.mdt.enocachallenge.repository;

import com.mdt.enocachallenge.model.Company;
import com.mdt.enocachallenge.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    List<Employee> findAllByIsDeleted(boolean b);
}
