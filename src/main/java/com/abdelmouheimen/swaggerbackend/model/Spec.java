package com.abdelmouheimen.swaggerbackend.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Spec {

	private String summary;
	private String description;
	private String operationId;
	private List<Parameter> parameters;
}
