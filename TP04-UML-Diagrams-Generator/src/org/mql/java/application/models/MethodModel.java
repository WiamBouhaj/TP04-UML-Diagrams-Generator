package org.mql.java.application.models;

import java.util.List;

import org.mql.java.application.enumerations.Visibility;

public class MethodModel {
	    private String name;
	    private String returnType;
	    private Visibility visibility; 
	   // private List<String> parameters;
	    private List<String> parameters;

	    public MethodModel(String name, String returnType, Visibility visibility, List<String> list) {
	        this.name = name;
	        this.returnType = returnType;
	        this.visibility = visibility;
	        this.parameters = list;
	    }

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getReturnType() {
			return returnType;
		}

		public void setReturnType(String returnType) {
			this.returnType = returnType;
		}

		public Visibility getVisibility() {
			return visibility;
		}

		public void setVisibility(Visibility visibility) {
			this.visibility = visibility;
		}

		public List<String> getParameters() {
			return parameters;
		}

		public void setParameters(List<String> parameters) {
			this.parameters = parameters;
		}

}
