package org.mql.java.application.parsers;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.mql.java.application.enumerations.Visibility;
import org.mql.java.application.models.ClassModel;
import org.mql.java.application.models.FieldModel;
import org.mql.java.application.models.MethodModel;
import org.mql.java.application.models.RelationModel;
import org.mql.java.application.enumerations.RelationType;

public class ClassParser {
    private Class<?> cls;
    public ClassModel classModel;

    public ClassModel parse(String packageName, String className) {
        try {
            // Charger la classe
            this.cls = Class.forName(packageName + "." + className);
            this.classModel = new ClassModel(cls.getSimpleName(), null, packageName, null,   
                    new Vector<>(), new Vector<>(),   
                    new Vector<>(), new Vector<>(),   
                    new Vector<>());  this.classModel.setVisibility(mapVisibility(cls.getModifiers()));

            // Ajouter le nom du package
            this.classModel.setPackageName(cls.getPackageName());

            // Héritage (superclass)
            if (cls.getSuperclass() != null) {
                classModel.setSuperClass(new ClassModel(
                        cls.getSuperclass().getSimpleName(),
                        null, cls.getSuperclass().getPackageName(), classModel, null, null, null, null, null
                ));
            }

            // Extraction des champs
            parseFields(cls, classModel);

            // Extraction des méthodes
            parseMethods(cls, classModel);

            // Extraction des classes internes
            for (Class<?> innerClass : cls.getDeclaredClasses()) {
                ClassModel innerClassModel = parse(innerClass.getPackageName(), innerClass.getSimpleName());
                classModel.getInternClasses().add(innerClassModel);
            }

            // Extraction des interfaces
            parseInterfaces(cls, classModel);

            return classModel;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Méthode d'extraction des champs
    private void parseFields(Class<?> cls, ClassModel classModel) {
        for (Field field : cls.getDeclaredFields()) {
            Visibility visibility = mapVisibility(field.getModifiers());

            FieldModel fieldModel = new FieldModel(
                    field.getName(),
                    field.getType().getSimpleName(),
                    visibility,
                    Modifier.isStatic(field.getModifiers()),
                    Modifier.isFinal(field.getModifiers())
            );
            classModel.getFields().add(fieldModel);
        }
    }

    // Méthode d'extraction des méthodes
    private void parseMethods(Class<?> cls, ClassModel classModel) {
        for (Method method : cls.getDeclaredMethods()) {
            MethodModel methodModel = new MethodModel(
                    method.getName(),
                    method.getReturnType().getSimpleName(),
                    mapVisibility(method.getModifiers()),
                    getParameterTypes(method)
            );
            classModel.getMethods().add(methodModel);
        }
    }

    // Extraire les interfaces implémentées
    private void parseInterfaces(Class<?> cls, ClassModel classModel) {
        for (Class<?> iface : cls.getInterfaces()) {
            classModel.getInterfaces().add(iface);
        }
    }

    // Mapper les modificateurs à l'énumération Visibility
    private Visibility mapVisibility(int modifiers) {
        if (Modifier.isPublic(modifiers)) return Visibility.PUBLIC;
        if (Modifier.isProtected(modifiers)) return Visibility.PROTECTED;
        if (Modifier.isPrivate(modifiers)) return Visibility.PRIVATE;
        return Visibility.DEFAULT;
    }

    private List<Class<?>> getParameterTypes(Method method) {
        return List.of(method.getParameterTypes());
    }

    // Méthodes pour obtenir les modificateurs sous forme de chaîne (si nécessaire)
    public String getModifier() {
        return Modifier.toString(cls.getModifiers());
    }

    private String getModifier(int modifier) {
        return Modifier.toString(modifier);
    }

}
