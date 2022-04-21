package com.example.employee.web;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.example.employee.model.Employee;
import com.example.employee.model.EmployeeData;
import com.example.employee.repository.EmployeeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Operation(description = "Get all employee data")
    @ApiResponse(responseCode = "200", description = "Response containing the list of available employees.")
    @ApiResponse(responseCode = "400", description = "Parse error or invalid data in request.")
    @RequestMapping(
        value = "",
        method = RequestMethod.GET,
        produces = "application/json"
    )
    public EmployeeData findAll() {
        var employees = employeeRepository.findAll();
        return new EmployeeData(StreamSupport.stream(employees.spliterator(), false).collect(Collectors.toList()));
    }

    @Operation(description = "Get employees by name")
    @ApiResponse(responseCode = "200", description = "Response containing the list of available employees with the given name.")
    @ApiResponse(responseCode = "400", description = "Parse error or invalid data in request.")
    @RequestMapping(
        value = "/name/{employeeName}",
        method = RequestMethod.GET,
        produces = "application/json"
    )
    public EmployeeData findByName(
        @PathVariable @Schema(description = "Employee name", example = "James Smith") String employeeName) {
        var employees = employeeRepository.findByName(employeeName);
        return new EmployeeData(employees);
    }

    @Operation(description = "Get a single employee data by id")
    @ApiResponse(responseCode = "200", description = "Response containing the given employee data.")
    @ApiResponse(responseCode = "400", description = "Parse error or invalid data in request.")
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.GET,
        produces = "application/json"
    )
    public Employee findById(@PathVariable @Schema(description = "ID of the employee", example = "1") long id) {
        return employeeRepository.findById(id).orElseThrow();
    }

    @Operation(description = "Creates a new employee record in database")
    @ApiResponse(responseCode = "200", description = "New employee record successfully created in database.")
    @ApiResponse(responseCode = "400", description = "Parse error or invalid data in request.")
    @RequestMapping(
        value = "",
        method = RequestMethod.POST,
        produces = "application/json"
    )
    public Employee create(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @Operation(description = "Remove an employee record by id")
    @ApiResponse(responseCode = "204", description = "Employee record successfully removed.")
    @ApiResponse(responseCode = "400", description = "Parse error or invalid data in request.")
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.DELETE,
        produces = "application/json"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Schema(description = "ID of the employee", example = "1") long id) {
        employeeRepository.findById(id).orElseThrow();
        employeeRepository.deleteById(id);
    }

    @Operation(description = "Update an employee record by id")
    @ApiResponse(responseCode = "200", description = "Employee record successfully updated.")
    @ApiResponse(responseCode = "400", description = "Parse error or invalid data in request.")
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.PUT,
        produces = "application/json"
    )
    public Employee updateEmployee(@RequestBody Employee employee,
        @PathVariable @Schema(description = "ID of the employee", example = "123456") long id) {
        employeeRepository.findById(id);
        return employeeRepository.save(employee);
    }

}
