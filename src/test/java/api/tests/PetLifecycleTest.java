package api.tests;

import java.util.List;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.Test;
import api.models.PetPayload;
import api.services.PetStoreService;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.restassured.response.Response;

public class PetLifecycleTest {

	private int petId = (int) (Math.random() * 10000);
	private PetStoreService petService = new PetStoreService();
	private PetPayload.Category category = new PetPayload.Category(2, "cat");
	private PetPayload.Tag tag = new PetPayload.Tag(3, "Friendly");
	private PetPayload.Tag tag2 = new PetPayload.Tag(3, "Trained");
	private PetPayload pet = new PetPayload(petId, category, "Missy", List.of("https://example.com/missy.jpg"),
			List.of(tag), "available");
	private PetPayload updatedPet = new PetPayload(petId, category, "Missy",
			List.of("https://example.com/missy1.jpg", "https://example.com/missy2.jpg"), List.of(tag2), "sold");

	@Test(priority = 1)
	@Description("Create a new pet and verify creation")

	public void addNewPet() {
		Response createResponse = petService.addNewPet(pet);
		Assert.assertEquals(createResponse.statusCode(), 200);
		Allure.addAttachment("Response Body", createResponse.asPrettyString());
		Assert.assertEquals(createResponse.jsonPath().getString("name"), pet.getName());
		Assert.assertEquals(createResponse.jsonPath().getString("status"), pet.getStatus());
		Assert.assertEquals(createResponse.jsonPath().getInt("category.id"), pet.getCategory().getId());
		Assert.assertEquals(createResponse.jsonPath().getString("category.name"), pet.getCategory().getName());
		Assert.assertEquals(createResponse.jsonPath().getList("photoUrls"), pet.getPhotoUrls());
		List<Map<String, Object>> actualTags = createResponse.jsonPath().getList("tags");

		if (actualTags != null && !actualTags.isEmpty()) {
			for (int i = 0; i < actualTags.size(); i++) {
				Map<String, Object> actualTag = actualTags.get(i);
				Assert.assertEquals(actualTag.get("id"), pet.getTags().get(i).getId());
				Assert.assertEquals(actualTag.get("name"), pet.getTags().get(i).getName());
			}
		}

	}

	@Test(priority = 2, dependsOnMethods = {"addNewPet"}, retryAnalyzer = api.utils.RetryAnalyzer.class)
	@Description("Retrieve the created pet and verify details")
	public void getPet() throws InterruptedException {
		Response getResponse = petService.getPetById(petId);
		Assert.assertEquals(getResponse.statusCode(), 200);
		Allure.addAttachment("Response Body", getResponse.asPrettyString());
		Assert.assertEquals(getResponse.jsonPath().getString("name"), pet.getName());
		Assert.assertEquals(getResponse.jsonPath().getString("status"), pet.getStatus());
		Assert.assertEquals(getResponse.jsonPath().getInt("category.id"), pet.getCategory().getId());
		Assert.assertEquals(getResponse.jsonPath().getString("category.name"), pet.getCategory().getName());
		Assert.assertEquals(getResponse.jsonPath().getList("photoUrls"), pet.getPhotoUrls());
		List<Map<String, Object>> actualTags = getResponse.jsonPath().getList("tags");

		if (actualTags != null && !actualTags.isEmpty()) {
			for (int i = 0; i < actualTags.size(); i++) {
				Map<String, Object> actualTag = actualTags.get(i);
				Assert.assertEquals(actualTag.get("id"), pet.getTags().get(i).getId());
				Assert.assertEquals(actualTag.get("name"), pet.getTags().get(i).getName());
			}
		}
	}

	@Test(priority = 3, dependsOnMethods = {"addNewPet", "getPet"})
	@Description("Update the pet and verify changes")
	public void updatePet() {
		Response updateResponse = petService.updateExistingPet(updatedPet);
		Assert.assertEquals(updateResponse.statusCode(), 200);
		Allure.addAttachment("Response Body", updateResponse.asPrettyString());
		Assert.assertEquals(updateResponse.jsonPath().getString("name"), updatedPet.getName());
		Assert.assertEquals(updateResponse.jsonPath().getString("status"), updatedPet.getStatus());
		Assert.assertEquals(updateResponse.jsonPath().getInt("category.id"), updatedPet.getCategory().getId());
		Assert.assertEquals(updateResponse.jsonPath().getString("category.name"), updatedPet.getCategory().getName());
		Assert.assertEquals(updateResponse.jsonPath().getList("photoUrls"), updatedPet.getPhotoUrls());
		List<Map<String, Object>> actualTags = updateResponse.jsonPath().getList("tags");

		if (actualTags != null && !actualTags.isEmpty()) {
			for (int i = 0; i < actualTags.size(); i++) {
				Map<String, Object> actualTag = actualTags.get(i);
				Assert.assertEquals(actualTag.get("id"), updatedPet.getTags().get(i).getId());
				Assert.assertEquals(actualTag.get("name"), updatedPet.getTags().get(i).getName());
			}
		}
	}

	@Test(priority = 4, dependsOnMethods = {"updatePet"}, retryAnalyzer = api.utils.RetryAnalyzer.class)
	@Description("Retrieve the updated pet and verify details")
	public void getUpdatedPet() throws InterruptedException {
		Response getUpdatedResponse = petService.getPetById(petId);
		Assert.assertEquals(getUpdatedResponse.statusCode(), 200);
		Allure.addAttachment("Response Body", getUpdatedResponse.asPrettyString());
		Assert.assertEquals(getUpdatedResponse.jsonPath().getString("name"), updatedPet.getName());
		Assert.assertEquals(getUpdatedResponse.jsonPath().getString("status"), updatedPet.getStatus());
		Assert.assertEquals(getUpdatedResponse.jsonPath().getInt("category.id"), updatedPet.getCategory().getId());
		Assert.assertEquals(getUpdatedResponse.jsonPath().getString("category.name"), updatedPet.getCategory().getName());
		Assert.assertEquals(getUpdatedResponse.jsonPath().getList("photoUrls"), updatedPet.getPhotoUrls());
		List<Map<String, Object>> actualTags = getUpdatedResponse.jsonPath().getList("tags");

		if (actualTags != null && !actualTags.isEmpty()) {
			for (int i = 0; i < actualTags.size(); i++) {
				Map<String, Object> actualTag = actualTags.get(i);
				Assert.assertEquals(actualTag.get("id"), updatedPet.getTags().get(i).getId());
				Assert.assertEquals(actualTag.get("name"), updatedPet.getTags().get(i).getName());
			}
		}
	}
	
	@Test(priority = 5, dependsOnMethods = {"getUpdatedPet"}, retryAnalyzer = api.utils.RetryAnalyzer.class)
	@Description("Delete an existing pet and verify status code")
	public void deleteExistingPet() throws InterruptedException {
		Response deleteValidPetResponse = petService.deletePet(petId);
		Assert.assertEquals(deleteValidPetResponse.statusCode(), 200);
		Allure.addAttachment("Response Body", deleteValidPetResponse.asPrettyString());
	}
	
	@Test(priority = 6, dependsOnMethods = {"deleteExistingPet"}, retryAnalyzer = api.utils.RetryAnalyzer.class)
	@Description("Delete a non-existing pet and verify status code")
	public void deleteNonExistingPet() throws InterruptedException {
		Response deleteValidPetResponse = petService.deletePet(petId);
		Assert.assertEquals(deleteValidPetResponse.statusCode(), 404);
		Allure.addAttachment("Response Body", deleteValidPetResponse.asPrettyString());
	}
}
