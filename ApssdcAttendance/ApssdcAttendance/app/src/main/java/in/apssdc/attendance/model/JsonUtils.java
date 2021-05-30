package in.apssdc.attendance.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils
{
	private static final ObjectMapper mapper = new ObjectMapper();

	public static String toString(Object object)
	{
		String str = null;

		try{
			str = mapper.writeValueAsString(object);
		}
		catch (JsonProcessingException e){
			str = "" + object;
		}
		return str;
	}
}
