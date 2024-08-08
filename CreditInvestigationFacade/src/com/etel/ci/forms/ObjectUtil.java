package com.etel.ci.forms;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ObjectUtil {
	
	/**
	 * Returns field name and it's value
	 * **/
	@SuppressWarnings({ "rawtypes" })
	public static Map getFieldNamesAndValues(final Object valueObj)
			throws IllegalArgumentException, IllegalAccessException {
		Class c1 = valueObj.getClass();
		Map<String, Object> fieldMap = new HashMap<String, Object>();
		Field[] valueObjFields = c1.getDeclaredFields();

		// compare values now
		for (int i = 0; i < valueObjFields.length; i++) {
			String fieldName = valueObjFields[i].getName();

			valueObjFields[i].setAccessible(true);

			Object newObj = valueObjFields[i].get(valueObj);

			// System.out.println("Value of field " + fieldName + " : " +
			// newObj);

			fieldMap.put(fieldName, newObj);
		}
		return fieldMap;
	}
	
//	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException{
//	@SuppressWarnings("unchecked")
//	Map<String, Object> m = ObjectUtil.getFieldNamesAndValues(new PDRNDesk());
//	for (Map.Entry<String, Object> entry : m.entrySet())
//	{
//	    System.out.println(entry.getKey() + "/" + entry.getValue());
//	}
//}
	
}
