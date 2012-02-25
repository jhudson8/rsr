package org.rsr.serializer;

import java.io.OutputStream;
import java.io.Serializable;

import org.rsr.Context;
import org.rsr.Executable;

public interface Serializer {

	public void serialize(Serializable response, String mediaType,
			OutputStream out, Context context, Executable executable) throws Exception;
}
