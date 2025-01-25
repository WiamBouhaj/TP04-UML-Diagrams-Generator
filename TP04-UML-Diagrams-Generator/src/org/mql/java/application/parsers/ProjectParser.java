package org.mql.java.application.parsers;

import java.io.File;

import org.mql.java.application.models.PackageModel;
import org.mql.java.application.models.ProjectModel;

public class ProjectParser {
	    private PackageExplorer packageExplorer;

	    public ProjectParser(File rootDirectory) {
	        this.packageExplorer = new PackageExplorer(rootDirectory);
	    }

	    public ProjectModel parse(File projectRoot) {
	        if (projectRoot == null || !projectRoot.exists() || !projectRoot.isDirectory()) {
	            System.out.println("Le répertoire du projet n'existe pas ou n'est pas valide.");
	            return null;
	        }
	        ProjectModel projectModel = new ProjectModel(projectRoot.getName());

	        explorePackages(projectRoot, projectModel);

	        return projectModel;
	    }

	    private void explorePackages(File directory, ProjectModel projectModel) {
	        File[] files = directory.listFiles();
	        if (files != null) {
	            for (File file : files) {
	                if (file.isDirectory()) {
	                    PackageModel packageModel = packageExplorer.parse(file);

	                    if (packageModel != null) {
	                        packageExplorer.addPackageToProject(projectModel, packageModel);

	                        explorePackages(file, projectModel);
	                    }
	                }
	            }
	        }
	    }
	}
