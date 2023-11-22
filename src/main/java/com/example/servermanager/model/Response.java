package com.example.servermanager.model;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;


@Data
@AllArgsConstructor
@SuperBuilder
@JsonInclude(NON_NULL)

public class Response {
	
	protected LocalDateTime timeStamp;
	protected int statusCode;
	protected HttpStatus status;
	protected String reason;
	protected String message;
	protected String developerMessage;
	protected Map<?, ?> data;

}
