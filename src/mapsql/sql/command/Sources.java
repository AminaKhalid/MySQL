package mapsql.sql.command;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import mapsql.shell.core.SQLVisitor;
import mapsql.shell.parser.MapSQL;
import mapsql.shell.parser.ParseException;
import mapsql.shell.parser.SimpleNode;
import mapsql.sql.core.SQLCommand;
import mapsql.sql.core.SQLException;
import mapsql.sql.core.SQLManager;
import mapsql.sql.core.SQLOperation;
import mapsql.util.List;

public class Sources implements SQLCommand {
	private String filename;

	public Sources(String filename) {
		this.filename = filename;
	}	
	@Override
	public String execute(SQLManager manager) throws SQLException, IOException {

		//PART 7
		//NOTE: this code is taken from the SHELL.java class, it is altered to work for file reading
		//When running shell.java, I used the command SOURCE "test.sql"
		
		File file = new File(filename);
		if(!filename.isEmpty()) 
		{
			System.out.print("mapsql>");

			try {
				SimpleNode n = new MapSQL(new FileInputStream(file)).Start();

				List<SQLOperation> operations = (List<SQLOperation>) n.jjtAccept(new SQLVisitor(), null);
				for (SQLOperation operation : operations) 
				{
					System.out.println(manager.execute(operation));
				}
			} catch (ParseException e) {
				System.out.println(e.getMessage());
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return filename;
	}
}