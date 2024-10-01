package rest.apis;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import junit.framework.Assert;
import pojoclasses.Data;

public class UserServiceTest {

	@Test
	public void getLisOfUsers() {
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
	public void getListOfUsersWithQueryParameters() {

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
	public void getListOfUsersWithPathParameters() {

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
	public void getListOfUsers() throws Exception {

		RestAssured.baseURI = "https://reqres.in";
		Response res = given().with().pathParam("id", "2").when().get("/api/users/{id}").thenReturn();
		Assert.assertEquals(res.getStatusCode(), 200);

		String dataString = res.jsonPath().get("data").toString();

		System.out.println(dataString);

		ObjectMapper objMapper = new ObjectMapper();
		Data data = objMapper.readValue(dataString, Data.class);

		System.out.println(data.getId());

	}

}
