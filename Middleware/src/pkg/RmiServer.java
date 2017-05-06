package pkg;
import java.rmi.*;
import java.util.ArrayList;
public interface RmiServer
{
	String serverReceiveMessage(String command, String myAddress, ArrayList<RmiServer> allAddresses) throws RemoteException;
	boolean getStatus() throws RemoteException;
	int getPriority() throws RemoteException;
	void setPriority(int priority) throws RemoteException;
	String getAddress() throws RemoteException;
}