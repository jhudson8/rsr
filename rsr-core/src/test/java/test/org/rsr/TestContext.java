package test.org.rsr;

import java.util.HashMap;
import java.util.Map;

import org.rsr.Context;

class TestContext implements Context {
	private Map<String, String> params = new HashMap<String, String>();
	public Map<String, String> getNamedParameters() {
		return params;
	}
	public Map<String, String> getParams() {
		return params;
	}
}
