package com.abdelmouheimen.swaggerbackend.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Path {

	private int id;
	private String method;
	private String path;
	private String summary;
	private Map<String, String> testValues;
	private List<Assertion> assertions;
	private Spec spec;
	
	@JsonIgnore
	private Response response;
	
	
	public void removeFromTestValuesByKey(String key) {
		if(testValues!=null){
			this.testValues.remove(key);
		}
	}
}
