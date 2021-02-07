package utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abdelmouheimen.swaggerbackend.model.Scenario;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ScenarisExtractor {
	
	public static void main(String[] args) throws IOException {
		Map<String, List<Scenario>> result = getScenarisFromResources();
		System.out.println(result);
	}

	
	/**
	 * transform json files in src.main.resources.scenaris to map </br>
	 *  where keys are file name and value the file value transformed to scenario Objects list
	 * @return 
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static Map<String, List<Scenario>> getScenarisFromResources() throws JsonParseException, JsonMappingException, IOException {
		Map<String, List<Scenario>> result = new HashMap<>();
		List<File> files = new ArrayList<>();
		
		String mainDirectoryPath = "K:\\sources\\test tool\\swaggerbackend\\src\\main\\resources\\scenaris";
		File mainDir = new File(mainDirectoryPath);
		if(mainDir.exists() && mainDir.isDirectory()) {
			File arr[] = mainDir.listFiles();
			files.addAll(Arrays.asList(arr));
		}
		
		for (File f : files) {
			System.out.println("------------"+f.getAbsolutePath()+"   "+f.getPath());
		    List<Scenario> scenaris = new ObjectMapper().readValue(f, new TypeReference<List<Scenario>>() {});
		    result.put(f.getName(), scenaris); 
		 }
		
		return result;
	}
	
	 private static File[] getResourceFolderFiles (String folder) {
		    ClassLoader loader = Thread.currentThread().getContextClassLoader();
		    URL url = loader.getResource(folder);
		    String path = url.getPath().replace("%20", " ");
		    return new File(path).listFiles();
		  }
}
