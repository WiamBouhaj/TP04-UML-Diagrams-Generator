package org.mql.java.application.parsers;

import java.io.File;

import org.mql.java.application.models.PackageModel;
import org.mql.java.application.models.ProjectModel;

public class ProjectParser {

	private String projectPath;

    public ProjectParser(String projectPath) {
        this.projectPath = projectPath;
    }

    public ProjectModel parse(String projectName) {
    	ProjectModel project = new ProjectModel(projectName);
        File root = new File(projectPath);

        if (root.exists() && root.isDirectory()) {
            for (File packageDir : root.listFiles()) {
                if (packageDir.isDirectory()) {
                    PackageModel javaPackage = new PackageExplorer().parse(packageDir);
                    project.addPackage(javaPackage);
                }
            }
        }
        return project;
    }
}


//    public void parseProject(String projectPath) throws ClassNotFoundException {
//        File root = new File(projectPath);
//        scanDirectory(root);
//    }
//
//    private void scanDirectory(File directory) throws ClassNotFoundException {
//        for (File file : directory.listFiles()) {
//            if (file.isDirectory()) {
//                scanDirectory(file);
//            } else if (file.getName().endsWith(".class")) {
//                analyzeClass(file);
//            }
//        }
//    }
//
//    private void analyzeClass(File file) throws ClassNotFoundException {
//        String className = getClassNameFromFile(file);
//        Class<?> clazz = Class.forName(className);
//
//        // Analyse des attributs et méthodes
//        for (Field field : clazz.getDeclaredFields()) {
//            System.out.println("Field: " + field.getName());
//        }
//        for (Method method : clazz.getDeclaredMethods()) {
//            System.out.println("Method: " + method.getName());
//        }
//    }
//
//    private String getClassNameFromFile(File file) {
//        String path = file.getPath();
//        return path.replace("/", ".").replace(".class", "");
//    }
//}
