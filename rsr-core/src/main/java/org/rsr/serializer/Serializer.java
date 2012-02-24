package org.rsr.serializer;

import java.io.OutputStream;
import java.io.Serializable;

public interface Serializer {

	public void serialize(Serializable repsonse, String mediaType,
			OutputStream out);
}
