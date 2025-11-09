package api.services;

import static io.restassured.RestAssured.given;

import api.models.PetPayload;
import api.specs.RequestSpecBuilder;
import io.restassured.response.Response;

public class PetStoreService {

    public Response addNewPet(PetPayload payload) {
        return given()
                .spec(RequestSpecBuilder.getRequestSpec())
                .body(payload)
                .when()
                .post();
    }

    public Response getPetById(int id) {
        return given()
                .spec(RequestSpecBuilder.getRequestSpec())
                .when()
                .get("/" + id);
    }

    public Response updateExistingPet(PetPayload payload) {
        return given()
                .spec(RequestSpecBuilder.getRequestSpec())
                .body(payload)
                .when()
                .put();
    }
    
    public Response deletePet(int id) {
        return given()
                .spec(RequestSpecBuilder.getRequestSpec())
                .when()
                .delete("/" + id);
    }
    
    public Response getPetByStatus(String status) {
        return given()
                .spec(RequestSpecBuilder.getRequestSpec())
                .queryParam("status", status)
                .when()
                .get("/findByStatus");
    }

}