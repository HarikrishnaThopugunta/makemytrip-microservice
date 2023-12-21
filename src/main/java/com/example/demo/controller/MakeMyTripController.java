package com.example.demo.controller;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
public class MakeMyTripController {

	@GetMapping("/")
	public String healthCheck() {
		return "MakeMyTrip Service {healthy:true}";
	}

	@GetMapping("/makemytrip-train-list")
	@CircuitBreaker(name = "MakeMyTripCircuitBreaker", fallbackMethod = "getAPIFallBack")
	public ResponseEntity<Object[]> isTrainsAvaialble() {
		System.out.println("Request Received to Make my trip app");
		RestTemplate restTemplate = new RestTemplate();
		String env = System.getenv("IRCTC_MICROSERVICE_SERVICE_HOST");
		env = Objects.nonNull(env) ? env : "http://localhost";
		System.out.println("env==============================>" + env);
		ResponseEntity<Object[]> responseEntity = restTemplate
				.getForEntity("http://localhost:9090/irctc-microservice/get-train-list", Object[].class);
//		ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(env + ":9090/get-train-list",

		Object[] objects = responseEntity.getBody();
		Arrays.asList(objects).forEach(System.out::println);
		return responseEntity;
	}

	public ResponseEntity<Object[]> getAPIFallBack(Throwable t) {
		Object[] dataArray = new Object[1];
		dataArray[0] = new String("Service Unavaliable.........");
		ResponseEntity<Object[]> re = new ResponseEntity<Object[]>(dataArray, HttpStatusCode.valueOf(200));
		return re;
	}

}
