package api.specs;

import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;


public class ResponseSpecBuilder {
	public static ResponseSpecification defaultResponseSpec() {
        return new io.restassured.builder.ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .build();
    }
}