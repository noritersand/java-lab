package thirdparty.google;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

/**
 * 
 * 
 * @since 2017-07-27
 * @author fixalot
 */
public class GsonTest {
	private static final Logger logger = LoggerFactory.getLogger(GsonTest.class);

	@Test
	public void getDataFromFile() throws FileNotFoundException {
		JsonReader reader = new JsonReader(new FileReader("src\\test\\resources\\gson-test\\servers.json"));
		@SuppressWarnings("serial")
		Type listType = new TypeToken<ArrayList<HashMap<String, String>>>() {}.getType();
		Object result = new Gson().fromJson(reader, listType);
		Assert.assertEquals(new ArrayList<HashMap<String, String>>().getClass(), result.getClass());
		logger.debug(String.valueOf(result));
	}

	@Test
	public void toJSONString() {
		JsonObject job = new JsonObject();
		job.addProperty("key", "value");

		Assert.assertEquals("{\"key\":\"value\"}", new Gson().toJson(job));
	}

	@Test
	public void parseToPojo() {
		String json = "{\"key\":\"first\",\"value\":\"1234\"}";
		MyClass myClass = new Gson().fromJson(json, MyClass.class);
		Assert.assertEquals("first", myClass.getKey());
		Assert.assertEquals("1234", myClass.getValue());
	}

	@Test
	public void parseToCollections() {
		String json = "[{\"key\":\"first\",\"value\":\"1234\"}, {\"key\":\"second\",\"value\":\"5678\"}]";
		@SuppressWarnings("serial")
		Type listType = new TypeToken<ArrayList<MyClass>>() {}.getType();
		ArrayList<MyClass> myClassList = new Gson().fromJson(json, listType);
		Assert.assertEquals("first", myClassList.get(0).getKey());
		Assert.assertEquals("1234", myClassList.get(0).getValue());
		Assert.assertEquals("second", myClassList.get(1).getKey());
		Assert.assertEquals("5678", myClassList.get(1).getValue());
	}
}

class MyClass {
	private String key;
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}