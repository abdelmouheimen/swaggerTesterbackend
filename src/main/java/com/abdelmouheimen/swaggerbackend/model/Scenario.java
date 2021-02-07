package com.abdelmouheimen.swaggerbackend.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Scenario {
 private String name;
 private String specUrl;
 private int id;
 private List<Path> paths;
}
