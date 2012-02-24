package org.rsr.serializer;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import org.apache.commons.io.IOUtils;
import org.rsr.RestException;

public class DefaultSerializer implements Serializer {

	public void serialize(Serializable response, String mediaType,
			OutputStream out) throws Exception {
		this.serialize(response, mediaType, out, true);
	}

	public boolean serialize(Serializable response, String mediaType,
			OutputStream out, boolean validate) throws Exception {
		if (null != response) {
			if (response instanceof CharSequence) {
				out.write(response.toString().getBytes());
				return true;
			}
			else if (response instanceof InputStream) {
				IOUtils.copy((InputStream) response, out);
				return true;
			}
			else {
				if (validate) {
					throw new RestException("Invalid response type");
				}
				return false;
			}
		}
		return true;
	}
}
