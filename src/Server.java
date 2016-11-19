import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

public class Server {
	public Handler handler;
	public FileStore.Processor<Handler> processor;
	public int port;

	public static void main(String[] args) {
		Server server = new Server();
		server.startServer(args);
	}

	public void startServer(String[] args) {
			handler = new Handler();
			processor = new FileStore.Processor<Handler>(handler);
			port = Integer.valueOf(args[0]);
			Runnable fileServer = new Runnable() {
				@Override
				public void run() {
					try {
						TServerTransport serverTransport = new TServerSocket(port);
						TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));
						System.out.println("Starting Server on port "+port);
						server.serve();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			};
		Thread serverThread=new Thread(fileServer);	
		serverThread.start();
	}
}
