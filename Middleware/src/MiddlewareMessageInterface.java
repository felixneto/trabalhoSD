import java.rmi.*;
public interface MiddlewareMessageInterface extends Remote
{
	String middlewareReceiveMessage(String command, String myAddress) throws RemoteException;
}
