import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;


public class MainMiddleware {
	
	public static void main (String[] argv) throws RemoteException
	{
		RmiMiddleware rm = new RmiMiddleware();
		int choice;

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		//String line;
		System.out.println("Middleware para administrar os números de servidores. Para sair digite \"\\quit\"");
		//Statement stm;
		//PostgresConn banco = PostgresConn.getInstance("client", "client");
		
		while(true)
		{
				System.out.println("Escolha:");
				System.out.println("1 para cadastrar servidor:");
				System.out.println("2 para remover um servidor:");
				System.out.println("3 para verificar status de um servidor:");
				System.out.println("4 para sair :");
				
				try {
					
					choice = System.in.read();
					switch (choice) 
					{
					
					case 1:
						System.out.println("Digite o endereço ip do servidor corretamente. (Ex: 192.168.123.110)");
						rm.addServer(br.readLine());
						
						break;
					case 2:
						System.out.println("Digite o endereço ip do servidor corretamente. (Ex: 192.168.123.110");
						rm.removeServer(br.readLine());
						break;
					case 3:
						System.out.println("Digite o endereço ip do servidor corretamente. (Ex: 192.168.123.110");
						if(rm.checkStatusServer(br.readLine()))
							System.out.println("Servidor está funcionando corretamente");
						else
							System.out.println("Servidor não está funcionando corretamente");
						break;
					//case 4: System.out.println("Você escolheu sair do middleware");
						//System.exit(0);
						//break;

					default: System.out.println("voce escolheu uma opção inválida"); break;
					}
				} catch (IOException e) {
					System.out.println("Não foi possível fazer a leitura correta do seu teclado. Por favor digite corretamente");
					//e.printStackTrace();
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
