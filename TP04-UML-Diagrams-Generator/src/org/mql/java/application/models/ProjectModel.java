package org.mql.java.application.models;

import java.util.List;
import java.util.Vector;

public class ProjectModel {
	private String name;
    private List<PackageModel> packages = new Vector<>();

    public ProjectModel(String name) {
        this.name = name;
        this.packages = packages;
    }

//    
//	public ProjectModel(String name, List<PackageModel> packages) {
//		//super();
//		this.name = name;
//		this.packages = packages;
//	}
	
	public ProjectModel(String name, List<PackageModel> packages) {
        this.name = name;
        this.packages = packages != null ? packages : new Vector<>();
    }


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public List<PackageModel> getPackages() {
		return packages;
	}


	public void setPackages(List<PackageModel> packages) {
		this.packages = packages;
	}

	public ProjectModel() {
	}

	public void addPackage(PackageModel javaPackage) {
        if (javaPackage != null) {
            packages.add(javaPackage);
        }
    }
//	public void addPackageToProject(ProjectModel project, PackageModel packageModel) {
//        project.addPackage(packageModel);
//    }
	
	// Ajouter un package via un projet
    public void addPackageToProject(ProjectModel project, PackageModel packageModel) {
        if (project != null && packageModel != null) {
            project.addPackage(packageModel);
        }
    }

    // Récupérer toutes les classes du projet
    public List<ClassModel> getAllClasses() {
        List<ClassModel> allClasses = new Vector<>();
        for (PackageModel pk : packages) {
            allClasses.addAll(pk.getClasses());
        }
        return allClasses;
    }
	
//	public List<ClassModel> getAllClasses() {
//	    List<ClassModel> allClasses = new Vector<>();
//	    for (PackageModel pk : packages) {
//	        allClasses.addAll(pk.getClasses());
//	    }
//	    return allClasses;
//	}
}
