package com.abdelmouheimen.swaggerbackend.service;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abdelmouheimen.swaggerbackend.model.Assertion;
import com.abdelmouheimen.swaggerbackend.model.EHTTPMethod;
import com.abdelmouheimen.swaggerbackend.model.EOnAssertion;
import com.abdelmouheimen.swaggerbackend.model.ETypeAssertion;
import com.abdelmouheimen.swaggerbackend.model.Path;
import com.abdelmouheimen.swaggerbackend.model.Response;
import com.abdelmouheimen.swaggerbackend.model.Scenario;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.github.yantrashala.sc.config.ClientConfigurer;
import utils.ScenarisExtractor;

@Service
public class WebServicesCaller {

	@Autowired
	ClientConfigurer clientConfigurer;
	
	
	public void mainService() throws IOException {
		Map<String, List<Scenario>> scenariosListByName = callWebServicesFromScenaris();
		scenariosListByName.values()
		.stream()
		.flatMap(Collection::stream)
		.forEach(scenario -> verifyAssertionPaths(scenario.getPaths()));
		
		System.out.println(scenariosListByName);
	}
	
	/**
	 * call webSerbices from all scenrio files
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public Map<String, List<Scenario>> callWebServicesFromScenaris() throws IOException {
		Map<String, List<Scenario>> scenaris = ScenarisExtractor.getScenarisFromResources();
		scenaris.values()
				.stream()
				.flatMap(Collection::stream)
				.forEach(scenario -> {
					try {
						traitScenario(scenario);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
		
		return scenaris;
	}
	
	/**
	 * call all paths of scenario passed in parameter
	 * @param scenario
	 * @throws IOException 
	 */
	private void traitScenario(Scenario scenario) throws IOException {
		List<Path> paths = scenario.getPaths();
		for(Path path: paths) {
			EHTTPMethod method = EHTTPMethod.valueOf(path.getMethod());
			switch(method) {
			case GET: Map<String, String> map = path.getTestValues();
					  feign.Response response = clientConfigurer.configure("https://petstore.swagger.io/v2")
							  						  .getServiceResponse(PathCreator.createGetPath(path), map, new HashMap<>());
				//	  System.out.println(json);
					  path.setResponse(extractFeignResponseDetails(response));
					  break;
			}
			
			
		}
	}
	
	private Response extractFeignResponseDetails(feign.Response response) throws IOException {
		Map<String, Collection<String>> responseHeaders = response.headers();
		String responseStatusCode = String.valueOf(response.status());
		String body ="";
		try (final Reader reader = response.body().asReader()) {
		    body = IOUtils.toString(reader);
		  } catch (IOException e) {
		    System.out.println(e.getStackTrace());
		  }
//		ObjectMapper mapper = new ObjectMapper();
//		JsonNode responseBody = mapper.readTree(body); 

		Response result = new Response(responseHeaders, responseStatusCode, body);
		return result;
	}

	/**
	 * verify assertions of paths list passed in parameter
	 * @param paths
	 */
	private void verifyAssertionPaths(List<Path> paths) {
		paths.parallelStream()
			 .forEach(path -> verifyAssertionPath(path));
	}

	/**
	 * verify assertion of path passed in parameter
	 * @param path
	 */
	private void verifyAssertionPath(Path path) {
		List<Assertion> assertions = path.getAssertions();
		if(assertions == null)
			assertions = new ArrayList<>();
		assertions.stream()
				  .forEach(assertion -> verifyAssertion(assertion, path.getResponse()));
		
	}
	
	/**
	 * headers are not treated now
	 * @param assertion
	 * @param response
	 */
	private void verifyAssertion(Assertion assertion, Response response) {
		EOnAssertion on = EOnAssertion.valueOf(assertion.getOn().replace(" ", "_"));
		switch(on) {
			case status: treatAssertionWithType(assertion, response.getStatusCode());
				break;
			case body: treatAssertionWithType(assertion, response.getBody());
				break;
		}
	}

	/**
	 * treat assertion for diffent types
	 * @param assertion
	 * @param string
	 */
	private void treatAssertionWithType(Assertion assertion, String string) {
		ETypeAssertion type = ETypeAssertion.valueOf(assertion.getType().replace(" ", "_"));
		assertion.setResult(string);
		switch(type) {
		case is : assertion.setVerified(string.equals(assertion.getValue()));
				  break;
		case c : assertion.setVerified(string.contains(assertion.getValue()));
				  break;
		case nc : assertion.setVerified(!string.contains(assertion.getValue()));
		}
		
	}
}
