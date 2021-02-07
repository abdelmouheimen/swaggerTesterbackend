package com.abdelmouheimen.swaggerbackend.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Parameter {

	private String name;
	private String in;
	private String description;
	private Map<String, Object> schema;
}
