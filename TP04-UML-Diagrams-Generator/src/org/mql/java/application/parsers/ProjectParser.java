package org.mql.java.application.parsers;

import java.io.File;

import org.mql.java.application.models.PackageModel;
import org.mql.java.application.models.ProjectModel;

public class ProjectParser {
	    private PackageExplorer packageExplorer;

	    public ProjectParser() {
	        this.packageExplorer = new PackageExplorer();
	    }

	    public ProjectModel parse(File projectRoot) {
	        if (projectRoot == null || !projectRoot.exists() || !projectRoot.isDirectory()) {
	            System.out.println("Le répertoire du projet n'existe pas ou n'est pas valide.");
	            return null;
	        }

	        // Créer un modèle de projet
	        ProjectModel projectModel = new ProjectModel(projectRoot.getName());

	        // Explorer tous les sous-dossiers
	        explorePackages(projectRoot, projectModel);

	        return projectModel;
	    }

	    private void explorePackages(File directory, ProjectModel projectModel) {
	        File[] files = directory.listFiles();
	        if (files != null) {
	            for (File file : files) {
	                if (file.isDirectory()) {
	                    // Analyser le package
	                    PackageModel packageModel = packageExplorer.parse(file);

	                    if (packageModel != null) {
	                        // Ajouter le package au modèle de projet
	                        packageExplorer.addPackageToProject(projectModel, packageModel);

	                        // Exploration récursive pour les sous-packages
	                        explorePackages(file, projectModel);
	                    }
	                }
	            }
	        }
	    }
	}

//public class ProjectParser {
//    private String projectPath;
//
//    public ProjectParser(String projectPath) {
//        this.projectPath = projectPath;
//    }
//
//    public ProjectModel parse(String projectName) {
//    	
//        ProjectModel project = new ProjectModel(projectName);
//        List<ClassModel> classModels = project.getAllClasses(); // Implémentez cette méthode pour récupérer toutes les classes.
//
//        File root = new File(projectPath);
//
//        if (root.exists() && root.isDirectory()) {
//            // Parcours des sous-dossiers représentant des packages
//            for (File packageDir : root.listFiles()) {
//                if (packageDir.isDirectory()) {
//                    // Convertit le chemin en nom de package
//                    String packageName = packageDir.getAbsolutePath()
//                        .replace(projectPath + File.separator, "") // Retire le chemin racine
//                        .replace(File.separator, "."); // Convertit les séparateurs en '.'
//
//                    // Utilise PackageExplorer pour analyser le package
//                    PackageExplorer explorer = new PackageExplorer();
//                    explorer.parse(packageDir);
//
//                    // Crée un PackageModel avec les classes trouvées
//                    PackageModel packageModel = new PackageModel(packageName);
//                    for (String className : explorer.getClassNames()) {
//                        // Sépare le package et le nom de la classe
//                        String simpleClassName = className.substring(className.lastIndexOf('.') + 1);
//
//                        // Analyse la classe avec ClassParser
//                        ClassModel classModel = new ClassParser().parse(packageName, simpleClassName);
//                        if (classModel != null) {
//                            packageModel.addClass(classModel);
//                        }
//                    }
//
//                    // Ajoute le package au projet
//                    project.addPackage(packageModel);
//                }
//            }
//        } else {
//            System.out.println("Le répertoire " + projectPath + " n'existe pas ou n'est pas un répertoire valide.");
//        }
//
//        return project;
//    }
//}
