package org.mql.java.application.models;

import java.lang.reflect.Field;

import org.mql.java.application.enumerations.Visibility;

public class FieldModel {
    private String name;
    private String type;
    private Visibility  visibility; 
    private boolean isStatic;
    private boolean isFinal;

    public FieldModel(String name, String type, Visibility visibility, boolean isStatic, boolean isFinal) {
        this.name = name;
        this.type = type;
        this.visibility = visibility;
        this.isStatic = isStatic;
        this.isFinal = isFinal;
    }
    
    public FieldModel() {}
    
    public FieldModel(Field field) {
        this.name = field.getName();
        this.type = field.getType().getSimpleName();
        this.visibility = getVisibilityFromModifiers(field.getModifiers());
        this.isStatic = java.lang.reflect.Modifier.isStatic(field.getModifiers());
        this.isFinal = java.lang.reflect.Modifier.isFinal(field.getModifiers());
    }
 // Méthode pour obtenir une représentation textuelle du champ  
    public String getRepresentation() {  
        StringBuilder rep = new StringBuilder();  
        rep.append(visibility);  
        rep.append(" ").append(name).append(" : ").append(type);  
        if (isStatic) {  
            rep.append(" [static]");  
        }  
        if (isFinal) {  
            rep.append(" [final]");  
        }  
        return rep.toString();  
    }  

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Visibility getVisibility() {
		return visibility;
	}

	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

	public boolean isFinal() {
		return isFinal;
	}

	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
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
