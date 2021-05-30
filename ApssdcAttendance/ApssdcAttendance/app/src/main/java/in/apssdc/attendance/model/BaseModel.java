package in.apssdc.attendance.model;

import java.io.Serializable;

public class BaseModel implements Serializable
{
	@Override
	public String toString()
	{
		return JsonUtils.toString(this);
	}
}
