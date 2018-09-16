package mapsql.sql.condition;

import java.util.Map;

import mapsql.sql.core.Field;
import mapsql.sql.core.SQLException;
import mapsql.sql.core.TableDescription;

public class Like extends AbstractCondition {
	private String column;
	private String value;

	public Like(String column, String value) {
		this.column = column;
		this.value = value;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean evaluate(TableDescription description, Map<String, String> data) throws SQLException {

		//PART 2

		Field field = description.findField(column);
		String str = (String) field.toValue(data.get(column));

		//declare String that replaces space with % sign
		String wildCard_pattern = value.replaceAll("%", "").trim();

		//true if string contains a given substring
		if(str.contains(wildCard_pattern))
		{
			return true;
		}

		//true if string starts with a given substring
		if(str.startsWith(value))
		{
			return true;
		}
		//true if string ends with a given substring
		else if(str.endsWith(value))
		{
			return true;
		}
		
		return false;
	}

}	

