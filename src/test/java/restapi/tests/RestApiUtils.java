package restapi.tests;

import static io.restassured.RestAssured.given;

import java.util.Map;

import org.hamcrest.core.IsNull;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class RestApiUtils {

	public ValidatableResponse get(RequestSpecification rs, String endPoint, int statusCode) {
		return rs.when().get(endPoint).then().log().ifValidationFails().body(IsNull.notNullValue())
				.statusCode(statusCode);
	}

	public ValidatableResponse getWithQueryParameters(RequestSpecification rs, String endPoint,
			Map<String, Object> queryParams, int statusCode) {
		return rs.with().queryParams(queryParams).when().get(endPoint).then().log().ifValidationFails()
				.body(IsNull.notNullValue()).statusCode(statusCode);
	}

	public ValidatableResponse getWithPathParameters(RequestSpecification rs, String endPoint,
			Map<String, Object> pathParams, int statusCode) {
		return rs.with().queryParams(pathParams).when()
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
