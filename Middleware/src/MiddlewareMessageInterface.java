import java.rmi.*;
public interface MiddlewareMessageInterface extends Remote
{
	void receiveMessage(String command, String myAddress) throws RemoteException;
}
