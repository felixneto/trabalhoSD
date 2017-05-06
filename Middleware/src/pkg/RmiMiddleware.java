package pkg;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Enumeration;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.*;
import java.net.UnknownHostException;

public class RmiMiddleware implements MiddlewareMessageInterface, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int status[];
	private int priority[];
	public String clientAddress; // localhost
	public int clientPort = 3030; // port
	public String middlewareAddress = "127.0.0.1"; // localhost
	private int middlewarePort = 3232;
	public ClientMessageInterface rmiClient;
	public ServerMessageInterface rmiServer;
	public int serverPort = 3434;
	public RmiServer Server;
	public int liderServerPosition;
	public ArrayList<ServerMessageInterface> hostServers;
	public Registry registry;
	static String name;

	public RmiMiddleware() throws RemoteException {

		try {
			
			Enumeration<NetworkInterface> interfaces = NetworkInterface
					.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface iface = interfaces.nextElement();
				// filters out 127.0.0.1 and inactive interfaces
				if (iface.isLoopback() || !iface.isUp())
					continue;

				Enumeration<InetAddress> addresses = iface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					InetAddress addr = addresses.nextElement();
					if (addr instanceof Inet6Address)
						continue;
					middlewareAddress = addr.getHostAddress();
				}
			}

			System.setSecurityManager(new SecurityManager());
			registry = LocateRegistry.createRegistry(middlewarePort);
			registry.rebind("rmiMiddleware", this);
			hostServers = new ArrayList<ServerMessageInterface>();
		} catch (SocketException e) {
			System.out.println("1: "+e.getMessage());
		} catch (RemoteException e) {
			throw e;
		}

	}

	private void hostElection() {
		if (hostServers.size() == 1)
			liderServerPosition = 0;
		else {

			for (int i = 0; i < this.hostServers.size(); i++) {
			}

			elect(0);
		}

	}

	public void elect(int inicial) {
		int aux;
		//inicial = inicial -1; caso não precise fazer toda eleição
		aux = inicial + 1;
		for (int i = 0; i < hostServers.size(); i++) {
			if (priority[inicial] < priority[i]) {
				//Aqui o servidor envia a mensagem; .getStatus() do server
				// nesse caso acho que quem verifica o servidor é o middleware
				System.out.println("Election message is sent from "
						+ (inicial + 1) + " to " + (i + 1));
				if (status[i] == 1)
					elect(i + 1);
			}
		}
	}

	public boolean checkStatusServer(String host) {
		// String serverAddress = ;
		for (ServerMessageInterface smi : hostServers) {
			try {
				if (smi.getAddress().equals(host))
					System.out.println("Servidor funcionando");
			} catch (RemoteException e) {
				System.out.println("Servidor com problema de conexão");
			}
		}
		try {
			registry = LocateRegistry.getRegistry(host, serverPort);
			rmiServer = (ServerMessageInterface) (registry.lookup("rmiServer"));
		} catch (NotBoundException | RemoteException e) {
			return false;
			// System.out.println("Servidor não está funcionando corretamente"+e.getMessage());
			// e.printStackTrace();
		}
		// System.out.println("Servidor está funcionando corretamente");
		return true;

	}

	@Override
	public String middlewareReceiveMessage(String command, String address)
			throws RemoteException {
		String s;

		try {
			//registry = LocateRegistry.getRegistry(liderServerAddress,	serverPort);
			
			s = hostServers.get(liderServerPosition).serverReceiveMessage(command, middlewareAddress, hostServers);
			return s;
//			rmiServer = (ServerMessageInterface) (registry.lookup("rmiServer"));
//			rmiServer.receiveMessageServer(command, middlewareAddress, hostServers);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return e.getMessage();
		}

	}

	public void addServer(ServerMessageInterface host) {
		hostServers.add(host);
		//status = new int[hostServers.size()];
		//priority = new int[hostServers.size()];
		hostElection();
	}

	public void removeServer(ServerMessageInterface host) {
		hostServers.remove(host);
		//status = new int[hostServers.size()];
		//priority = new int[hostServers.size()];
		hostElection();
	}

}
