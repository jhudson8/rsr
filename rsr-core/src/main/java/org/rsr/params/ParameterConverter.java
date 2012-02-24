package org.rsr.params;

import java.io.Serializable;

public interface ParameterConverter {

	public Serializable convert(String param);
}
