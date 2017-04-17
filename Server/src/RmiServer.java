import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.*;

public class RmiServer implements ServerMessageInterface {

	public String serverAddress;
	private int serverPort = 3434;
	public String middlewareAddress; // localhost
	private int middlewarePort = 3232;
	private int priority;
	public ReceiveMessageInterface rmiMiddleware;
	public ServerMessageInterface rmiServer;
	int Port = 3434;
	Registry registry; // rmi registry for lookup the remote objects.

	public RmiServer() throws Exception {

		try {
			registry = LocateRegistry.createRegistry(serverPort);
			registry.rebind("rmiServer", this);
			// ReceiveMessageInterface rMI = (ReceiveMessageInterface)
			// UnicastRemoteObject.exportObject(this, 3232);
			// registry.bind("RmiServer", rMI);
		} catch (RemoteException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public String serverReceiveMessage(String command, String address,
			ArrayList<ServerMessageInterface> hosts) throws RemoteException {
		//Se o servidor receber uma lista nula significa que ele não é o líder
		// Sendo assim ele armazena as respostas dos outros servidores
		if (hosts != null) {
			for (ServerMessageInterface host : hosts) {
				registry = LocateRegistry.getRegistry(host.getAddress(), serverPort);
				try {
					rmiServer = (ServerMessageInterface) (registry
							.lookup("rmiServer"));
				} catch (NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//rmiServer.receiveMessageServer(command, serverAddress, null);
			}

		}

		Connection conn = PostgresConn.getInstance("client", "client")
				.getConnection();

		try {
			Statement stm = PostgresConn.getInstance("client", "client")
					.getConnection().createStatement();
			stm.execute(command);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getStatus() throws RemoteException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int getPriority() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPriority(int priority) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAddress() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
}