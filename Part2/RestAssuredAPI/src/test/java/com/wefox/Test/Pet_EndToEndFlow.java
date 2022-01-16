package com.wefox.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class Pet_EndToEndFlow {

    @Test(description = "To create a new pet", priority = 0)
    public void createPet()
    {


        String requestBody = "{\n" +
                "  \"id\": "+ DataUtils.NewPetId +",\n" +
                "  \"name\": \""+DataUtils.NewPetName+"\",\n" +
                "  \"category\": {\n" +
                "    \"id\": "+DataUtils.CategoryId+",\n" +
                "    \"name\": \""+DataUtils.CategoryName+"\"\n" +
                "  },\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "    \"id\": " +DataUtils.TagId+",\n" +
                "    \"name\": \""+DataUtils.TagName+"\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"" +DataUtils.Status+"\"}";



        // GIVEN
        given()
                .baseUri(RestAssuredUtils.BaseUrl).header("Accept","application/json")
                .contentType(ContentType.JSON).and()
                .body(requestBody).
        // WHEN
        when()
                 .post("/pet").
        // THEN
        then()
                .statusCode(200)
                .body("id", equalTo(DataUtils.NewPetId))
                .body("name", equalTo(DataUtils.NewPetName))
                .body("status", equalTo(DataUtils.Status))
                .body("category.name", equalTo(DataUtils.CategoryName))
                .body("category.id", equalTo(DataUtils.CategoryId))
                .body("tags.id[0]", equalTo(DataUtils.TagId))
                .body("tags.name[0]", equalTo(DataUtils.TagName));

    }



    @Test(description = "To verify a new pet", priority = 1)
    public void getPetsById()
    {


      String GetPetsByIdUrl = RestAssuredUtils.BaseUrl + "/pet/" +DataUtils.NewPetId;

      given()
              .baseUri(RestAssuredUtils.BaseUrl).header("Accept","application/json")
              .contentType(ContentType.JSON).
      when()
              .get(GetPetsByIdUrl).
      then().
            assertThat()
              .statusCode(200)
              .body("id", equalTo(DataUtils.NewPetId))
              .body("name", equalTo(DataUtils.NewPetName))
              .body("status", equalTo(DataUtils.Status))
              .body("category.name", equalTo(DataUtils.CategoryName))
              .body("category.id", equalTo(DataUtils.CategoryId))
              .body("tags.id[0]", equalTo(DataUtils.TagId))
              .body("tags.name[0]", equalTo(DataUtils.TagName));
    }


    @Test(description = "To find pets by Tags", priority = 2)
    public void getPetsByTags()
    {


        String GetPetsByTagsUrl = RestAssuredUtils.BaseUrl + "/pet/findByTags" ;

        given().queryParam("tags",DataUtils.TagName)
                .baseUri(RestAssuredUtils.BaseUrl).header("Accept","application/json")
                .contentType(ContentType.JSON).
        when()
                .get(GetPetsByTagsUrl).
        then()
              .assertThat()
                .statusCode(200);
    }

    @Test(description = "To verify pets by status", priority = 3)
    public void getPetsByStatus()
    {


        String GetPetsByStatusUrl = RestAssuredUtils.BaseUrl + "/pet/findByStatus" ;
        String PetStatus = "available";

        Response response =

        given()
                .queryParam("status",PetStatus)
                .baseUri(RestAssuredUtils.BaseUrl).header("Accept","application/json")
                .contentType(ContentType.JSON).

        when()
                .get(GetPetsByStatusUrl).
        then()
                .extract().response();

       Assert.assertTrue(response.getStatusCode()==200);

        // put all of the states into a list
        List<String> checkstatus = response.path("status");

        // check that they are all 'available/sold/pending'
        for (String petstatus : checkstatus)
        {
            Assert.assertEquals(petstatus,PetStatus);
        }


    }


    @Test(description = "To update an existing pet", priority = 4)
    public void updatePet() throws InterruptedException {
        Thread.sleep(10000);

        String requestBody = "{\n" +
                "  \"id\": "+ DataUtils.NewPetId +",\n" +
                "  \"name\": \""+DataUtils.updatePetName+"\",\n" +
                "  \"category\": {\n" +
                "    \"id\": "+DataUtils.CategoryId+",\n" +
                "    \"name\": \""+DataUtils.CategoryName+"\"\n" +
                "  },\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "    \"id\": " +DataUtils.TagId+",\n" +
                "    \"name\": \""+DataUtils.TagName+"\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"" +DataUtils.Status+"\"}";



        // GIVEN
        given()
                .baseUri(RestAssuredUtils.BaseUrl).header("Accept","application/json")
                .contentType(ContentType.JSON).and()
                .body(requestBody).
        // WHEN
        when()
                .put("/pet").
        // THEN
        then()
                .statusCode(200)
                .body("id", equalTo(DataUtils.NewPetId))
                .body("name", equalTo(DataUtils.updatePetName))
                .body("status", equalTo(DataUtils.Status))
                .body("category.name", equalTo(DataUtils.CategoryName))
                .body("category.id", equalTo(DataUtils.CategoryId))
                .body("tags.id[0]", equalTo(DataUtils.TagId))
                .body("tags.name[0]", equalTo(DataUtils.TagName));

    }


    @Test(description = "To delete a pet", priority = 5)
    public void deletePetsById()
    {


        String GetPetsByIdUrl = RestAssuredUtils.BaseUrl + "/pet/" +DataUtils.NewPetId;

        given()
                .baseUri(RestAssuredUtils.BaseUrl).header("Accept","application/json")
                .contentType(ContentType.JSON).
        when()
                .delete(GetPetsByIdUrl).
        then().
                assertThat()
                .statusCode(200)
                .body(equalTo("Pet deleted"));
    }


}
