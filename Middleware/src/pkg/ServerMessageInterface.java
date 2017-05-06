package pkg;
import java.rmi.*;
import java.util.ArrayList;
public interface ServerMessageInterface extends Remote
{
	String serverReceiveMessage(String command, String myAddress, ArrayList<ServerMessageInterface> allAddresses) throws RemoteException;
	boolean getStatus() throws RemoteException;
	int getPriority() throws RemoteException;
	void setPriority(int priority) throws RemoteException;
	String getAddress() throws RemoteException;
}