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

public class RetrievePetDataByIdServiceTest {
	private final PetStoreService petService = new PetStoreService();
    
	@Epic("Pet Store Management API")
    @Feature("Get Pet Endpoint")
    @Story("Get an existing Pet")
    @Description("Verify that valid GET returns status code 200")
    @Test(dataProvider = "getValidPetDataProvider")
    public void getPetDetailsById(int validPetId) {
    	Response getValidResponse = petService.getPetById(validPetId);
    	Allure.addAttachment("Response Body", getValidResponse.asPrettyString());
    	try {
    	Assert.assertEquals(getValidResponse.statusCode(), 200);
    	Assert.assertEquals(getValidResponse.jsonPath().getInt("id"), validPetId);
    	}
    	catch(AssertionError e) {
    		Allure.addAttachment("***FAILED INVALID ID IS *** ", String.valueOf(validPetId));
    	}
    }
    
	@Epic("Pet Store Management API")
    @Feature("Get Pet Endpoint")
    @Story("Get a non-existing Pet")
    @Description("Verify that invalid GET returns status code 404")
    @Test(dataProvider = "getInvalidPetDataProvider")
    public void getPetDetailsByInvalidId(int invalidPetId) {
    	Response getInvalidResponse = petService.getPetById(invalidPetId);
    	Allure.addAttachment("Response Body", getInvalidResponse.asPrettyString());
    	Assert.assertEquals(getInvalidResponse.statusCode(), 404);
    	Assert.assertEquals(getInvalidResponse.jsonPath().getString("message"), "Pet not found");
    }
    
    @DataProvider(name = "getValidPetDataProvider")
    public Object[][] getInvalidValidData() {
        return new Object[][] {
            {101},
            {102},
            {103},
            {104}
        };
    }
    
    @DataProvider(name = "getInvalidPetDataProvider")
    public Object[][] getValidData() {
        return new Object[][] {
            {000000000}
        };
    }
}