package com.abdelmouheimen.swaggerbackend.model;

import java.util.Collection;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
	private Map<String, Collection<String>> headers;
	private String statusCode;
	private String body;
}
