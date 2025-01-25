package org.mql.java.application.models;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AnnotationModel {
	
	private String name;
	    private RetentionPolicy retentionPolicy;
	    private boolean isInherited;
	    private Map<String, String> attributes;

	 public AnnotationModel(String name) {
		 attributes = new HashMap<>();
	        try {
	            Class<?> annotationClass = Class.forName(name);

	            name = annotationClass.getSimpleName();

	            Retention retention = annotationClass.getAnnotation(Retention.class);
	            setRetentionPolicy((retention != null) ? retention.value() : RetentionPolicy.CLASS);

	            setInherited(annotationClass.isAnnotationPresent(Inherited.class));

	            for (Method method : annotationClass.getDeclaredMethods()) {
	                attributes.put(method.getName(), method.getReturnType().getSimpleName());
	            }
	    	}
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	    }


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public RetentionPolicy getRetentionPolicy() {
		return retentionPolicy;
	}


	public Map<String, String> getAttributes() {
		return attributes;
	}


	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}


	public void setRetentionPolicy(RetentionPolicy retentionPolicy) {
		this.retentionPolicy = retentionPolicy;
	}


	public boolean isInherited() {
		return isInherited;
	}


	public void setInherited(boolean isInherited) {
		this.isInherited = isInherited;
	}


	@Override
	public String toString() {
		return "AnnotationModel [name=" + name + ", retentionPolicy=" + retentionPolicy + ", isInherited=" + isInherited
				+ ", attributes=" + attributes + "]";
	}
	
}
