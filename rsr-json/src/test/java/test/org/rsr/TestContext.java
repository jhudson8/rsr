package test.org.rsr;

import java.util.Map;

import org.rsr.Context;

class TestContext implements Context {
	private Map<String, String> params = null;

	public void setRouteParameters(Map<String, String> params) {
		this.params = params;
	}

	public String getParameter(String name) {
		return params.get(name);
	}

	public String[] getParameterValues(String name) {
		return null;
	}
}
