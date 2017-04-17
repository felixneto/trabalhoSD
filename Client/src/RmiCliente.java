import java.io.Serializable;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Enumeration;

public class RmiCliente implements ClientMessageInterface, Serializable {
	public String clientAddress; // localhost
	public int clientPort = 3030; // port
	public String middlewareAddress = "0.0.0.0"; // localhost
	public int middlewarePort = 3232;
	public MiddlewareMessageInterface rmiMiddleware;
	public String msg;
	public Registry registry;

	public RmiCliente () throws RemoteException {
		
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
					clientAddress = addr.getHostAddress();
				}
			}
			// create the registry and bind the name and object.
			registry = LocateRegistry.createRegistry(clientPort);
			registry.rebind("client", this);

			// ReceiveMessageInterface rMI = (ReceiveMessageInterface)
			// UnicastRemoteObject.exportObject(this, thisPort);
			// registry.bind("RmiClient", rMI);
		} catch (SocketException e) {
			throw new RuntimeException(e);
		} catch (RemoteException e) {
			throw e;
		}
	}

	@Override
	public void receiveMessageClient(String command, String middlewareAddress)
			throws RemoteException {
		// RmiMiddleware middleware = new RmiMiddleware();
		System.out.println(command);

	}

}
