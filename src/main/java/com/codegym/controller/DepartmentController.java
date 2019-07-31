package com.codegym.controller;

import com.codegym.model.Department;
import com.codegym.model.Employee;
import com.codegym.service.DepartmentService;
import com.codegym.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeService employeeService;
    @GetMapping("/departments")
    public ResponseEntity<Page<Department>> listDepartments(Pageable pageable){
        Page<Department> departments = departmentService.findAll(pageable);
        return new ResponseEntity<Page<Department>>(departments,HttpStatus.OK);
    }



    @PostMapping("/create-department")
    public ResponseEntity<Employee> saveProvince(@RequestBody Department department, UriComponentsBuilder ucBuilder){
        departmentService.save(department);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/category/{id}").buildAndExpand(department.getId()).toUri());
        return new ResponseEntity<Employee>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/edit-department/{id}")
    public ResponseEntity<Department> editFormDepartment(@PathVariable Long id, @RequestBody Department department) {
        Department currentDepartment = departmentService.findById(id);
        if(currentDepartment == null) {
            System.out.println("Department is id " + id + " not fount");
            return new ResponseEntity<Department>(HttpStatus.NOT_FOUND);
        }

        currentDepartment.setId(department.getId());
        currentDepartment.setName(department.getName());
        currentDepartment.setEmployees(department.getEmployees());
        departmentService.save(currentDepartment);
        return new ResponseEntity<Department>(HttpStatus.OK);

    }


    @DeleteMapping("/delete-department/{id}")
    public ResponseEntity<Department> deleteFormDepartment(@PathVariable Long id) {
        Department department = departmentService.findById(id);
        if(department == null) {
            System.out.println("Department is id " + id + " not fount");
            return new ResponseEntity<Department>(HttpStatus.NOT_FOUND);
        }

        departmentService.remove(id);
        return new ResponseEntity<Department>(HttpStatus.OK);

    }


}
