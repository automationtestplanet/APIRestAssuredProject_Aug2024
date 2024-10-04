package restapi.tests;

import java.util.Map;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsNull;
import org.testng.annotations.BeforeTest;

import com.google.common.net.HostAndPort;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RestApiUtils2 {
	
	ResponseSpecification resSpec;
	RequestSpecification reqSpec;
	@BeforeTest
	public void setup() {
		RestAssured.proxy.host("atp.com").port(8080);
		 RestAssured.config().sslConfig(null);
		reqSpec =  RestAssured.given().relaxedHTTPSValidation();
		reqSpec = RestAssured.given().auth().basic("", "");
		
		resSpec = RestAssured.expect().log().ifValidationFails().body(IsNull.notNullValue());
	}
	
	
	public ValidatableResponse get(RequestSpecification rs, int statusCode) {
		return (ValidatableResponse) RestAssured.given().spec(rs).when().then().log().ifValidationFails().body(IsNull.notNullValue())
				.statusCode(statusCode);
	}

	public ValidatableResponse getWithQueryParameters(RequestSpecification rs,
			Map<String, Object> queryParams, int statusCode) {
		return  (ValidatableResponse) RestAssured.given().spec(rs).with().queryParams(queryParams).when().then().spec(resSpec).statusCode(statusCode);
	}

	public ValidatableResponse getWithPathParameters(RequestSpecification rs, String endPoint,
			Map<String, Object> pathParams, int statusCode) {
		return rs.with().pathParams(pathParams).when()
				.get(endPoint + "/{" + pathParams.entrySet().iterator().next().getKey() + "}").then().log()
				.ifValidationFails().body(IsNull.notNullValue()).statusCode(statusCode);
	}

	public ValidatableResponse getWithHeaders(RequestSpecification rs, String endPoint, Map<String, Object> headers,
			int statusCode) {
		return rs.with().headers(headers).when().get(endPoint).then().log().ifValidationFails()
				.body(IsNull.notNullValue()).statusCode(statusCode);
	}

	public ValidatableResponse getWithQueryParametersAndHeaders(RequestSpecification rs, String endPoint,
			Map<String, Object> queryParams, Map<String, Object> headers, int statusCode) {
		return rs.with().queryParams(queryParams).headers(headers).when().get(endPoint).then().log().ifValidationFails()
				.body(IsNull.notNullValue()).statusCode(statusCode);
	}

	public ValidatableResponse getWithPathParametersAndHeaders(RequestSpecification rs, String endPoint,
			Map<String, Object> pathParams, Map<String, Object> headers, int statusCode) {
		return rs.with().queryParams(pathParams).headers(headers).when()
				.get(endPoint + "/{" + pathParams.entrySet().iterator().next().getKey() + "}").then().log()
				.ifValidationFails().body(IsNull.notNullValue()).statusCode(statusCode);
	}

	public ValidatableResponse getWithQueryAndPathParametersAndHeaders(RequestSpecification rs, String endPoint,
			Map<String, Object> queryParams, Map<String, Object> pathParams, Map<String, Object> headers,
			int statusCode) {
		return rs.with().queryParams(queryParams).queryParams(pathParams).headers(headers).when()
				.get(endPoint + "/{" + pathParams.entrySet().iterator().next().getKey() + "}").then().log()
				.ifValidationFails().body(IsNull.notNullValue()).statusCode(statusCode);
	}

	public ValidatableResponse postWithRequestBodyAndHeaders(RequestSpecification rs, String endPoint,
			Map<String, Object> headers, String requestBody, int statusCode) {
		return rs.with().headers(headers).and().body(requestBody).when().post(endPoint).then().log().ifValidationFails()
				.body(IsNull.notNullValue()).statusCode(statusCode);
	}

	public ValidatableResponse postWithRequestBodyAndHeaders(RequestSpecification rs, String endPoint,
			Map<String, Object> headers, Object requestBody, int statusCode) {
		return rs.with().headers(headers).and().body(requestBody).when().post(endPoint).then().log().ifValidationFails()
				.body(IsNull.notNullValue()).statusCode(statusCode);
	}

	public ValidatableResponse putWithRequestBodyAndHeaders(RequestSpecification rs, String endPoint,
			Map<String, Object> headers, String requestBody, int statusCode) {
		return rs.with().headers(headers).and().body(requestBody).when().put(endPoint).then().log().ifValidationFails()
				.body(IsNull.notNullValue()).statusCode(statusCode);
	}

	public ValidatableResponse putWithRequestBodyAndHeaders(RequestSpecification rs, String endPoint,
			Map<String, Object> headers, Object requestBody, int statusCode) {
		return rs.with().headers(headers).and().body(requestBody).when().put(endPoint).then().log().ifValidationFails()
				.body(IsNull.notNullValue()).statusCode(statusCode);

	}

	public ValidatableResponse putWithPathParamsAndRequestBodyAndHeaders(RequestSpecification rs, String endPoint,
			Map<String, Object> pathParams, Map<String, Object> headers, String requestBody, int statusCode) {
		return rs.with().pathParams(pathParams).headers(headers).and().body(requestBody).when()
				.put(endPoint + "/{" + pathParams.entrySet().iterator().next().getKey() + "}").then().log()
				.ifValidationFails().body(IsNull.notNullValue()).statusCode(statusCode);
	}

	public ValidatableResponse putWithPathParamsAndRequestBodyAndHeaders(RequestSpecification rs, String endPoint,
			Map<String, Object> pathParams, Map<String, Object> headers, Object requestBody, int statusCode) {
		return rs.with().pathParams(pathParams).headers(headers).and().body(requestBody).when()
				.put(endPoint + "/{" + pathParams.entrySet().iterator().next().getKey() + "}").then().log()
				.ifValidationFails().body(IsNull.notNullValue()).statusCode(statusCode);
	}

	public ValidatableResponse patchWithPathParamsAndRequestBodyAndHeaders(RequestSpecification rs, String endPoint,
			Map<String, Object> pathParams, Map<String, Object> headers, String requestBody, int statusCode) {
		return rs.with().pathParams(pathParams).headers(headers).and().body(requestBody).when()
				.patch(endPoint + "/{" + pathParams.entrySet().iterator().next().getKey() + "}").then().log()
				.ifValidationFails().body(IsNull.notNullValue()).statusCode(statusCode);
	}

	public ValidatableResponse patchWithPathParamsAndRequestBodyAndHeaders(RequestSpecification rs, String endPoint,
			Map<String, Object> pathParams, Map<String, Object> headers, Object requestBody, int statusCode) {
		return rs.with().pathParams(pathParams).headers(headers).and().body(requestBody).when()
				.patch(endPoint + "/{" + pathParams.entrySet().iterator().next().getKey() + "}").then().log()
				.ifValidationFails().body(IsNull.notNullValue()).statusCode(statusCode);
	}

	public ValidatableResponse deleteWithPathParameters(RequestSpecification rs, String endPoint,
			Map<String, Object> pathParams, int statusCode) {
		return rs.with().pathParams(pathParams).and().when()
				.delete(endPoint + "/{" + pathParams.entrySet().iterator().next().getKey() + "}").then().log()
				.ifValidationFails().body(IsNull.notNullValue()).statusCode(statusCode);
	}

	public ValidatableResponse deleteWithPathParametersAndHeaders(RequestSpecification rs, String endPoint,
			Map<String, Object> pathParams, Map<String, Object> headers, int statusCode) {
		return rs.with().pathParams(pathParams).headers(headers).and().when()
				.delete(endPoint + "/{" + pathParams.entrySet().iterator().next().getKey() + "}").then().log()
				.ifValidationFails().body(IsNull.notNullValue()).statusCode(statusCode);
	}

	public ValidatableResponse deleteWithRequestBodyAndPathParametersAndHeaders(RequestSpecification rs,
			String endPoint, Map<String, Object> pathParams, Map<String, Object> headers, String requestBody,
			int statusCode) {
		return rs.with().pathParams(pathParams).headers(headers).and().body(requestBody).when()
				.delete(endPoint + "/{" + pathParams.entrySet().iterator().next().getKey() + "}").then().log()
				.ifValidationFails().body(IsNull.notNullValue()).statusCode(statusCode);

	}

	public ValidatableResponse deleteWithRequestBodyAndPathParametersAndHeaders(RequestSpecification rs,
			String endPoint, Map<String, Object> pathParams, Map<String, Object> headers, Object requestBody,
			int statusCode) {
		return rs.with().pathParams(pathParams).headers(headers).and().body(requestBody).when()
				.delete(endPoint + "/{" + pathParams.entrySet().iterator().next().getKey() + "}").then().log()
				.ifValidationFails().body(IsNull.notNullValue()).statusCode(statusCode);

	}
}
