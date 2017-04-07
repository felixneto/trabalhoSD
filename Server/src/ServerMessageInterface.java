import java.rmi.*;
import java.util.ArrayList;
public interface ServerMessageInterface extends Remote
{
	void receiveMessageServer(String command, String myAddress,ArrayList<String> allAddresses) throws RemoteException;
}
