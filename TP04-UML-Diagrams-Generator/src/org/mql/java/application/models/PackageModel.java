package org.mql.java.application.models;

import java.util.List;
import java.util.Vector;

public class PackageModel {
    private String name;
    private List<ClassModel> classes;

    public PackageModel(String name) {
        this.name = name;
        this.classes = new Vector<>();
    }

    public PackageModel(String name, List<ClassModel> classes) {
        this.name = name;
        this.classes = classes;
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

    public void addClass(ClassModel classModel) {
        this.classes.add(classModel);
    }

    @Override
    public String toString() {
        return "PackageModel{" +
                "name='" + name + '\'' +
                ", classes=" + classes +
                '}';
    }
}
