import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.*;
import java.net.UnknownHostException;

public class RmiMiddleware implements MiddlewareMessageInterface, Serializable {
	private int status[];
	private int priority[];
	public String clientAddress; // localhost
	public int clientPort = 3030; // port
	public String middlewareAddress; // localhost
	private int middlewarePort = 3232;
	public ClientMessageInterface rmiClient;
	public ServerMessageInterface rmiServer;
	private final static int ServerPort = 3434;
	private String liderServerAddress;
	private String filename = "hosts.txt";
	private ArrayList<String> hostServers;

	static String name;

	String thisAddress = "127.0.0.1";
	Registry registry;

	public RmiMiddleware() throws RemoteException {

		try {
			// create the registry and bind the name and object.
			middlewareAddress = (InetAddress.getLocalHost()).toString();
			registry = LocateRegistry.createRegistry(middlewarePort);
			registry.rebind("rmiMiddleware", this);
			hostServers = new ArrayList<String>();

			// ReceiveMessageInterface rMI = (ReceiveMessageInterface)
			// UnicastRemoteObject.exportObject(this, thisPort);
			// registry.bind("RmiClient", rMI);
		} catch (RemoteException e) {
			throw e;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void hostElection() {
		if (hostServers.size() == 1)
			liderServerAddress = hostServers.get(0);
		else {
			this.hostServers = new ArrayList<String>();
			BufferedReader reader;
			int lines = 0;
			String host;
			try {

				reader = new BufferedReader(new FileReader(filename));
				host = reader.readLine();
				while (host != null) {
					this.hostServers.add(host);
					lines++;
					host = reader.readLine();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (int i = 0; i < this.hostServers.size(); i++) {
				try {
					registry = LocateRegistry.getRegistry(hostServers.get(i),
							ServerPort);
				} catch (RemoteException e) {

				}
			}
		}

	}

	public boolean checkStatusServer(String host) {
		// String serverAddress = ;
		try {
			registry = LocateRegistry.getRegistry(host, ServerPort);
			return true;
		} catch (RemoteException e) {
			return false;
			// e.printStackTrace();
		}

	}

	@Override
	public void receiveMessage(String command, String address)
			throws RemoteException {

		if (address != liderServerAddress) {
			try {
				registry = LocateRegistry
						.getRegistry(clientAddress, clientPort);
				rmiClient = (ClientMessageInterface) (registry.lookup("rmiClient"));
				rmiClient.receiveMessageClient(command, middlewareAddress);
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}

		} else {

			if (!checkStatusServer(liderServerAddress))
				hostElection();

			try {
				registry = LocateRegistry.getRegistry(liderServerAddress,
						ServerPort);
				rmiServer = (ServerMessageInterface) (registry
						.lookup("rmiServer"));
				rmiServer.receiveMessageServer(command, middlewareAddress, hostServers);
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
		}

	}

	public void addServer(String host) {
		hostServers.add(host);
		status = new int[hostServers.size()];
		priority = new int[hostServers.size()];
		hostElection();
	}

	public void removeServer(String host) {
		hostServers.remove(host);
		status = new int[hostServers.size()];
		priority = new int[hostServers.size()];
		hostElection();
	}

}
