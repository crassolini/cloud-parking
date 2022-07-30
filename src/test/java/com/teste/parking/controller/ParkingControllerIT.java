package com.teste.parking.controller;

import com.teste.parking.controller.dto.ParkingCreateDTO;
import io.restassured.RestAssured;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParkingControllerIT extends AbstractContainerBase {

    @LocalServerPort
    private int randomPort;

    @BeforeEach
    public void setUpTest() {
        RestAssured.port = randomPort;
    }

    @Test
    void whenFindAllThenCheckResult() {
        RestAssured.given()
                .auth()
                .basic("usuario", "usuario.Abc")
                .when()
                .get("/parking")
                .then()
                //.body("license[0]", Matchers.equalTo("ABC-1234"));
                //.extract().response().body().prettyPrint();
                .statusCode(HttpStatus.OK.value());
                //.body()

    }

    @Test
    void whenCreateThenCheckIsCreated() {

        ParkingCreateDTO parkingCreateDTO = new ParkingCreateDTO();
        parkingCreateDTO.setColor("AMARELO");
        parkingCreateDTO.setLicense("QQQ-0011");
        parkingCreateDTO.setModel("Fiat Strada");
        parkingCreateDTO.setState("CE");

        RestAssured.given()
                .auth()
                .basic("usuario","usuario.Abc")
                .when()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(parkingCreateDTO)
                .post("/parking")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("color", Matchers.equalTo("AMARELO"))
                .body("license", Matchers.equalTo("QQQ-0011"));

    }
}