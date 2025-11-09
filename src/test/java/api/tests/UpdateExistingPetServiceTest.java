package api.tests;

import java.io.IOException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import api.models.PetPayload;
import api.models.PetPayload.Category;
import api.models.PetPayload.Tag;
import api.services.PetStoreService;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;

public class UpdateExistingPetServiceTest {
	private final PetStoreService petService = new PetStoreService();

	@Epic("Pet Store Management API")
    @Feature("Update Pet Endpoint")
    @Story("Update a existing Pet")
    @Description("Verify that PUT returns status code 200")
	@Test(dataProvider = "updatePetDataProvider")
	public void createBooking(PetPayload payLoad) throws StreamReadException, DatabindException, IOException {
		Response createRresponse = petService.addNewPet(payLoad);
		Assert.assertEquals(createRresponse.statusCode(), 200);
		Allure.addAttachment("Response Body", createRresponse.asPrettyString());
		if (payLoad.getName() != null) {
			Assert.assertEquals(createRresponse.jsonPath().getJsonObject("name"), payLoad.getName());
		}
		if (payLoad.getStatus() != null) {
			Assert.assertEquals(createRresponse.jsonPath().getJsonObject("status"), payLoad.getStatus());
		}
		if (payLoad.getCategory() != null) {
			Assert.assertEquals(createRresponse.jsonPath().getJsonObject("category.name"),
					payLoad.getCategory().getName());
		}
		if (payLoad.getTags() != null && !payLoad.getTags().isEmpty()) {
			Assert.assertEquals(createRresponse.jsonPath().getJsonObject("tags[0].name"),
					payLoad.getTags().get(0).getName());
		}
	}

	@DataProvider(name = "updatePetDataProvider")
	public Object[][] petDataProvider() {
		PetPayload pet1 = new PetPayload();
		pet1.setId(103);
		pet1.setCategory(new Category(1, "dog"));
		pet1.setName("Walter");
		pet1.setPhotoUrls(List.of("http://example.com/walter.jpg", "http://example.com/walter2.jpg"));
		pet1.setTags(List.of(new Tag(1, "friendly"), new Tag(2, "trained")));
		pet1.setStatus("pending");

		PetPayload pet2 = new PetPayload();
		pet2.setId(104);
		pet2.setCategory(new Category(3, "fish"));
		pet2.setName("Goldie");
		pet2.setPhotoUrls(List.of("http://example.com/goldie1.jpg", "http://example.com/goldie2.jpg" ));
		pet2.setTags(List.of(new Tag(2, "friendly")));
		pet2.setStatus("available");

		return new Object[][] { { pet1 }, { pet2 } };
	}
}