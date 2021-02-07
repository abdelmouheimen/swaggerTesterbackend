package utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abdelmouheimen.swaggerbackend.model.Scenario;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class Main {

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		Map<String, List<Scenario>> scenaris = ScenarisExtractor.getScenarisFromResources();
		
	}
}
