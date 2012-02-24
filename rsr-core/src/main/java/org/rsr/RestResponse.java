package org.rsr;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import org.rsr.serializer.Serializer;

public class RestResponse {

	private Serializable response;
	private String mediaType;
	private Serializer serializer;

	public RestResponse(Serializable response, Serializer serializer,
			String mediaType) {
		this.response = response;
		this.serializer = serializer;
		this.mediaType = mediaType;
	}

	public Serializable getResponse() {
		return response;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void connect(OutputStream out) throws IOException {
		if (null != response) {
			if (null == serializer) {
				out.write(response.toString().getBytes());
			} else {
				serializer.serialize(response, mediaType, out);
			}
		}
	}
}
