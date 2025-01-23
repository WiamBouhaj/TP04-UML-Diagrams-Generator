package org.mql.java.application.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//@Target(ElementType.TYPE)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Relations {
	String value() default "AGGREGATION";
}
