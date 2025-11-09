package api.specs;

import api.configurations.RouteConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpecBuilder {
	private static RequestSpecification spec;
	
    public static RequestSpecification getRequestSpec() {
    	if (spec == null) {
            spec = new io.restassured.builder.RequestSpecBuilder()
                    .setBaseUri(RouteConfig.getBaseUrl())
                    .setContentType(ContentType.JSON)
                    .setAccept("application/json")
                    .log(LogDetail.ALL)
                    .build();
        }
    	return spec;
    }
}