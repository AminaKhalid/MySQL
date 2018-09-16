package mapsql.sql.statement;

import java.util.Map;

import mapsql.sql.condition.Equals;
import mapsql.sql.core.Row;
import mapsql.sql.core.SQLException;
import mapsql.sql.core.SQLResult;
import mapsql.sql.core.SQLStatement;
import mapsql.sql.core.Table;
import mapsql.sql.core.TableDescription;
import mapsql.util.List;

public class DropTable implements SQLStatement {
	private String name;

	public DropTable(String name) {
		this.name = name;
	}

	@Override
	public SQLResult execute(Map<String, Table> tables) throws SQLException {

		//PART 6
		
		//throw exception if anyone attempts to delete 'mapsql.tables' table
		if(name.equals("mapsql.tables"))
		{
			throw new SQLException("Table 'mapsql.tables' cannot be modified");
		}
		//check if the table exists, if not, then print statement
		if(!tables.containsKey(name))
		{
			System.out.println("No such Table exists in the Database");
		}
		
		//removing table contact from both the tables Map and the system table
		tables.get("mapsql.tables").delete(new Equals("table", name));
		
		tables.remove(name);

		return new SQLResult() {
			public String toString() {
				return "OK";
			}

			@Override
			public TableDescription description() {
				return null;
			}

			@Override
			public List<Row> rows() {
				return null;
			}
		};
	}

}
