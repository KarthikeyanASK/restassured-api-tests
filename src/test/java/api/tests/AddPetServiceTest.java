package api.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import api.models.PetPayload;
import api.models.PetPayload.Category;
import api.models.PetPayload.Tag;
import api.services.PetStoreService;
import io.qameta.allure.*;
import io.restassured.response.Response;
import java.util.Collections;
import java.util.List;

public class AddPetServiceTest {
	private final PetStoreService petService = new PetStoreService();

	@Epic("Pet Store Management API")
	@Feature("Add Pet Endpoint")
	@Story("Add a new Pet")
	@Description("Verify that POST returns status code 200")
	@Test(dataProvider = "createPetDataProvider")
	public void createBooking(PetPayload payLoad) {
		Response createResponse = petService.addNewPet(payLoad);
		Assert.assertEquals(createResponse.statusCode(), 200);
		Allure.addAttachment("Response Body", createResponse.asPrettyString());
		if (payLoad.getName() != null) {
			Assert.assertEquals(createResponse.jsonPath().getString("name"), payLoad.getName());
		}
		if (payLoad.getStatus() != null) {
			Assert.assertEquals(createResponse.jsonPath().getString("status"), payLoad.getStatus());
		}
		if (payLoad.getCategory() != null) {
			Assert.assertEquals(createResponse.jsonPath().getString("category.name"), payLoad.getCategory().getName());
		}
		if (payLoad.getTags() != null && !payLoad.getTags().isEmpty()) {
			Assert.assertEquals(createResponse.jsonPath().getString("tags[0].name"),
					payLoad.getTags().get(0).getName());
		}
	}

	@DataProvider(name = "createPetDataProvider")
	public Object[][] petDataProvider() {
		PetPayload pet1 = new PetPayload();

		PetPayload pet2 = new PetPayload();
		pet2.setId(101);
		pet2.setCategory(new Category(1, "Dogs"));
		pet2.setName("dogge");
		pet2.setPhotoUrls(List.of("http://example.com/dogge.jpg"));
		pet2.setTags(List.of(new Tag(1, "friendly")));
		pet2.setStatus("available");

		PetPayload pet3 = new PetPayload();
		pet3.setId(102);
		pet3.setCategory(null);
		pet3.setName("Whiskers");
		pet3.setPhotoUrls(Collections.emptyList());
		pet3.setTags(Collections.emptyList());
		pet3.setStatus("available");

		PetPayload pet4 = new PetPayload();
		pet4.setId(103);
		pet4.setCategory(new Category(1, "dog"));
		pet4.setName("Walter");
		pet4.setPhotoUrls(List.of("http://example.com/walter.jpg"));
		pet4.setTags(List.of(new Tag(1, "friendly")));
		pet4.setStatus("pending");

		PetPayload pet5 = new PetPayload();
		pet5.setId(104);
		pet5.setCategory(new Category(3, "fish"));
		pet5.setName("Goldie");
		pet5.setPhotoUrls(List.of("http://example.com/goldie1.jpg", "http://example.com/goldie2.jpg"));
		pet5.setTags(List.of(new Tag(2, "friendly")));
		pet5.setStatus("sold");

		return new Object[][] { { pet1 }, { pet2 }, { pet3 }, { pet4 }, { pet5 } };
	}
}