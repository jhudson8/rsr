package org.rsr.serializer;

import java.io.OutputStream;
import java.io.Serializable;

import org.rsr.Context;
import org.rsr.executable.Executable;

/**
 * Serialize the execution context result into some other format.
 * 
 * @author Joe Hudson
 */
public interface Serializer {

	/**
	 * @param response the execution response
	 * @param mediaType the media type
	 * @param out the output stream to write the serialized value to
	 * @param context the execution context
	 * @param executable the executable which has already been executed
	 */
	public void serialize(Serializable response, String mediaType,
			OutputStream out, Context context, Executable executable) throws Exception;
}
