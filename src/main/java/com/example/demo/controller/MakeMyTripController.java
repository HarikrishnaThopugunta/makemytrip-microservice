package com.example.demo.controller;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MakeMyTripController {
	
	@GetMapping("/")
	public String healthCheck() {
		return "MakeMyTrip Service {healthy:true}";
	}
	
	@GetMapping("/makemytrip-train-list")
	public ResponseEntity<Object[]> isTrainsAvaialble() {
		System.out.println("Request Received to Make my trip app");
		RestTemplate restTemplate = new RestTemplate();	
		String env = System.getenv("IRCTC_MICROSERVICE_SERVICE_HOST");
		env = Objects.nonNull(env)? env :"http://localhost";
		System.out.println("env==============================>" + env);
		ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(env +":9090/get-train-list", Object[].class);
		Object[] objects = responseEntity.getBody();
		Arrays.asList(objects).forEach(System.out::println);	
		return responseEntity;
	}

}
