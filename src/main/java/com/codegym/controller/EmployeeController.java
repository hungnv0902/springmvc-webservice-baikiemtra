package com.codegym.controller;

import com.codegym.model.Department;
import com.codegym.model.Employee;
import com.codegym.model.EmployeeForm;
import com.codegym.service.DepartmentService;
import com.codegym.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
@Controller
@PropertySource("classpath:global_config_app.properties")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @ModelAttribute("departments")
    public Page<Department> departments(Pageable pageable) {
        return departmentService.findAll(pageable);
    }

    @Autowired
    Environment env;

    @RequestMapping("/employees")
    public ModelAndView showEmployee(@PageableDefault(size = 2) Pageable pageable) {
        Page<Employee> employees =employeeService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/employee/list");
        modelAndView.addObject("employees",employees);
        return modelAndView;

    }

    @GetMapping("/create-employee")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/employee/create");
        modelAndView.addObject("employeeForm", new EmployeeForm());
        return modelAndView;
    }

    @RequestMapping(value = "/create-employee", method = RequestMethod.POST)
    public ModelAndView saveEmployee(@ModelAttribute("employeeForm") EmployeeForm employeeForm, BindingResult result, HttpServletRequest servletRequest) {

        // thong bao neu xay ra loi
        if (result.hasErrors()) {
            System.out.println("Result Error Occured" + result.getAllErrors());
        }

        // lay ten file
        MultipartFile multipartFile = employeeForm.getAvatar();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = env.getProperty("file_upload").toString();

        // luu file len server
        try {
            //multipartFile.transferTo(imageFile);
            FileCopyUtils.copy(employeeForm.getAvatar().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // tham khảo: https://github.com/codegym-vn/spring-static-resources

        // tao doi tuong de luu vao db
        Employee productObject = new Employee(employeeForm.getName(), employeeForm.getDepartment(),employeeForm.getAddress(),fileName,employeeForm.getSalary());

        // luu vao db
        employeeService.save(productObject);


        ModelAndView modelAndView = new ModelAndView("/employee/create");
        modelAndView.addObject("employee", new EmployeeForm());
        modelAndView.addObject("message", "New employee created successfully");
        return modelAndView;
    }
    @GetMapping("/edit-employee/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Employee employee = employeeService.findById(id);
        if(employee != null) {
            EmployeeForm employeeForm = new EmployeeForm(employee.getId(),employee.getName(),employee.getDepartment(),employee.getAddress(),null,employee.getSalary());
            ModelAndView modelAndView = new ModelAndView("/employee/edit");
            modelAndView.addObject("employee", employee);
            modelAndView.addObject("employeeForm",employeeForm);
            return modelAndView;

        }else {
            ModelAndView modelAndView = new ModelAndView("employee/error");
            return modelAndView;
        }
    }

    @PostMapping("/edit-employee")
    public ModelAndView editEmployee(@ModelAttribute("employeeForm") EmployeeForm employeeForm, BindingResult result, Pageable pageable) {

        // thong bao neu xay ra loi
        if (result.hasErrors()) {
            System.out.println("Result Error Occured" + result.getAllErrors());
        }

        // lay ten file
        MultipartFile multipartFile = employeeForm.getAvatar();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = env.getProperty("file_upload").toString();

        // luu file len server
        try {
            //multipartFile.transferTo(imageFile);
            FileCopyUtils.copy(employeeForm.getAvatar().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Employee employee = employeeService.findById(employeeForm.getId());
        // tao doi tuong de luu vao db
        if(fileName != "") {
            Employee employeeObject = new Employee(employeeForm.getId(), employeeForm.getName(), employeeForm.getDepartment(), employeeForm.getAddress(), fileName, employeeForm.getSalary());
            employeeService.save(employeeObject);
        } else {
            Employee employeeObject = new Employee(employeeForm.getId(), employeeForm.getName(), employeeForm.getDepartment(), employeeForm.getAddress(),employee.getAvatar(), employeeForm.getSalary());
            employeeService.save(employeeObject);
        }
        // luu vao db



        Page<Employee> employees =employeeService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/employee/list");
        modelAndView.addObject("employees",employees);
        return modelAndView;

    }


    @GetMapping("/delete-employee/{id}")
    public ModelAndView deleteEmployee(@PathVariable Long id) {
        Employee employee =  employeeService.findById(id);
        if (employee != null) {
            ModelAndView modelAndView = new ModelAndView("/employee/delete");
            modelAndView.addObject("employee",employee);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/employee/error");
            return modelAndView;
        }
    }


    @PostMapping("/delete-employee")
    public String deleteCustomer(@ModelAttribute("employee") Employee employee){
        employeeService.remove(employee.getId());
        return "redirect:/employees";
    }

    @GetMapping("/search-employee")
    public ModelAndView searchBlog(@RequestParam("department") String department,Pageable pageable) {
        Department department1 = departmentService.findByName(department);
        Page<Employee> employees = employeeService.findAllByDepartment(department1, pageable);
        ModelAndView modelAndView = new ModelAndView("/employee/search");
        modelAndView.addObject("employees",employees);
        return modelAndView;
    }

    @GetMapping("/view-employee/{id}")
    public ModelAndView viewEmployee(@PathVariable Long id) {
        Employee employee = employeeService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/employee/view");
        modelAndView.addObject("employee",employee);
        return modelAndView;
    }


}
