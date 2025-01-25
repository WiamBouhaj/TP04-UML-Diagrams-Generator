package org.mql.java.application.models;

import java.util.List;
import java.util.Vector;

public class ProjectModel {
	private String name;
    private List<PackageModel> packages = new Vector<>();
    private List<ClassModel> classes;
    private List<RelationModel> relations;
    
    public ProjectModel(String name) {
        this.name = name;
        packages = packages;
        classes = new Vector<>();
        relations = new Vector<>();
    }

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public void addPackage(PackageModel javaPackage) {
        if (javaPackage != null) {
            packages.add(javaPackage);
        }
    }
	
	public void addClass(ClassModel classModel) {
        classes.add(classModel);
    }

    public void addRelation(RelationModel relationModel) {
        relations.add(relationModel);
    }

	public List<PackageModel> getPackages() {
		return packages;
	}

    public List<ClassModel> getClasses() {
        return classes;
    }

    public List<RelationModel> getRelations() {
        return relations;
    }

	public void setPackages(List<PackageModel> packages) {
		this.packages = packages;
	}


	
	public List<ClassModel> getAllClasses() {
	    List<ClassModel> allClasses = new Vector<>();
	    for (PackageModel pk : packages) {
	        allClasses.addAll(pk.getClasses());
	    }
	    return allClasses;
	}
}
