package com.gorest.info;

import com.gorest.constants.EndPoints;
import com.gorest.model.UserPojo;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class UserSteps {

    static final String token = "Bearer d9b32baa853468ab909fb19a4185e96b84ede0c421188546c6adb513eff90833";

    @Step("Creating the user with name:{0},email:{1},gender:{2},status:{3}")
    public ValidatableResponse createUser(String name, String email, String gender, String status) {
        UserPojo userPojo = new UserPojo();
        userPojo.setName(name);
        userPojo.setEmail(email);
        userPojo.setGender(gender);
        userPojo.setStatus(status);
        return SerenityRest.given().log().all()
                .header("Authorization", token)
                .header("Content-Type", "application/json")
                .header("Connection", "keep-alive")
                .body(userPojo)
                .when()
                .post("/users")
                .then().log().all().statusCode(201);
    }

    @Step("Getting the user information with firstname: {0}")
    public HashMap<String, Object> getUserInfoByName(String name) {
        String u1 = "findAll{it.firstName == '";
        String u2 = "'}.get(0)";
        return SerenityRest.given()
                .header("Authorization", token)
                .header("Content-Type", "application/json")
                .header("Connection", "keep-alive")
                .when()
                .get(EndPoints.GET_ALL_USERS)
                .then().statusCode(200)
                .extract()
                .path(u1 + name + u2);
    }
    @Step("Updating user information with userId: {0},name:{1},email: {2},gender:{3},status: {4}")
    public ValidatableResponse updateUser(int uid, String name, String email, String gender, String status) {
        UserPojo userPojo = new UserPojo();
        userPojo.setName(name);
        userPojo.setEmail(email);
        userPojo.setGender(gender);
        userPojo.setStatus(status);
        return SerenityRest.given()
                .header("Authorization", token)
                .header("Content-Type", "application/json")
                .header("Connection", "keep-alive")
                .pathParam("id", uid)
                .when()
                .body(userPojo)
                .put(EndPoints.UPDATE_USER_BY_ID)
                .then();
    }

    @Step("Deleting user information with userId:{0}")
    public ValidatableResponse deleteUser(int userId) {
        return SerenityRest.given()
                .header("Authorization", token)
                .header("Content-Type", "application/json")
                .header("Connection", "keep-alive")
                .pathParam("id", userId)
                .when()
                .delete(EndPoints.DELETE_USER_BY_ID)
                .then().log().all();
    }
  @Step("Getting user information with userId:{0}")
    public ValidatableResponse getUserById(int userId){
        return SerenityRest.given()
                .header("Authorization", token)
                .header("Content-Type", "application/json")
                .header("Connection", "keep-alive")
                .pathParam("id",userId)
                .when()
                .get(EndPoints.GET_SINGLE_USER_BY_ID)
                .then();
  }

}