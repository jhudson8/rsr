package org.rsr.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Allows a {@link org.rsr.serializer.Serializer} to be associated with a route handler method.  The serialazer value
 * must be the fully qualified name of the serializer class.
 * 
 * @author Joe Hudson
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Serializer {

	public Class<? extends org.rsr.serializer.Serializer> value() default org.rsr.serializer.Serializer.class;

	public Class<? extends org.rsr.serializer.Serializer> type() default org.rsr.serializer.Serializer.class;

	public String properties() default "";
}
