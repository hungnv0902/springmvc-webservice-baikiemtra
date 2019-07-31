package com.codegym.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    private String address;
    private String avatar;
    private Long salary;

    public Employee() {
    }

    public Employee(String name,  Department department, String address, String avatar, Long salary) {
        this.name = name;
        this.department = department;
        this.address = address;
        this.avatar = avatar;
        this.salary = salary;
    }

    public Employee(Long id,String name, Department department, String address, String avatar, Long salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.address = address;
        this.avatar = avatar;
        this.salary = salary;
    }

    public Employee(Long id,String name, Department department, String address, Long salary) {
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }
}
