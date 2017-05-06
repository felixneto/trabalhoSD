package pkg;
import java.rmi.*;
public interface ClientMessageInterface extends Remote
{
	void receiveMessageClient(String command, String myAddress) throws RemoteException;
}
