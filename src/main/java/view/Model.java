package view;

import java.util.HashMap;
import java.util.Map;

public class Model {

	public Map<String, Object> model = new HashMap<>();

	public void addModel(String key, Object value) {
		model.put(key, value);
	}

	public Object getValue(String key) {
		return model.get(key);
	}
}
