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
	
	public static <T> T fromJson(String jsonString)
	{
        Gson gson = new GsonBuilder().create();
		
//		JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
//        String className = jsonObject.get("type").getAsString();
//        JsonElement data = jsonObject.getAsJsonObject("data");

        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        String className = jsonObject.get("type").getAsString();
        JsonElement data = jsonObject.get("data");

        
        
        // Deserializa el objeto usando la información del tipo
        Class<?> clazz = MyReflection.clasz.forName(className);
        T deserializedObject = (T)gson.fromJson(data, clazz);
		
		return deserializedObject;
	}
	

	public static void main(String[] args)
	{
		Persona p = new Persona();
		p.setDni(22);
		p.setNombre("popo");
		String json = MyJson.toJson(p);
		
		
		
		System.out.println(json);
		
		Persona x = MyJson.fromJson(json);
		System.out.println(x);
	}
	
}
