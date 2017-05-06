package pkg;
import java.io.IOException;
import java.rmi.RemoteException;


public class MainServer {
	
	public static void main (String[] argv) throws RemoteException
	{
		try {
			RmiServer rs = new RmiServer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
