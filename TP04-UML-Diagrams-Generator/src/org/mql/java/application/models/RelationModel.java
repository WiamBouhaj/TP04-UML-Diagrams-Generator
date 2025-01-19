package org.mql.java.application.models;

import org.mql.java.application.enumerations.RelationType;

public class RelationModel {
	    private RelationType type;
	    private ClassModel source;
	    private ClassModel target;

	    public RelationModel(RelationType type, ClassModel source, ClassModel target) {
	        this.type = type;
	        this.source = source;
	        this.target = target;
	    }
	    public RelationModel() {
	    	super();
	    }
	    
	    public RelationType getType() {
	        return type;
	    }

	    public void setType(RelationType type) {
	        this.type = type;
	    }

	    public ClassModel getSource() {
	        return source;
	    }

	    public void setSource(ClassModel source) {
	        this.source = source;
	    }

	    public ClassModel getTarget() {
	        return target;
	    }

	    public void setTarget(ClassModel target) {
	        this.target = target;
	    }
	    
	    public void addRelation(ClassModel sourceClass, ClassModel targetClass, RelationType relationType) {
	        RelationModel relation = new RelationModel(relationType, sourceClass, targetClass);
	        sourceClass.addRelation(relation);
	    }
	    
}
