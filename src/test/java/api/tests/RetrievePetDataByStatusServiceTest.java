package api.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import api.services.PetStoreService;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;

public class RetrievePetDataByStatusServiceTest {
	private final PetStoreService petService = new PetStoreService();
	
	@Epic("Pet Store Management API")
    @Feature("Get Pet Endpoint")
    @Story("Get Pet list by status")
    @Description("Verify that valid GET returns status code 200")
	@Test(dataProvider = "statusDataProvider")
    public void getPetDetailsByStatus(String status) {
    	Response getResponse = petService.getPetByStatus(status);
    	Allure.addAttachment("Response Body", getResponse.asPrettyString());
    	Assert.assertEquals(getResponse.statusCode(), 200);
    	Assert.assertEquals(getResponse.jsonPath().getString("[0].status"), status);
    }
    
    @DataProvider(name = "statusDataProvider")
    public Object[][] getStatusData() {
        return new Object[][] {
            {"available"},
            {"pending"},
            {"sold"}
        };
    }
}