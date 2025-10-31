package thejavalistener.fwk.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MyJson
{
	public static String toJson(Object value)
	{
		Gson gson = new GsonBuilder().create();
		
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("type",value.getClass().getName());
		jsonObject.add("data",gson.toJsonTree(value));
		
		return gson.toJson(jsonObject);
	}
		
	@SuppressWarnings("unchecked")
	public static <T> T fromJson(String jsonString)
	{
	    if (jsonString == null) return null;

	    Gson gson = new GsonBuilder().create();
	    JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

	    String className = jsonObject.get("type").getAsString();
	    JsonElement data = jsonObject.get("data");

	    try
	    {
	        Class<?> clazz = Class.forName(className);

	     // --- Caso especial: Class ---
	        if (clazz.equals(Class.class)) {
	            String innerName = data.getAsString();
	            return (T) Class.forName(innerName);
	        }

	        
	        // 🔹 Normalizar tipos problemáticos (List.of, Map.of, etc.)
	        if (clazz.getName().startsWith("java.util.ImmutableCollections$List"))
	        {
	            return (T) gson.fromJson(data, java.util.ArrayList.class);
	        }
	        else if (clazz.getName().startsWith("java.util.ImmutableCollections$Map"))
	        {
	            return (T) gson.fromJson(data, java.util.LinkedHashMap.class);
	        }
	        else if (clazz.getName().startsWith("java.util.ImmutableCollections$Set"))
	        {
	            return (T) gson.fromJson(data, java.util.LinkedHashSet.class);
	        }
	        else
	        {
	            // 🔹 Tipo normal
	            return (T) gson.fromJson(data, clazz);
	        }
	    }
	    catch (ClassNotFoundException e)
	    {
	        throw new RuntimeException("Clase no encontrada: " + className, e);
	    }
	}
	
}
