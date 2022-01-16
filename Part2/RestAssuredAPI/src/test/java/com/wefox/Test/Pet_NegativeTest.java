package com.wefox.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class Pet_NegativeTest {


    @Test(description = "To verify pet with non existing Id", priority = 0)
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
              .statusCode(404)
              .body(equalTo("Pet not found"));
    }

    @Test(description = "To create a new pet with invalid body", priority = 1)
    public void createPet()
    {


         String requestBody = "" +
                 "  \"id\": " +",\n" +
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
                .statusCode(400)
                .statusLine(equalTo("HTTP/1.1 400 Bad Request"))
                .body(equalTo("{\"code\":400,\"message\":\"Input error: unable to convert input to io.swagger.petstore.model.Pet\"}"));
    }

    @Test(description = "To verify a new pet with incorrect endpoint", priority = 2)
    public void getPetsByTags()
    {


        String GetPetsByTagsUrl = RestAssuredUtils.BaseUrl + "/pet/findByTag" ;


        given().queryParam("tags",DataUtils.TagName)
                .baseUri(RestAssuredUtils.BaseUrl).header("Accept","application/json")
                .contentType(ContentType.JSON).

        when()
                .get(GetPetsByTagsUrl).
        then()
              .assertThat()
                .statusCode(400);
    }

    @Test(description = "To verify pets by invalid status", priority = 2)
    public void getPetsByStatus()
    {


        String GetPetsByStatusUrl = RestAssuredUtils.BaseUrl + "/pet/findByStatus" ;
        String PetStatus = "ready";

        Response response =

        given()
                .queryParam("status",PetStatus)
                .baseUri(RestAssuredUtils.BaseUrl).header("Accept","application/json")
                .contentType(ContentType.JSON).
        when()
                .get(GetPetsByStatusUrl).
        then().
                extract().response();

        Assert.assertTrue(response.getStatusCode()==400);
        Assert.assertTrue(response.getBody().asString().equals("{\"code\":400,\"message\":\"Input error: query parameter `status value `ready` is not in the allowable values `[available, pending, sold]`\"}"));




    }


    @Test(description = "To create a new pet with invalid body", priority = 3)
    public void updatePet()
    {


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
                .statusCode(404);

    }



}
