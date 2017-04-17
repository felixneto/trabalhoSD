import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;


public class MainClient {
	
	public static void main (String[] argv) throws RemoteException
	{
		RmiCliente c = new RmiCliente();
		

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String line;
		System.out.println("Bem vindo ao cliente SQL. Para sair digite \"\\quit\"");
		//Statement stm;
		//PostgresConn banco = PostgresConn.getInstance("client", "client");
		
		while(true)
		{
			try {
				line = br.readLine();
				if(line.startsWith("\\quit"))
					break;
				
				c.registry = LocateRegistry.getRegistry(c.middlewareAddress, c.middlewarePort);
				// look up the remote object
				c.rmiMiddleware = (MiddlewareMessageInterface) (c.registry.lookup("rmiMiddleware"));
				// call the remote method
				c.rmiMiddleware.receiveMessage(line, c.clientAddress);
				//stm = PostgresConn.getInstance("client", "client").getConnection().createStatement();
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
