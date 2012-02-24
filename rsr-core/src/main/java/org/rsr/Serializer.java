package org.rsr;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Serializer {

	public Class<? extends org.rsr.serializer.Serializer> value() default org.rsr.serializer.Serializer.class;

	public Class<? extends org.rsr.serializer.Serializer> type() default org.rsr.serializer.Serializer.class;

	public String properties() default "";
}
