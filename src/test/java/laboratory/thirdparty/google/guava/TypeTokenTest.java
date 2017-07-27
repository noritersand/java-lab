package laboratory.thirdparty.google.guava;

import java.lang.reflect.Type;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.reflect.TypeToken;

/**
 * 
 * 
 * @since 2017-07-27
 * @author fixalot
 */
public class TypeTokenTest {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(TypeTokenTest.class);

	@Test
	public void useTypeToken() {
		@SuppressWarnings("serial")
		Type listType = new TypeToken<ArrayList<MyClass>>() {}.getType();
		Assert.assertEquals("java.util.ArrayList<laboratory.thirdparty.google.guava.MyClass>", String.valueOf(listType));
	}
}

class MyClass {

}