package org.rsr;

import javax.ws.rs.core.MediaType;

import org.rsr.serializer.Serializer;

/**
 * Settings which can be defined as annotated values on a controller
 * 
 * @author Joe Hudson
 */
public interface RouteSettings {

	/**
	 * Set the media type as determined by the {@link MediaType} annotation
	 * @param mediatype the media type
	 * @return this for chaining
	 */
	public RouteSettings setMediaType(String mediatype);

	/**
	 * Set the serializaer as determined by the {@link org.rsr.annotation.Serializer} annotation
	 * @param serializer the serializer
	 * @return this for chaining
	 */
	public RouteSettings setSerializer(Serializer serializer);
}
