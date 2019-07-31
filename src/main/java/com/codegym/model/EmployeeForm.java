package com.codegym.model;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public class EmployeeForm {
    private Long id;
    private String name;
    private Department department;
    private String address;
    private MultipartFile avatar;
    private Long salary;

    public EmployeeForm() {
    }

    public EmployeeForm(Long id, String name, Department department, String address, MultipartFile avatar, Long salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.address = address;
        this.avatar = avatar;
        this.salary = salary;
    }

    public EmployeeForm(Long id, String name, Department department, String address,Long salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.address = address;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public MultipartFile getAvatar() {
        return avatar;
    }

    public void setAvatar(MultipartFile avatar) {
        this.avatar = avatar;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }
}
