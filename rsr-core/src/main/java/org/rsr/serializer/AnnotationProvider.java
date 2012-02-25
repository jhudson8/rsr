package org.rsr.serializer;

import java.lang.annotation.Annotation;

public interface AnnotationProvider {

	public <T extends Annotation> T getRouteAnnotation(Class<T> clazz);
}
