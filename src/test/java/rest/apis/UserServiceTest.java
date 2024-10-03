package rest.apis;

import static io.restassured.RestAssured.given;

import java.io.FileReader;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import junit.framework.Assert;
import pojoclasses.CreateUser;
import pojoclasses.Data;

public class UserServiceTest {

	@Test
	public void getLisOfUsersWithGET() {
		Response res = given().when().get("https://reqres.in/api/users?page=2").thenReturn();
		Assert.assertEquals(res.getStatusCode(), 200);
		JsonPath jsonpath = res.body().jsonPath();
		List<Map<String, Object>> dataList = jsonpath.getList("data");
		System.out.println(dataList);
		for (Map<String, Object> eachData : dataList) {
			System.out.println(eachData);
		}
	}

	@Test
	public void getListOfUsersWithQueryParametersWithGET() {

		RestAssured.baseURI = "https://reqres.in";

		Response res = given().with().queryParam("page", "2").when().get("/api/users").thenReturn();
		Assert.assertEquals(res.getStatusCode(), 200);
		JsonPath jsonpath = res.body().jsonPath();
		List<Map<String, Object>> dataList = jsonpath.getList("data");
		System.out.println(dataList);

		for (Map<String, Object> eachData : dataList) {
			System.out.println(eachData);
		}

		System.out.println("------------------------------------------------------------");
		Map<String, String> queryParams = new HashMap<>();
		queryParams.put("page", "2");

		Response res2 = given().with().queryParams(queryParams).when().get("/api/users").thenReturn();

		JsonPath jsonpath2 = res2.body().jsonPath();
		List<Map<String, Object>> dataList2 = jsonpath2.getList("data");
		System.out.println(dataList2);

		for (Map<String, Object> eachData : dataList2) {
			System.out.println(eachData);
		}
	}

	@Test
	public void getListOfUsersWithPathParametersWithGET() {

		RestAssured.baseURI = "https://reqres.in";
		Response res = given().with().pathParam("id", "2").when().get("/api/users/{id}").thenReturn();
		Assert.assertEquals(res.getStatusCode(), 200);
		JsonPath jsonpath = res.body().jsonPath();
		System.out.println(jsonpath.get("data").toString());

		System.out.println("-------------------------------------------------------------------------");
		Map<String, String> pathParams = new HashMap<>();
		pathParams.put("id", "3");
		Response res2 = given().with().pathParams(pathParams).when().get("/api/users/{id}").thenReturn();
		Assert.assertEquals(res2.getStatusCode(), 200);
		JsonPath jsonpath2 = res2.body().jsonPath();
		System.out.println(jsonpath2.get("data").toString());
	}

	@Test
	public void getListOfUsersWithGET() throws Exception {

		RestAssured.baseURI = "https://reqres.in";
		Response res = given().with().pathParam("id", "2").when().get("/api/users/{id}").thenReturn();
		Assert.assertEquals(res.getStatusCode(), 200);

		Map data = (Map) (res.body().as(Map.class).get("data"));
		System.out.println(data.get("id"));
		System.out.println(data.get("email"));
		System.out.println(data.get("first_name"));
		System.out.println(data.get("last_name"));
		System.out.println(data.get("avatar"));

		Assert.assertEquals((int) data.get("id"), 2);

		Map<String, Map<String, Object>> totalRes = given().with().pathParam("id", "2").when().get("/api/users/{id}")
				.then().extract().as(Map.class);

		Map<String, Object> data1 = totalRes.get("data");

		System.out.println(data1.get("id"));
		System.out.println(data1.get("email"));
		System.out.println(data1.get("first_name"));
		System.out.println(data1.get("last_name"));
		System.out.println(data1.get("avatar"));

		Map<String, Object> data3 = (Map) given().with().pathParam("id", "2").when().get("/api/users/{id}").then().log()
				.ifValidationFails().body(IsNull.notNullValue()).statusCode(200).extract().as(Map.class).get("data");

		System.out.println(data3.get("id"));
		System.out.println(data3.get("email"));
		System.out.println(data3.get("first_name"));
		System.out.println(data3.get("last_name"));
		System.out.println(data3.get("avatar"));
	}

	@Test
	public void createUserWithPOST() {
		RestAssured.baseURI = "https://reqres.in";

		String requestBody = "{\r\n" + "    \"name\": \"morpheus\",\r\n" + "    \"job\": \"leader\"\r\n" + "}";

		var validateRes = given().with().contentType(ContentType.JSON).header("Accept", "*/*").and().body(requestBody)
				.when().post("/api/users").then().log().ifValidationFails().body(IsNull.notNullValue()).statusCode(201);

		Map<String, String> createUserRes = validateRes.extract().as(Map.class);
		System.out.println(createUserRes);
		Assert.assertEquals(createUserRes.get("name"), "morpheus");
		Assert.assertEquals(createUserRes.get("job"), "leader");
		Assert.assertNotNull(createUserRes.get("id"));

		CreateUser createUserDetails = new CreateUser();
		createUserDetails.setName("Raju");
		createUserDetails.setJob("Architect");

		var validateRes2 = given().with().contentType(ContentType.JSON).header("Accept", "*/*").and()
				.body(createUserDetails).when().post("/api/users").then().log().ifValidationFails()
				.body(IsNull.notNullValue()).statusCode(201);

		Map<String, String> createUserRes2 = validateRes2.extract().as(Map.class);
		System.out.println(createUserRes2);
		Assert.assertEquals(createUserRes2.get("name"), createUserDetails.getName());
		Assert.assertEquals(createUserRes2.get("job"), createUserDetails.getJob());
		Assert.assertNotNull(createUserRes2.get("id"));

		try {

			String filePath = System.getProperty("user.dir")
					+ "\\src\\test\\resources\\TestData\\UserServiceTestData\\CreateUser.json";

			ObjectMapper objMapper = new ObjectMapper();
			CreateUser createUserDetailsFromFile = objMapper.readValue(Paths.get(filePath).toFile(), CreateUser.class);

			var validateRes3 = given().with().contentType(ContentType.JSON).header("Accept", "*/*").and()
					.body(createUserDetailsFromFile).when().post("/api/users").then().log().ifValidationFails()
					.body(IsNull.notNullValue()).statusCode(201);

			Map<String, String> createUserRes3 = validateRes3.extract().as(Map.class);
			System.out.println(createUserRes3);
			Assert.assertEquals(createUserRes3.get("name"), createUserDetailsFromFile.getName());
			Assert.assertEquals(createUserRes3.get("job"), createUserDetailsFromFile.getJob());
			Assert.assertNotNull(createUserRes3.get("id"));

		} catch (Exception e) {
			System.out.println("Exception Occured while Creating the user " + e.getMessage());
		}

		try {

			String filePath = System.getProperty("user.dir")
					+ "\\src\\test\\resources\\TestData\\UserServiceTestData\\CreateUser2.json";
			;

			JSONParser jParse = new JSONParser();
			FileReader jsonFile = new FileReader(filePath);
			JSONObject jObject = (JSONObject) jParse.parse(jsonFile);

			String ReqBody = String.format(jObject.toString(), "Krish", "Software Engineer");

			System.out.println(ReqBody);

			var validateRes4 = given().with().contentType(ContentType.JSON).header("Accept", "*/*").and().body(ReqBody)
					.when().post("/api/users").then().log().ifValidationFails().body(IsNull.notNullValue())
					.statusCode(201);

			Map<String, String> createUserRes4 = validateRes4.extract().as(Map.class);
			System.out.println(createUserRes4);
			Assert.assertEquals(createUserRes4.get("name"), "Krish");
			Assert.assertEquals(createUserRes4.get("job"), "Software Engineer");
			Assert.assertNotNull(createUserRes4.get("id"));

		} catch (Exception e) {
			System.out.println("Exception Occured while Creating the user " + e.getMessage());
		}
	}

	@Test
	public void updateUserWithPUT() {
		try {
			RestAssured.baseURI = "https://reqres.in";
			String filePath = System.getProperty("user.dir")
					+ "\\src\\test\\resources\\TestData\\UserServiceTestData\\UpdateUser.json";

			JSONParser jParse = new JSONParser();
			FileReader jsonFile = new FileReader(filePath);
			JSONObject jObject = (JSONObject) jParse.parse(jsonFile);

			String ReqBody = String.format(jObject.toString(), "morpheus", "zion resident");

			Map<String,String> headers= new HashMap<>();
			headers.put("Accept", "*/*");
			headers.put("Content-Type", "application/json");
			
			Map<String,Integer> pathParams = new HashMap<>();
			pathParams.put("id", 3);
			
			var res = given().with().headers(headers).pathParams(pathParams).and()
					.body(ReqBody).when().put("/api/users/{id}").then().log().ifValidationFails()
					.body(IsNull.notNullValue()).statusCode(200);

			Map<String, String> updateUserRes = res.extract().as(Map.class);
			System.out.println(updateUserRes);
			Assert.assertEquals(updateUserRes.get("name"), "morpheus");
			Assert.assertEquals(updateUserRes.get("job"), "zion resident");
		} catch (Exception e) {
			System.out.println("Exception Occured while Updating the user " + e.getMessage());
		}

	}
	
	@Test
	public void updateUserWithPATCH() {
		try {
			RestAssured.baseURI = "https://reqres.in";
			String filePath = System.getProperty("user.dir")
					+ "\\src\\test\\resources\\TestData\\UserServiceTestData\\UpdateUser2.json";

			JSONParser jParse = new JSONParser();
			FileReader jsonFile = new FileReader(filePath);
			JSONObject jObject = (JSONObject) jParse.parse(jsonFile);

			String ReqBody = String.format(jObject.toString(),"zion resident");

			Map<String,String> headers= new HashMap<>();
			headers.put("Accept", "*/*");
			headers.put("Content-Type", "application/json");
			
			Map<String,Integer> pathParams = new HashMap<>();
			pathParams.put("id", 3);
			
			var res = given().with().headers(headers).pathParams(pathParams).and()
					.body(ReqBody).when().patch("/api/users/{id}").then().log().ifValidationFails()
					.body(IsNull.notNullValue()).statusCode(200);

			Map<String, String> updateUserRes = res.extract().as(Map.class);
			System.out.println(updateUserRes);
			Assert.assertEquals(updateUserRes.get("job"), "zion resident");
		} catch (Exception e) {
			System.out.println("Exception Occured while Updating the user " + e.getMessage());
		}

	}
	
	
	@Test
	public void deleteUserWithDELETE() {
		try {
			RestAssured.baseURI = "https://reqres.in";
			
			Map<String,Integer> pathParams = new HashMap<>();
			pathParams.put("id", 3);			
			var res = given().with().pathParams(pathParams).and()
					.when().delete("/api/users/{id}").then().log().ifValidationFails()
					.body(IsNull.notNullValue()).statusCode(204);			
			Assert.assertEquals(res.extract().asString(), "");
		} catch (Exception e) {
			System.out.println("Exception Occured while Updating the user " + e.getMessage());
		}

	}


}
