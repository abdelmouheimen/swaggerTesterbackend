package com.abdelmouheimen.swaggerbackend.service;

import java.util.List;

import com.abdelmouheimen.swaggerbackend.model.ETypeParameter;
import com.abdelmouheimen.swaggerbackend.model.Parameter;
import com.abdelmouheimen.swaggerbackend.model.Path;
import com.abdelmouheimen.swaggerbackend.model.Scenario;

public class PathCreator {
	
	/**
	 * remove the first "/" in the pathUrl</br>
	 * example: /pet/2 ===> pet/2
	 * @param path
	 * @return
	 */
	private static String ajusterPath(String path) {
		String first = path.substring(0,1);
		if("/".equals(first))
			return path.substring(1);
		return path;
	}
	
	
	public static String createGetPath(Path path) {
		List<Parameter> parameters = path.getSpec().getParameters();
		String basePath = ajusterPath(path.getPath());
		for(Parameter param: parameters) {
			if(ETypeParameter.PATH.name().equalsIgnoreCase(param.getIn())) {
				basePath = basePath.replace("{"+param.getName()+"}", path.getTestValues().get(param.getName()));
				path.removeFromTestValuesByKey(param.getName());
			}
		}
		
		return basePath;
	}

}
