package org.mql.java.application.models;

import java.util.List;
import java.util.Vector;

import org.mql.java.application.enumerations.Visibility;

public class ClassModel {
	private String name;
	private Visibility visibility; 
    private String packageName;
	private ClassModel superClass;
	//private List<Constructor> constructors = new Vector<>();
    private List<FieldModel> fields = new Vector<>();
    private List<MethodModel> methods = new Vector<>();
    private List<ClassModel> internClasses = new Vector<>();
    private List<Class<?>> interfaces = new Vector<>();
    private List<RelationModel> relations = new Vector<>();



    public ClassModel(String name, String packageName) {
        this.name = name;
        this.packageName = packageName;
        this.fields = fields;
        this.methods = methods;
    }

    public ClassModel(String name) {
    	super();
    	this.name = name;
    }

	public ClassModel(String name, Visibility visibility, String packageName, ClassModel superClass,
			List<FieldModel> fields, List<MethodModel> methods, List<ClassModel> internClasses, List<Class<?>> interfaces,
			List<RelationModel> relations) {
		super();
		this.name = name;
		this.visibility = visibility;
		this.packageName = packageName;
		this.superClass = superClass;
		this.fields = fields;
		this.methods = methods;
		this.internClasses = internClasses;
		this.interfaces = interfaces;
		this.relations = relations;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Visibility getVisibility() {
	    return visibility;
	}

	public void setVisibility(Visibility visibility) {
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

	public List<Class<?>> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(List<Class<?>> interfaces) {
		this.interfaces = interfaces;
	}

	public List<RelationModel> getRelations() {
		return relations;
	}

	public void setRelations(List<RelationModel> relations) {
		this.relations = relations;
	}

	public void addRelation(RelationModel relation) {
	        relations.add(relation);
	}
  
	public void addField(FieldModel field) {
	        fields.add(field);
	    }
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("Classe : ").append(name).append("\n");
	    if (superClass != null) {
	        sb.append("    Héritage : ").append(superClass.getName()).append("\n");
	    }
	    sb.append("    Champs : \n");
	    for (FieldModel field : fields) {
	        sb.append("        ").append(field).append("\n");
	    }
	    sb.append("    Méthodes : \n");
	    for (MethodModel method : methods) {
	        sb.append("        ").append(method).append("\n");
	    }
	    sb.append("    Relations : \n");
	    for (RelationModel relation : relations) {
	        sb.append("        ").append(relation).append("\n");
	    }
	    return sb.toString();
	}

}  
