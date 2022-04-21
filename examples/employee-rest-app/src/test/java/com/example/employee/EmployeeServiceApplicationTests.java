package com.example.employee;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import com.example.employee.model.Employee;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmployeeServiceApplicationTests {

    private static final String API_ROOT = "/api/employees";

    @LocalServerPort
    private int port;

    private RequestSpecification request;

    @BeforeEach
    public void setup() {
        request = RestAssured.given().log().ifValidationFails()
            .baseUri("http://localhost")
            .port(port);
    }

    @Test
    public void get_all_employees() {
        request.get(API_ROOT).then().statusCode(200);
    }

    @Test
    public void get_first_employees() {
        request.pathParams("id", 1).get(API_ROOT + "/{id}").then().statusCode(200);
    }

    @Test
    public void get_employee_by_name() {
        var employee = generateRandomEmployee();
        createNewEmployee(employee);
        request.pathParams("employeeName", employee.getName())
            .get(API_ROOT + "/name/{employeeName}").then().statusCode(200).extract().jsonPath().get("data[0].name").equals(employee.getName());
    }

    @Test
    public void create_employee() {
        var employee = generateRandomEmployee();
        request
            .contentType(ContentType.JSON)
            .body(employee)
            .post(API_ROOT)
            .then()
            .statusCode(200)
            .extract().body().jsonPath().get("name").equals(employee.getName());
    }

    @Test
    public void delete_employee() {
        var employee = generateRandomEmployee();
        var employeeId = createNewEmployee(employee);
        request.pathParams("id", employeeId).delete(API_ROOT + "/{id}").then().statusCode(204);
    }

    @Test
    public void update_employee() {
        var employee = generateRandomEmployee();
        var employeeId = createNewEmployee(employee);
        employee.setId(Long.valueOf(employeeId));
        request.contentType(ContentType.JSON)
            .pathParams("id", employee.getId())
            .body(employee)
            .put(API_ROOT + "/{id}")
            .then().statusCode(200);
    }

    private Employee generateRandomEmployee() {
        Employee employee = new Employee();
        employee.setName(randomAlphabetic(10));
        employee.setSalary(1234);
        employee.setAge(99);
        return employee;
    }

    private Integer createNewEmployee(Employee employee) {
        return
            RestAssured.given().log().ifValidationFails()
                .baseUri("http://localhost")
                .port(port)
                .contentType(ContentType.JSON)
                .body(employee)
                .post(API_ROOT)
                .then()
                .statusCode(200)
                .extract().body().jsonPath().get("id");
    }

}
