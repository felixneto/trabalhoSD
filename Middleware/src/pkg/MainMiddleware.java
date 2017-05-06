package pkg;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;


public class MainMiddleware {
	
	public static void main (String[] argv) throws RemoteException
	{
		RmiMiddleware rm = new RmiMiddleware();
		ServerMessageInterface server;
		String host;
		int choice;

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		//String line;
		System.out.println("Middleware para administrar os números de servidores. Para sair digite \"\\quit\"");
		//Statement stm;
		//PostgresConn banco = PostgresConn.getInstance("client", "client");
		Scanner sc = new Scanner(System.in);
		
		while(true)
		{
				System.out.println("Escolha:");
				System.out.println("1 para cadastrar servidor:");
				System.out.println("2 para remover um servidor:");
				System.out.println("3 para verificar status de um servidor:");
				System.out.println("4 para sair :");
				
				choice = sc.nextInt();
				switch (choice) 
				{
				
				case 1:
					System.out.println("Digite o endereço ip que deseja adicionar. (Ex: 192.168.123.110)");
					try {
						host = sc.next();
						rm.registry = LocateRegistry.getRegistry(host, rm.serverPort);
						server = (ServerMessageInterface) rm.registry.lookup("rmiServer");
						rm.addServer(server);
						System.out.println("Servidor adicionado com sucesso");
					}
					catch(RemoteException | NotBoundException e)
					{
						System.out.println("Há algum problema de conexão por favor verifique a o servidor ou a conexão./n"+e.getMessage());
					}
					
					
					break;
				case 2://
					System.out.println("Digite o endereço ip que deseja remover. (Ex: 192.168.123.110");
					
					try {
						host = sc.next();
						rm.registry = LocateRegistry.getRegistry(host, rm.serverPort);
						server = (ServerMessageInterface) rm.registry.lookup("rmiServer");
						rm.removeServer(server);
						System.out.println("Servidor removido com sucesso");
					}
					catch(RemoteException | NotBoundException e)
					{
						System.out.println("Há algum problema de conexão por favor verifique a o servidor ou a conexão.");
					}
					break;
				case 3:
					System.out.println("Digite o endereço ip do servidor corretamente. (Ex: 192.168.123.110");
					host = sc.next();
					if(rm.checkStatusServer(host))
						System.out.println("Servidor está funcionando corretamente.");
					else
						System.out.println("Servidor não está funcionando corretamente, por favor verifique a aplicação");
					break;
//				case 4: System.out.println("Você escolheu sair do middleware");
//				for (String s : rm.hostServers)
//				{
//					System.out.println(s);
//				}
//				System.out.println("Lider: "+rm.liderServerAddress);
//					
//					break;

				default: System.out.println("voce escolheu uma opção inválida"); break;
				}
				
//				rm.registry = LocateRegistry.getRegistry(c.middlewareAddress, c.middlewarePort);
//				// look up the remote object
//				rm.rmiMiddleware = (ReceiveMessageInterface) (c.registry.lookup("rmiMiddleware"));
//				// call the remote method
//				rm.rmiMiddleware.receiveMessage(line, c.clientAddress);
				//stm = PostgresConn.getInstance("client", "client").getConnection().createStatement();
				
				
		}
	}

}
