package com.example.servermanager.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import com.example.servermanager.service.implementation.ServerServiceImpl;

import enumaration.Status;
import jakarta.validation.Valid;

import com.example.servermanager.model.Response;
import com.example.servermanager.model.Server;

import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
//@CrossOrigin(origins = "h")
@RestController
@RequestMapping("/server")
public class ServerResource {


	private final ServerServiceImpl serverService;


	@GetMapping("/list")
	public ResponseEntity<Response> getServers() throws InterruptedException {
		TimeUnit.SECONDS.sleep(3);
		return ResponseEntity.ok(
				Response.builder()
				.timeStamp(LocalDateTime.now())
			 	.data(Map.of("servers", serverService.list(30)))
			 	.message("Servers retrieved")
			 	.status(HttpStatus.OK)
			 	.statusCode(HttpStatus.OK.value())
	 			.build()				 	
		);
	}
	

	@GetMapping("/ping/{ipAdress}")
	public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException{
		
		Server server = serverService.ping(ipAddress);
		
		return ResponseEntity.ok(
				Response.builder()
				.timeStamp(LocalDateTime.now())
			 	.data(Map.of("servers", serverService.list(30)))
			 	.message(server.getStatus() == Status.SERVER_UP ? "Ping success" : "Ping failed")
			 	.status(HttpStatus.OK)
			 	.statusCode(HttpStatus.OK.value())
	 			.build()				 	
		);
	
	}
	
	
	
	
	@PostMapping("/post")
	public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server){
		
		return ResponseEntity.ok(
				Response.builder()
				.timeStamp(LocalDateTime.now())
			 	.data(Map.of("servers", serverService.list(30)))
			 	.message("Server Created")
			 	.status(HttpStatus.CREATED)
			 	.statusCode(HttpStatus.CREATED.value())
	 			.build()				 	
		);
	}
	
	
	
	@GetMapping("/get/{id}")
	public ResponseEntity<Response> getServer(@PathVariable("id") Long id){
		
		return ResponseEntity.ok(
				Response.builder()
				.timeStamp(LocalDateTime.now())
			 	.data(Map.of("servers", serverService.get(id)))
			 	.message("Server retrieved")
			 	.status(HttpStatus.OK)
			 	.statusCode(HttpStatus.OK.value())
	 			.build()				 	
		);
	}
	
	
	@DeleteMapping("/get/{id}")
	public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id){
	
		return ResponseEntity.ok(
				Response.builder()
				.timeStamp(LocalDateTime.now())
			 	.data(Map.of("deleted", serverService.delete(id)))
			 	.message("Server deleted")
			 	.status(HttpStatus.OK)
			 	.statusCode(HttpStatus.OK.value())
	 			.build()				 	
		);
	}
	
	
	@GetMapping(path = "/image/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
	public byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException{
		
		return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/Downloads/images" + fileName));
				
	
	}
	
}
