package mapsql.sql.test;

import mapsql.sql.condition.Equals;
import mapsql.sql.condition.GreaterThan;
import mapsql.sql.condition.GreaterThanOrEqual;
import mapsql.sql.condition.LessThan;
import mapsql.sql.condition.LessThanOrEqual;
import mapsql.sql.condition.Like;
import mapsql.sql.condition.NotEqual;
import mapsql.sql.condition.OrCondition;
import mapsql.sql.core.Field;
import mapsql.sql.core.SQLException;
import mapsql.sql.core.SQLManager;
import mapsql.sql.core.SQLResult;
import mapsql.sql.core.SQLStatement;
import mapsql.sql.field.CHARACTER;
import mapsql.sql.field.INTEGER;
import mapsql.sql.statement.CreateTable;
import mapsql.sql.statement.Delete;
import mapsql.sql.statement.DropTable;
import mapsql.sql.statement.Insert;
import mapsql.sql.statement.Select;
import mapsql.sql.statement.Update;

public class ConditionsTest {
	static SQLManager manager = new SQLManager();

	public static void main(String[] args) {
		createTableStatement();

		showTables();
		insertData();
		selectTable();

		insertPartialData();
		selectTable();
		test_conditions();
		test_like();

		updateData();
//		updateOrData();
		selectTable();

		deleteData();
		selectTable();

		dropTable();
		showTables();
	}

	//testing conditions method
	private static void test_conditions() {
		// TODO Auto-generated method stub

		System.out.println("testing EQUALS");
		//select statement that tests the = operator, so a table with ID 2 will be returned
		executeStatement(new Select("contact", new String[] { "*" }, new Equals("id","2")));

		System.out.println("testing GREATER THAN");
		//select statement that tests the > operator, so a table with greater than ID 1 will be returned
		executeStatement(new Select("contact", new String[] { "*" }, new GreaterThan("id","1")));

		System.out.println("testing GREATER THAN OR EQUAL");
		//select statement that tests the >= operator, so a table with greater than or equal to ID 1 will be returned
		executeStatement(new Select("contact", new String[] { "*" }, new GreaterThanOrEqual("id","1")));

		System.out.println("testing LESS THAN");
		//select statement that tests < operator, so a table with less than ID 2 will be returned
		executeStatement(new Select("contact", new String[] { "*" }, new LessThan("id","2")));

		System.out.println("testing LESS THAN OR EQUAL");
		//select statement that tests <= operator, so a table with less than or equal to ID 1 will be returned
		executeStatement(new Select("contact", new String[] { "*" }, new LessThanOrEqual("id","2")));

		System.out.println("testing NOT EQUAL");
		//select statement that tests != operator, so a table which is not equal to id 1 will be returned
		executeStatement(new Select("contact", new String[] { "*" }, new NotEqual("id","1")));

	}

	//testing all three permitted wildcard patterns
	private static void test_like() {
		// TODO Auto-generated method stub

		System.out.println("%xxx%");
		//test should return Henry
		executeStatement(new Select("contact", new String[] { "*" }, new Like("name","%en%")));

		System.out.println("xxx%");
		//test should return Rem
		executeStatement(new Select("contact", new String[] { "*" }, new Like("email","ali%")));

		System.out.println("%xxx ");
		//test should return Rem
		executeStatement(new Select("contact", new String[] { "*" }, new Like("name","%em")));
	}

	private static void executeStatement(SQLStatement statement) {
		try {
			SQLResult result = manager.execute(statement);
			System.out.println(result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createTableStatement() {
		executeStatement(
				new CreateTable(
						"contact", 
						new Field[] {
								new INTEGER("id", true, false, true), 
								new CHARACTER("name", 30, false, true), 
								new CHARACTER("email", 30, false, false)
						}
						)
				);
	}

	public static void showTables() {
		executeStatement(new Select("mapsql.tables", new String[] { "*" }));
	}

	public static void selectTable() {
		executeStatement(new Select("contact", new String[] { "*" }));
	}

	public static void dropTable() {
		System.out.println("DROP TABLE");
		executeStatement(new DropTable("contact"));
	}

	public static void insertData() {
		executeStatement(
				new Insert(
						"contact", 
						new String[] {"name", "email"}, 
						new String[] {"Rem", "rem.collier@ucd.ie"}
						)
				);

		executeStatement(
				new Insert(
						"contact", 
						new String[] {"name", "email"}, 
						new String[] {"Amina", "amina.khalid@ucdconnect.ie"}
						)
				);
	}

	public static void insertPartialData() {
		executeStatement(new Insert("contact", new String[] {"name"}, new String[] {"Henry"}));
	}

	public static void updateData() {
		System.out.println("UPDATE");
		executeStatement(
				new Update(
						"contact", 
						new String[] {"email"}, 
						new String[] {"henry.mcloughlin@ucd.ie"}, 
						new Equals("id", "3")
						)
				);

	}

	public static void updateOrData() {
		System.out.println("UPDATE");
		executeStatement(
				new Update(
						"contact", 
						new String[] {"email"}, 
						new String[] {"henry.mcloughlin@ucd.ie"}, 
						new OrCondition(
								new Equals("id", "2"), 
								new Equals("id", "3")
								)
						)
				);

	}

	public static void deleteData() {
		System.out.println("DELETE ID 2");
		executeStatement(new Delete("contact", new Equals("id", "2")));

	}

	public static void selectTableWithColumns() {
		System.out.println("select Table With Columns");
		executeStatement(new Select("contact", new String[] { "id", "name" }));
	}
}
