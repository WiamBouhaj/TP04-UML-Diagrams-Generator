package org.mql.java.application.parsers;

import java.io.File;
import java.util.List;
import java.util.Vector;

import org.mql.java.application.models.ClassModel;
import org.mql.java.application.models.PackageModel;
import org.mql.java.application.models.ProjectModel;

public class PackageExplorer {
    private List<String> classNames;

    public PackageExplorer() {
        classNames = new Vector<>();
    }

    public PackageModel parse(File directory) {
        if (directory == null || !directory.exists() || !directory.isDirectory()) {
            System.out.println("Le répertoire n'existe pas ou n'est pas valide.");
            return null;
        }
        String packageName = directory.toPath()
                .toAbsolutePath()
                .toString()
                .replace(File.separator, ".");

          PackageModel packageModel = new PackageModel(packageName);

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".class")) {
                    String className = packageName + "." + file.getName().replace(".class", "");
                    classNames.add(className);

                    // Ajout de la classe analysée au modèle de package
                    ClassModel classModel = new ClassParser().parse(packageName, file.getName().replace(".class", ""));
                    if (classModel != null) {
                        packageModel.addClass(classModel);
                    }
                }
            }
        }

        return packageModel;
    }

    public List<String> getClassNames() {
        return classNames;
    }
    
    public void addPackageToProject(ProjectModel project, PackageModel packageModel) {
        if (project != null && packageModel != null) {
            project.addPackage(packageModel);
        }
    }
}
