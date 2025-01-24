package org.mql.java.application.models;

import org.mql.java.application.enumerations.Cardinality;
import org.mql.java.application.enumerations.RelationType;

public class RelationModel {
	    private RelationType type;
	    private ClassModel source;
	    private ClassModel target;
        private Cardinality cardinality;

	    public RelationModel(RelationType type, ClassModel source, ClassModel target, Cardinality cardinality) {
			super();
			this.type = type;
			this.source = source;
			this.target = target;
			this.setCardinality(cardinality);
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
	    
	    @Override
	    public String toString() {
	        return "Relation : " + type + " avec " + target.getName();
	    }

		public Cardinality getCardinality() {
			return cardinality;
		}

		public void setCardinality(Cardinality cardinality) {
			this.cardinality = cardinality;
		}

}
