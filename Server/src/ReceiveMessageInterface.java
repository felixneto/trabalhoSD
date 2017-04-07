import java.rmi.*;
public interface ReceiveMessageInterface extends Remote
{
	void receiveMessage(String command, String myAddress) throws RemoteException;
}
