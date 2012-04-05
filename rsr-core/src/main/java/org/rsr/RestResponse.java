package org.rsr;

import java.io.OutputStream;
import java.io.Serializable;

import org.rsr.serializer.DefaultSerializer;
import org.rsr.serializer.Serializer;

/**
 * Response returned from the route handler for a service endpoint execution.
 * 
 * @author Joe Hudson
 */
public class RestResponse {

	private Serializable response;
	private String mediaType;
	private Serializer serializer;

	/**
	 * @param response the actual response from the execution call
	 * @param serializer the serializer associated with the route definition
	 * @param mediaType the media type associated with the route definition
	 */
	RestResponse(Serializable response, Serializer serializer,
			String mediaType) {
		this.response = response;
		this.serializer = serializer;
		this.mediaType = mediaType;
	}

	/**
	 * @return the execution response
	 */
	public Serializable getResponse() {
		return response;
	}

	/**
	 * @return the media type
	 */
	public String getMediaType() {
		return mediaType;
	}

	/**
	 * Connect the output stream with the response using the serializer
	 * @param out the output stream
	 */
	public void connect(OutputStream out) throws Exception {
		if (null == serializer) {
			DefaultSerializer.INSTANCE.serialize(response, mediaType, out, null, null);
		} else {
			serializer.serialize(response, mediaType, out, null, null);
		}
	}
}
