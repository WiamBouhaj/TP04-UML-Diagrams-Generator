package org.mql.java.application.parsers;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Vector;

import org.mql.java.application.models.ClassModel;
import org.mql.java.application.models.PackageModel;
import org.mql.java.application.models.ProjectModel;

public class PackageExplorer {
    private File rootDirectory; // Répertoire racine du projet
    private URLClassLoader classLoader;
    public PackageExplorer(File rootDirectory) {
        this.rootDirectory = rootDirectory;
        try {  
            URL url = rootDirectory.toURI().toURL(); // Obtenir l'URL du répertoire  
            this.classLoader = new URLClassLoader(new URL[]{url}); // Initialiser le classLoader  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }

    public PackageModel parse(File directory) {
        if (directory == null || !directory.exists() || !directory.isDirectory()) {
            System.out.println("Le répertoire n'existe pas ou n'est pas valide.");
            return null;
        }

        // Chemin relatif pour le package
        String packageName = directory.getPath()
            .replace(rootDirectory.getPath(), "")
            .replace(File.separator, ".")
            .replaceFirst("^\\.", ""); // Retire le "." initial

        if (packageName.startsWith("bin.")) {
            packageName = packageName.substring(4); // Supprime "bin."
        }

        // Créer le modèle de package
        PackageModel packageModel = new PackageModel(packageName);

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                   
                } else if (file.isFile() && file.getName().endsWith(".class")) {
                    // Charger la classe
                    String className = file.getName().replace(".class", "");
                    ClassModel classModel = new ClassParser(classLoader).parse(directory, packageName, className);
                    if (classModel != null) {
                        packageModel.addClass(classModel);
                    }
                }
            }
        }

        return packageModel;
    }

    // Méthode pour ajouter un package au projet
    public void addPackageToProject(ProjectModel project, PackageModel packageModel) {
        if (project != null && packageModel != null) {
            project.addPackage(packageModel);
        }
    }
}
