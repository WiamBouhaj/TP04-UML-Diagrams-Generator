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

    
	public ProjectModel(String name, List<PackageModel> packages) {
		//super();
		this.name = name;
		this.packages = packages;
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
}
