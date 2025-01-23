package org.mql.java.application.models;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Vector;

import org.mql.java.application.enumerations.RelationType;
import org.mql.java.application.enumerations.Visibility;

public class ClassModel {
	private String name;
	private Visibility visibility; 
    private String packageName;
	private ClassModel superClass;
	//private List<Constructor> constructors = new Vector<>();
    private List<FieldModel> fields = new Vector<>();
    private List<MethodModel> methods = new Vector<>();
    private List<ClassModel> internClasses = new Vector<>();
    private List<Class<?>> interfaces = new Vector<>();
    private List<RelationModel> relations = new Vector<>();

    public ClassModel() {}
        public ClassModel(String classPath) {
            try {
                // Utiliser la réflexion pour analyser la classe
                Class<?> cls = Class.forName(classPath);

                // Nom et package
                this.name = cls.getSimpleName();
                this.packageName = cls.getPackage().getName();

                // Visibilité
                this.visibility = getVisibilityFromModifiers(cls.getModifiers());


                // Superclasse
                if (cls.getSuperclass() != null && !cls.getSuperclass().equals(Object.class)) {
                    this.superClass = new ClassModel(cls.getSuperclass().getName());
                }

                // Champs
                for (Field field : cls.getDeclaredFields()) {
                    fields.add(new FieldModel(field));
                }

                // Méthodes
                for (Method method : cls.getDeclaredMethods()) {
                    Visibility methodVisibility = getVisibilityFromModifiers(method.getModifiers());
                    String returnType = method.getReturnType().getName();

                    // Convertir les paramètres en List<Class<?>>
                    List<Class<?>> parameters = new Vector<>();
                    for (Class<?> paramType : method.getParameterTypes()) {
                        parameters.add(paramType);
                    }

                    methods.add(new MethodModel(method.getName(), returnType, methodVisibility, parameters));
                }


                // Interfaces
                for (Class<?> iface : cls.getInterfaces()) {
                    interfaces.add(iface);
                    relations.add(new RelationModel(RelationType.IMPLEMENTATION, this, new ClassModel(iface.getName())));
                }

                // Relations
                extractRelations();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        private void extractRelations() {
            // Héritage
            if (superClass != null) {
                relations.add(new RelationModel(RelationType.INHERITANCE, this, superClass));
            }

            // Composition et agrégation
            for (FieldModel field : fields) {
                if (field.getType() != null && !field.getType().isEmpty()) {
                    RelationType relationType = field.isFinal() ? RelationType.COMPOSITION : RelationType.AGGREGATION;
                    ClassModel targetClass = new ClassModel(field.getType());
                    relations.add(new RelationModel(relationType, this, targetClass));
                }
            }
        }

	public ClassModel(String name, Visibility visibility, String packageName, ClassModel superClass,
			List<FieldModel> fields, List<MethodModel> methods, List<ClassModel> internClasses, List<Class<?>> interfaces,
			List<RelationModel> relations) {
		super();
		this.name = name;
		this.visibility = visibility;
		this.packageName = packageName;
		this.superClass = superClass;
		this.fields = fields;
		this.methods = methods;
		this.internClasses = internClasses;
		this.interfaces = interfaces;
		this.relations = relations;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Visibility getVisibility() {
	    return visibility;
	}

	public void setVisibility(Visibility visibility) {
	    this.visibility = visibility;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public ClassModel getSuperClass() {
		return superClass;
	}

	public void setSuperClass(ClassModel superClass) {
		this.superClass = superClass;
	}

	public List<FieldModel> getFields() {
		return fields;
	}

	public void setFields(List<FieldModel> fields) {
		this.fields = fields;
	}

	public List<MethodModel> getMethods() {
		return methods;
	}

	public void setMethods(List<MethodModel> methods) {
		this.methods = methods;
	}

	public List<ClassModel> getInternClasses() {
		return internClasses;
	}

	public void setInternClasses(List<ClassModel> internClasses) {
		this.internClasses = internClasses;
	}

	public List<Class<?>> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(List<Class<?>> interfaces) {
		this.interfaces = interfaces;
	}

	public List<RelationModel> getRelations() {
		return relations;
	}

	public void setRelations(List<RelationModel> relations) {
		this.relations = relations;
	}

	public void addRelation(RelationModel relation) {
	        relations.add(relation);
	}
  
	public void addField(FieldModel field) {
	        fields.add(field);
	    }
	
	private Visibility getVisibilityFromModifiers(int modifiers) {
	    if (java.lang.reflect.Modifier.isPublic(modifiers)) {
	        return Visibility.PUBLIC;
	    } else if (java.lang.reflect.Modifier.isProtected(modifiers)) {
	        return Visibility.PROTECTED;
	    } else if (java.lang.reflect.Modifier.isPrivate(modifiers)) {
	        return Visibility.PRIVATE;
	    } else {
	        return Visibility.DEFAULT;
	    }
	}

}  
