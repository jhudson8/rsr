package test.org.rsr;

import java.io.Serializable;

import org.rsr.Context;
import org.rsr.Executable;

class TestExecutable implements Executable {
	private String[] parameterNames;
	private String[] parameters;
	public void init(String[] parameterNames) throws Exception {
		this.parameterNames = parameterNames;
	}
	public Serializable execute(String route, String[] parameters, Context context)
			throws Exception {
		this.parameters = parameters;
		return route;
	}
	public String[] getParameterNames() {
		return parameterNames;
	}
	public String[] getParameters() {
		return parameters;
	}
}

