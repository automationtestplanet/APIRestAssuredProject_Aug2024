package restapi.tests;

import java.util.List;
import java.util.Map;

import io.restassured.specification.RequestSpecification;

public class UserService2 extends RestApiUtils2 {

	public List<Map<String, Object>> getlistOfUsers(RequestSpecification rs, String endPoint, int statusCode) {
		return get(rs, statusCode).extract().jsonPath().getList("data");
	}

	public List<Map<String, Object>> getListOfUsers(RequestSpecification rs, Map<String, Object> queryParams,
			int statusCode) {
		return getWithQueryParameters(rs,  queryParams, statusCode).extract().jsonPath().getList("data");
	}

	public Map<String, Object> getSingleUser(RequestSpecification rs, String endPoint, Map<String, Object> pathParams,
			int statusCode) {
		return getWithPathParameters(rs, endPoint, pathParams, statusCode).extract().jsonPath().get("data");
	}

	public Map<String, String> createUser(RequestSpecification rs, String endPoint, Map<String, Object> headers,
			String requestBody, int statusCode) {
		return postWithRequestBodyAndHeaders(rs, endPoint, headers, requestBody, statusCode).extract().as(Map.class);
	}

	public Map<String, String> createUser(RequestSpecification rs, String endPoint, Map<String, Object> headers,
			Object requestBody, int statusCode) {
		return postWithRequestBodyAndHeaders(rs, endPoint, headers, requestBody, statusCode).extract().as(Map.class);
	}

	public Map<String, String> updateUser(RequestSpecification rs, String endPoint, Map<String, Object> pathParams,
			Map<String, Object> headers, String requestBody, int statusCode) {
		return putWithPathParamsAndRequestBodyAndHeaders(rs, endPoint, pathParams, headers, requestBody, statusCode)
				.extract().as(Map.class);
	}

	public Map<String, String> updateUser(RequestSpecification rs, String endPoint, Map<String, Object> pathParams,
			Map<String, Object> headers, Object requestBody, int statusCode) {
		return putWithPathParamsAndRequestBodyAndHeaders(rs, endPoint, pathParams, headers, requestBody, statusCode)
				.extract().as(Map.class);
	}

	public Map<String, String> updateUserWithPartialRequest(RequestSpecification rs, String endPoint,
			Map<String, Object> pathParams, Map<String, Object> headers, String requestBody, int statusCode) {
		return patchWithPathParamsAndRequestBodyAndHeaders(rs, endPoint, pathParams, headers, requestBody, statusCode)
				.extract().as(Map.class);
	}

	public Map<String, String> updateUserWithPartialRequest(RequestSpecification rs, String endPoint,
			Map<String, Object> pathParams, Map<String, Object> headers, Object requestBody, int statusCode) {
		return patchWithPathParamsAndRequestBodyAndHeaders(rs, endPoint, pathParams, headers, requestBody, statusCode)
				.extract().as(Map.class);
	}

	public String deleteUser(RequestSpecification rs, String endPoint, Map<String, Object> pathParams, int statusCode) {
		return deleteWithPathParameters(rs, endPoint, pathParams, statusCode).extract().asString();
	}

	public String deleteUser(RequestSpecification rs, String endPoint, Map<String, Object> pathParams,
			Map<String, Object> headers, int statusCode) {
		return deleteWithPathParametersAndHeaders(rs, endPoint, pathParams, headers, statusCode).extract().asString();
	}

	public String deleteUser(RequestSpecification rs, String endPoint, Map<String, Object> pathParams,
			Map<String, Object> headers, String requestBody, int statusCode) {
		return deleteWithRequestBodyAndPathParametersAndHeaders(rs, endPoint, pathParams, headers, requestBody,
				statusCode).extract().asString();
	}

	public String deleteUser(RequestSpecification rs, String endPoint, Map<String, Object> pathParams,
			Map<String, Object> headers, Object requestBody, int statusCode) {
		return deleteWithRequestBodyAndPathParametersAndHeaders(rs, endPoint, pathParams, headers, requestBody,
				statusCode).extract().asString();
	}
}
