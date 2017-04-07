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

	public String serverAddress = "127.0.0.1"; // localhost
	private int serverPort = 3434;
	public String middlewareAddress = "127.0.0.1"; // localhost
	private int middlewarePort = 3232;
	public ReceiveMessageInterface rmiMiddleware;
	public ServerMessageInterface rmiServer;
	int Port = 3434;
	static String name;
	Registry registry; // rmi registry for lookup the remote objects.

	public RmiServer() throws Exception {
		// try {
		// // get the address of this host.
		// //thisAddress = (InetAddress.getLocalHost()).toString();
		// } catch (Exception e) {
		// throw new RemoteException("can't get inet address.");
		// }
		// this port(registry�s port)
		// System.out.println("this address=" + thisAddress + ",port=" +
		// thisPort);
		try {
			// create the registry and bind the name and object.
			serverAddress = (InetAddress.getLocalHost()).toString();
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
	public void receiveMessageServer(String command, String address,
			ArrayList<String> hosts) throws RemoteException {
		if (hosts != null) {
			for (String host : hosts) {
				registry = LocateRegistry.getRegistry(host, serverPort);
				try {
					rmiServer = (ServerMessageInterface) (registry
							.lookup("rmiServer"));
				} catch (NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				rmiServer.receiveMessageServer(command, serverAddress, null);
			}

		} else {
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
			// TODO Auto-generated method stub
		}
	}
}