package org.mql.java.application.models;

import java.util.List;
import java.util.Vector;

public class PackageModel {
	private String name;
    private List<ClassModel> classes = new Vector<>();
    private List<InterfaceModel> interfaces = new Vector<>();
    private List<EnumerationModel> enumerations = new Vector<>();
    private List<AnnotationModel> annotations = new Vector<>();
    private List<RelationModel> relations = new Vector<>();
    private List<PackageModel> subPackages = new Vector<>();;

    public PackageModel(String name) {
        this.name = name;
        this.classes= classes;
        this.interfaces = interfaces;
        this.enumerations = enumerations;
        this.annotations = annotations;
    }
    public PackageModel() {}
	
	public PackageModel(String name, List<ClassModel> classes, List<InterfaceModel> interfaces,
			List<EnumerationModel> enumerations, List<AnnotationModel> annotations, List<RelationModel> relations,
			List<PackageModel> subPackages) {
		super();
		this.name = name;
		this.classes = classes;
		this.interfaces = interfaces;
		this.enumerations = enumerations;
		this.annotations = annotations;
		this.relations = relations;
		this.subPackages = subPackages;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ClassModel> getClasses() {
		return classes;
	}

	public void setClasses(List<ClassModel> classes) {
		this.classes = classes;
	}

	public List<InterfaceModel> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(List<InterfaceModel> interfaces) {
		this.interfaces = interfaces;
	}

	public List<EnumerationModel> getEnumerations() {
		return enumerations;
	}

	public void setEnumerations(List<EnumerationModel> enumerations) {
		this.enumerations = enumerations;
	}

	public List<AnnotationModel> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<AnnotationModel> annotations) {
		this.annotations = annotations;
	}

	public List<RelationModel> getRelations() {
		return relations;
	}

	public void setRelations(List<RelationModel> relations) {
		this.relations = relations;
	}
	
	public void addClass(ClassModel classModel) {
        if (classModel != null) {
            classes.add(classModel);
        }
    }
	public void addPackage(PackageModel packageModel) {
        subPackages.add(packageModel);
    }
	public List<PackageModel> getSubPackages() {
		return subPackages;
	}
	public void setSubPackages(List<PackageModel> subPackages) {
		this.subPackages = subPackages;
	}
}
