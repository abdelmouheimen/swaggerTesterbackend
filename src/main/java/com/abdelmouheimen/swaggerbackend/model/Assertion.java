package com.abdelmouheimen.swaggerbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Assertion {

	private int id;
	private String on;
	private String type;
	private String value;
	
	@JsonIgnore
	private String result;
	@JsonIgnore
	private boolean isVerified;
}
