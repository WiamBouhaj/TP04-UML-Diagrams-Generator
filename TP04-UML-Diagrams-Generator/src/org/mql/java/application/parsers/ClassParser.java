package org.mql.java.application.parsers;  

import java.io.File;  
import java.lang.reflect.Field;  
import java.lang.reflect.Method;  
import java.lang.reflect.Modifier;  
import java.net.URL;  
import java.net.URLClassLoader;  
import java.util.List;  
import java.util.Vector;  

import org.mql.java.application.models.ClassModel;  
import org.mql.java.application.models.FieldModel;  
import org.mql.java.application.models.MethodModel;
import org.mql.java.application.models.RelationModel;
import org.mql.java.application.enumerations.Cardinality;
import org.mql.java.application.enumerations.RelationType;
import org.mql.java.application.enumerations.Visibility;  

public class ClassParser {  
    private URLClassLoader classLoader;  

    public ClassParser(URLClassLoader classLoader) {  // Modifié pour recevoir un classLoader  
        this.classLoader = classLoader;  // Initialise classLoader  
    }  

    public ClassModel parse(File directory, String packageName, String className) {  
        if (classLoader == null) {  
            System.err.println("Le classLoader est nul, initialisez-le correctement.");  
            return null;  
        }  
        try {  
            // Construction du nom complet de la classe  
            String fullClassName = packageName + "." + className;  
            Class<?> cls = classLoader.loadClass(fullClassName);  
            
            // Création du modèle de classe  
            ClassModel classModel = new ClassModel(  
                    cls.getSimpleName(),  
                    null,  
                    packageName,  
                    null,  
                    new Vector<>(),  
                    new Vector<>(),  
                    new Vector<>(),  
                    new Vector<>(),  
                    new Vector<>()  
            );  

            classModel.setVisibility(mapVisibility(cls.getModifiers()));  

            // Ajout de la classe super   
            Class<?> superClass = cls.getSuperclass();  
            if (superClass != null && superClass != Object.class) {  
                ClassModel superClassModel = parse(directory, superClass.getPackage().getName(), superClass.getSimpleName());  
                classModel.setSuperClass(superClassModel);  
            }  

            // Extraction des champs et méthodes  
            parseFields(cls, classModel);  
            parseMethods(cls, classModel);  

            return classModel;  

        } catch (ClassNotFoundException e) {  
            System.err.println("Classe non trouvée : " + packageName + "." + className);  
            return null;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
		
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
		
		
		private void parseMethods(Class<?> cls, ClassModel classModel) {  
		for (Method method : cls.getDeclaredMethods()) {  
		    MethodModel methodModel = new MethodModel(  
		            method.getName(),  
		            method.getReturnType().getSimpleName(),  
		            mapVisibility(method.getModifiers()),  
		            getParameterTypes(method)  
		    );  
		    classModel.getMethods().add(methodModel);  
		    //parseRelations(cls, classModel);  
		}  
		}  
		
		 private void parseRelations(Class<?> cls, ClassModel classModel) {  
		        for (Field field : cls.getDeclaredFields()) {  
		            Class<?> fieldType = field.getType();  
		            RelationType relationType = Modifier.isFinal(field.getModifiers())  
		                    ? RelationType.COMPOSITION  
		                    : RelationType.AGGREGATION;  

		            Cardinality cardinality = determineCardinality(field);  
		            RelationModel relationModel = new RelationModel(relationType, classModel, new ClassModel(fieldType.getSimpleName()), cardinality);  
		            classModel.getRelations().add(relationModel);  
		        }  
		    }  

		    private void parseInterfaces(Class<?> cls, ClassModel classModel) {  
		        for (Class<?> iface : cls.getInterfaces()) {  
		            RelationModel relationModel = new RelationModel(RelationType.IMPLEMENTATION, classModel, new ClassModel(iface.getSimpleName()), Cardinality.ONE);  
		            classModel.getRelations().add(relationModel);  
		        }  
		    }  
		
		private Visibility mapVisibility(int modifiers) {  
		if (Modifier.isPublic(modifiers)) return Visibility.PUBLIC;  
		if (Modifier.isProtected(modifiers)) return Visibility.PROTECTED;  
		if (Modifier.isPrivate(modifiers)) return Visibility.PRIVATE;  
		return Visibility.DEFAULT;  
		}  
		
		private List<String> getParameterTypes(Method method) {  
		return List.of(method.getParameterTypes()).stream()  
		        .map(Class::getSimpleName)  
		        .toList();  
		}  
		private Cardinality determineCardinality(Field field) {  
	        Class<?> fieldType = field.getType();  
	        if (fieldType.isArray()) {  
	            return Cardinality.MANY;  
	        }  
	        if (List.class.isAssignableFrom(fieldType) || Iterable.class.isAssignableFrom(fieldType)) {  
	            return Cardinality.MANY;  
	        }  
	        if (fieldType.getSimpleName().equals("Optional")) {  
	            return Cardinality.ZERO_OR_ONE;  
	        }  
	        return Cardinality.ONE;  
	    }  
		 
}