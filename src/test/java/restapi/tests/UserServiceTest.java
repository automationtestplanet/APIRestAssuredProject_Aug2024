package restapi.tests;

import static io.restassured.RestAssured.given;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.core.IsNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;
import pojoclasses.CreateUser;

public class UserServiceTest {

	RequestSpecification rs = RestAssured.given().baseUri("https://reqres.in");
	UserService userService = new UserService();

	@Test
	public void getLisOfUsersTest() {
		List<Map<String, Object>> dataList = userService.getlistOfUsers(rs, "/api/users", 200);
		Assert.assertTrue(dataList.size() > 0);
	}

	@Test
	public void getLisOfUsersByPageTest() {
		Map<String, Object> queryParam = new HashMap<>();
		queryParam.put("page", "2");
		List<Map<String, Object>> dataList = userService.getlistOfUsers(rs, "/api/users", queryParam, 200);
		Assert.assertTrue(dataList.size() > 0);
	}

	@Test
	public void getSingleUserTest() {
		Map<String, Object> pathParam = new HashMap<>();
		pathParam.put("id", "2");
		Map<String, Object> singleUserData = userService.getSingleUser(rs, "/api/users", pathParam, 200);
		Assert.assertEquals((int) singleUserData.get("id"), pathParam.get("id"));
	}

	@Test
	public void createUserWithStringDataTest() {
		Map<String, Object> headers = new HashMap<>();
		headers.put("Accept", "*/*");
		headers.put("Content-Type", "application/json");
		String requestBody = "{\r\n" + "    \"name\": \"morpheus\",\r\n" + "    \"job\": \"leader\"\r\n" + "}";
		Map<String, String> createUserRes = userService.createUser(rs, "/api/users", headers, requestBody, 201);
		Assert.assertEquals(createUserRes.get("name"), "morpheus");
		Assert.assertEquals(createUserRes.get("job"), "leader");
		Assert.assertNotNull(createUserRes.get("id"));

	}

	@Test
	public void createUserWithPojoObjectTest() {
		Map<String, Object> headers = new HashMap<>();
		headers.put("Accept", "*/*");
		headers.put("Content-Type", "application/json");
		CreateUser createUserDetails = new CreateUser();
		createUserDetails.setName("Raju");
		createUserDetails.setJob("Architect");
		Map<String, String> createUserRes = userService.createUser(rs, "/api/users", headers, createUserDetails, 201);
		Assert.assertEquals(createUserRes.get("name"), createUserDetails.getName());
		Assert.assertEquals(createUserRes.get("job"), createUserDetails.getJob());
		Assert.assertNotNull(createUserRes.get("id"));
	}

	@Test
	public void createUserWithJosnFileTest() throws Exception {
		Map<String, Object> headers = new HashMap<>();
		headers.put("Accept", "*/*");
		headers.put("Content-Type", "application/json");
		String filePath = System.getProperty("user.dir")
				+ "\\src\\test\\resources\\TestData\\UserServiceTestData\\CreateUser.json";
		ObjectMapper objMapper = new ObjectMapper();
		CreateUser createUserDetailsFromFile = objMapper.readValue(Paths.get(filePath).toFile(), CreateUser.class);
		Map<String, String> createUserRes = userService.createUser(rs, "/api/users", headers, createUserDetailsFromFile,
				201);
		Assert.assertEquals(createUserRes.get("name"), createUserDetailsFromFile.getName());
		Assert.assertEquals(createUserRes.get("job"), createUserDetailsFromFile.getJob());
		Assert.assertNotNull(createUserRes.get("id"));
	}

	@Test
	public void createUserWithJosnStringTest() throws Exception {
		Map<String, Object> headers = new HashMap<>();
		headers.put("Accept", "*/*");
		headers.put("Content-Type", "application/json");
		String filePath = System.getProperty("user.dir")
				+ "\\src\\test\\resources\\TestData\\UserServiceTestData\\CreateUser2.json";
		JSONParser jParse = new JSONParser();
		FileReader jsonFile = new FileReader(filePath);
		JSONObject jObject = (JSONObject) jParse.parse(jsonFile);
		String ReqBody = String.format(jObject.toString(), "Krish", "Software Engineer");
		Map<String, String> createUserRes = userService.createUser(rs, "/api/users", headers, ReqBody, 201);
		Assert.assertEquals(createUserRes.get("name"), "Krish");
		Assert.assertEquals(createUserRes.get("job"), "Software Engineer");
		Assert.assertNotNull(createUserRes.get("id"));
	}

//	@Test
//	public void updateUserWithPUT() {
//		try {
//			RestAssured.baseURI = "https://reqres.in";
//			String filePath = System.getProperty("user.dir")
//					+ "\\src\\test\\resources\\TestData\\UserServiceTestData\\UpdateUser.json";
//
//			JSONParser jParse = new JSONParser();
//			FileReader jsonFile = new FileReader(filePath);
//			JSONObject jObject = (JSONObject) jParse.parse(jsonFile);
//
//			String ReqBody = String.format(jObject.toString(), "morpheus", "zion resident");
//
//			Map<String, String> headers = new HashMap<>();
//			headers.put("Accept", "*/*");
//			headers.put("Content-Type", "application/json");
//
//			Map<String, Integer> pathParams = new HashMap<>();
//			pathParams.put("id", 3);
//
//			var res = given().with().headers(headers).pathParams(pathParams).and().body(ReqBody).when()
//					.put("/api/users/{id}").then().log().ifValidationFails().body(IsNull.notNullValue())
//					.statusCode(200);
//
//			Map<String, String> updateUserRes = res.extract().as(Map.class);
//			System.out.println(updateUserRes);
//			Assert.assertEquals(updateUserRes.get("name"), "morpheus");
//			Assert.assertEquals(updateUserRes.get("job"), "zion resident");
//		} catch (Exception e) {
//			System.out.println("Exception Occured while Updating the user " + e.getMessage());
//		}
//
//	}
//
//	@Test
//	public void updateUserWithPATCH() {
//		try {
//			RestAssured.baseURI = "https://reqres.in";
//			String filePath = System.getProperty("user.dir")
//					+ "\\src\\test\\resources\\TestData\\UserServiceTestData\\UpdateUser2.json";
//
//			JSONParser jParse = new JSONParser();
//			FileReader jsonFile = new FileReader(filePath);
//			JSONObject jObject = (JSONObject) jParse.parse(jsonFile);
//
//			String ReqBody = String.format(jObject.toString(), "zion resident");
//
//			Map<String, String> headers = new HashMap<>();
//			headers.put("Accept", "*/*");
//			headers.put("Content-Type", "application/json");
//
//			Map<String, Integer> pathParams = new HashMap<>();
//			pathParams.put("id", 3);
//
//			var res = given().with().headers(headers).pathParams(pathParams).and().body(ReqBody).when()
//					.patch("/api/users/{id}").then().log().ifValidationFails().body(IsNull.notNullValue())
//					.statusCode(200);
//
//			Map<String, String> updateUserRes = res.extract().as(Map.class);
//			System.out.println(updateUserRes);
//			Assert.assertEquals(updateUserRes.get("job"), "zion resident");
//		} catch (Exception e) {
//			System.out.println("Exception Occured while Updating the user " + e.getMessage());
//		}
//
//	}
//
//	@Test
//	public void deleteUserWithDELETE() {
//		try {
//			RestAssured.baseURI = "https://reqres.in";
//
//			Map<String, Integer> pathParams = new HashMap<>();
//			pathParams.put("id", 3);
//			var res = given().with().pathParams(pathParams).and().when().delete("/api/users/{id}").then().log()
//					.ifValidationFails().body(IsNull.notNullValue()).statusCode(204);
//			Assert.assertEquals(res.extract().asString(), "");
//		} catch (Exception e) {
//			System.out.println("Exception Occured while Updating the user " + e.getMessage());
//		}
//
//	}
}
