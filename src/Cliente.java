import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Statement;


public class Cliente {
	
	public static void main (String[] argv)
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));;

		String line="";
		System.out.println("Bem vindo ao cliente SQL. Para sair digite \"\\quit\"");
		Statement stm;
		//PostgresConn banco = PostgresConn.getInstance("client", "client");
		
		while(true)
		{
			try {
				line = br.readLine();
				if(line.contains("\\quit"))
					break;
				
				stm = PostgresConn.getInstance("client", "client").getConnection().createStatement();
				
				line = line.toUpperCase();
				if(line.startsWith("SELECT"))
				{
					stm.executeQuery(line);
				}
				else
					stm.executeUpdate(line);
					
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
