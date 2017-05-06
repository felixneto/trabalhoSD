import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Cliente implements ClientMessageInterface, Serializable{
	public String clientAddress; //localhost
	public int clientPort = 3030; //port
	public String middlewareAddress = "10.134.17.24"; //localhost
	public int middlewarePort = 3232;
	public MiddlewareMessageInterface rmiMiddleware;
	public String msg;
	public Registry registry;
	
	public Cliente () throws RemoteException {
		
		try {
			// create the registry and bind the name and object.
			clientAddress = InetAddress.getLocalHost().toString();
			registry = LocateRegistry.createRegistry(clientPort);
			registry.rebind("client", this);

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

	@Override
	public void receiveMessageClient(String command, String middlewareAddress) throws RemoteException {
		//RmiMiddleware middleware = new RmiMiddleware();
		System.out.println(command);
		
	}

}
