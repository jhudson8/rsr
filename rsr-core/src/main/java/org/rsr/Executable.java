package org.rsr;

import java.io.Serializable;

public interface Executable {

	public void init (String[] parameterNames) throws Exception;

	public Serializable execute (String route, String[] parameters, Context context) throws Exception;
}
