package rest.apis;

import static io.restassured.RestAssured.given;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;

public class HttpRequests {

	@Test
	public void test1() {
		// TODO Auto-generated method stub
		System.out.println("RestAssured -----> (-_-) (-_-) (-_-) (-_-)");
		Response response = given().when().get("https://reqres.in/api/unknown/2").thenReturn();
		System.out.println(response.getStatusCode());
		System.out.println(response.body().asString());		
		Assert.assertEquals(200, response.getStatusCode());
		Assert.assertNotNull(response);		
		JsonPath jsonPath =  response.body().jsonPath();		
		String data = jsonPath.get("data").toString();
		System.out.println(data);
		System.out.println(jsonPath.get("data.id").toString());
		System.out.println(jsonPath.get("data.year").toString());		
		Assert.assertEquals(2, Integer.parseInt(jsonPath.get("data.id").toString()));
		Assert.assertEquals(2002, Integer.parseInt(jsonPath.get("data.year").toString()));
//		Map headers = (Map) response.headers();
//		System.out.println(headers);
	}

}
