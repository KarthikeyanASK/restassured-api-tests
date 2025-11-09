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

public class DeletePetServiceTest {
	private final PetStoreService petService = new PetStoreService();

	@Epic("Pet Store Management API")
    @Feature("Delete Pet Endpoint")
    @Story("Delete an existing Pet")
    @Description("Verify that valid Delete returns status code 200")
	@Test(dataProvider = "getValidPetDataProvider")
	public void getPetDetailsByValidId(int validPetId) {
		Response getValidResponse = petService.deletePet(validPetId);
		Allure.addAttachment("Response Body", getValidResponse.asPrettyString());
		Assert.assertEquals(getValidResponse.statusCode(), 200);
	}
	
	@Epic("Pet Store Management API")
    @Feature("Delete Pet Endpoint")
    @Story("Delete a non-existing Pet")
    @Description("Verify that invalid Delete returns status code 404")
	@Test(dataProvider = "getInvalidPetDataProvider")
	public void getPetDetailsByInvalidId(int invalidPetId) {
		Response getInvalidResponse = petService.deletePet(invalidPetId);
		Allure.addAttachment("Response Body", getInvalidResponse.asPrettyString());
		Assert.assertEquals(getInvalidResponse.statusCode(), 404);
	}

	@DataProvider(name = "getValidPetDataProvider")
	public Object[][] getInvalidValidData() {
		return new Object[][] { { 101 }, { 102 }, { 103 }, { 104 } };
	}

	@DataProvider(name = "getInvalidPetDataProvider")
	public Object[][] getValidData() {
		return new Object[][] { { 000000000 } };
	}
}