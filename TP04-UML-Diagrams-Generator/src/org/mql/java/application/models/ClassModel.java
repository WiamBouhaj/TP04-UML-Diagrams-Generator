package org.mql.java.application.models;

import java.util.List;
import java.util.Vector;

public class ClassModel {
	private String name;
	private String visibility; 
    private String packageName;
	private ClassModel superClass;
	private List<Constructor> constructors = new Vector<>();
    private List<FieldModel> fields = new Vector<>();
    private List<MethodModel> methods = new Vector<>();
    private List<ClassModel> internClasses = new Vector<>();
    private List<String> interfaces = new Vector<>();


    public ClassModel(String name, String packageName) {
        this.name = name;
        this.packageName = packageName;
        this.fields = fields;
        this.methods = methods;
    }


	public ClassModel(String name, String visibility, String packageName, ClassModel superClass,
			List<Constructor> constructors, List<FieldModel> fields, List<MethodModel> methods,
			List<ClassModel> internClasses, List<String> interfaces) {
		super();
		this.name = name;
		this.visibility = visibility;
		this.packageName = packageName;
		this.superClass = superClass;
		this.constructors = constructors;
		this.fields = fields;
		this.methods = methods;
		this.internClasses = internClasses;
		this.interfaces = interfaces;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getVisibility() {
		return visibility;
	}


	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}


	public String getPackageName() {
		return packageName;
	}


	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}


	public ClassModel getSuperClass() {
		return superClass;
	}


	public void setSuperClass(ClassModel superClass) {
		this.superClass = superClass;
	}


	public List<Constructor> getConstructors() {
		return constructors;
	}


	public void setConstructors(List<Constructor> constructors) {
		this.constructors = constructors;
	}


	public List<FieldModel> getFields() {
		return fields;
	}


	public void setFields(List<FieldModel> fields) {
		this.fields = fields;
	}


	public List<MethodModel> getMethods() {
		return methods;
	}


	public void setMethods(List<MethodModel> methods) {
		this.methods = methods;
	}


	public List<ClassModel> getInternClasses() {
		return internClasses;
	}


	public void setInternClasses(List<ClassModel> internClasses) {
		this.internClasses = internClasses;
	}


	public List<String> getInterfaces() {
		return interfaces;
	}


	public void setInterfaces(List<String> interfaces) {
		this.interfaces = interfaces;
	}

}  
