import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class Coordinator {
	private static Map<String, String> files = new TreeMap<String, String>();
	private static List<ServerID> serversList = new ArrayList<ServerID>();
	public Handler handler;
	public FileStore.Processor<Handler> processor;
	public int port;
	//private List<Transaction> transactions = new ArrayList<Transaction>();
	private static List<Transaction> transactions = new LinkedList<Transaction>();
	private Queue<Transaction> processedTransactions = new LinkedList<Transaction>();
	private int transactionId = 0;
	private static String fileName = "transactions";

	public static void main(String[] args){
		Coordinator coordinator = new Coordinator();
		recovery(coordinator);
		createLogFile();
		serializeTransactions();
		coordinator.parseServers(args[0]);
		coordinator.startServer(args);
	}
	
	private void parseServers(String fileName){
		String readLine = "";
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while((readLine = bufferedReader.readLine())!=null){
				String serverDetails[] = readLine.split(" ");	
				ServerID serverID = new ServerID();
				serverID.setIp(serverDetails[0]);
				serverID.setPort(Integer.valueOf(serverDetails[1]));
				serversList.add(serverID);
			}
			bufferedReader.close();
			fileReader.close();
			System.out.println("Servers : "+serversList.toString());
		} catch (IOException e) {
			//e.printStackTrace();
			return;
		}
	}
	
	public void startServer(String[] args) {
		handler = new Handler(Coordinator.this, serversList);
		processor = new FileStore.Processor<Handler>(handler);
		port = Integer.valueOf(args[2]);
		Runnable fileServer = new Runnable() {
			@Override
			public void run() {
				try {
					//TServerTransport serverTransport = new TServerSocket(port);
					//TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));
					TServerSocket serverTransport = new TServerSocket(port);
					TServer server=new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
					
					System.out.println("Starting Coordinator on port "+port);
					server.serve();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		};
	Thread serverThread=new Thread(fileServer);	
	serverThread.start();
	
	////////
	/*Runnable transaction = new Runnable() {
		public void run() {
			canCommit();
		}
	};
	
		Thread transactionThread=new Thread(transaction);		
		transactionThread.start();*/
	}
		
	
	protected Transaction sendParticipantRequest(RFile rFile, String operation){
		Transaction transaction =  setTransaction(rFile, operation);
		int noOfparticipants = serversList.size();
		int noOfCanCommitReplies = 0;
		List<ServerID> failedParticipants = new ArrayList<ServerID>();
		ServerID currentServer = new ServerID();
		for (ServerID server : serversList){	
			currentServer = server;
			TTransport tTransport = new TSocket(server.getIp(), server.getPort(), 15000);
			try {
					tTransport.open();
					TProtocol protocol = new TBinaryProtocol(tTransport);
					FileStore.Client client = new FileStore.Client(protocol);
					//to-do spawn new thread
					logTransaction(transaction);
					if(transaction.getOperation().equalsIgnoreCase("r")){
						//to-do
						String contents = client.participantReadFile(transaction);
						transaction.setContent(contents);
						//processedTransactions.add(transaction);
						//break;
					}else if(transaction.getOperation().equalsIgnoreCase("w")){
						transaction = client.canCommitWriteFile(transaction);
						String canCommit = transaction.getCanCommit();
						if(canCommit.equalsIgnoreCase("y")){
							noOfCanCommitReplies++;
						}else{
							failedParticipants.add(server);
						}
						
					}else if(transaction.getOperation().equalsIgnoreCase("d")){
						transaction = client.canCommitDeleteFile(transaction);
						String canCommit = transaction.getCanCommit();
						if(canCommit.equalsIgnoreCase("y")){
							noOfCanCommitReplies++;
						}else{
							failedParticipants.add(server);
						}
					}
					logTransaction(transaction);
					//rFile.setFName("test.txt");
					//rFile.setContent("dsdsdsd");
					//client.writeFile(rFile);
					tTransport.close();
			} catch (TTransportException e1) {
				failedParticipants.add(currentServer);
				transaction.setDoCommitOrAbort("a");
				doCommitOrAbort(noOfCanCommitReplies, noOfparticipants, transaction);
				//e1.printStackTrace();
			} catch (TException e) {
				failedParticipants.add(currentServer);
				transaction.setDoCommitOrAbort("a");
				doCommitOrAbort(noOfCanCommitReplies, noOfparticipants, transaction);
				//e.printStackTrace();
			}
		}
		if(!transaction.getOperation().equalsIgnoreCase("r")){
			transaction = doCommitOrAbort(noOfCanCommitReplies, noOfparticipants, transaction);
		}	
		return transaction;
	}

	protected Transaction doCommitOrAbort(int noOfCanCommitReplies, int noOfparticipants, Transaction transaction){
		//System.out.println("noOfCanCommitReplies : "+noOfCanCommitReplies+" , noOfparticipants : "+noOfparticipants);
		for (ServerID server : serversList){
			TTransport tTransport = new TSocket(server.getIp(), server.getPort(), 5000);
			try {
					tTransport.open();
					TProtocol protocol = new TBinaryProtocol(tTransport);
					FileStore.Client client = new FileStore.Client(protocol);
					if(noOfCanCommitReplies == noOfparticipants){
						if(transaction.getOperation().equalsIgnoreCase("w")){
							transaction.setDoCommitOrAbort("c");
							transaction = client.doCommitOrAbortWriteFile(transaction);
						} else if(transaction.getOperation().equalsIgnoreCase("d")){
							transaction.setDoCommitOrAbort("c");
							transaction = client.doCommitOrAbortDeleteFile(transaction);
						}	
					} else{
						if(transaction.getOperation().equalsIgnoreCase("w")){
							transaction.setDoCommitOrAbort("a");
							transaction = client.doCommitOrAbortWriteFile(transaction);
						} else if(transaction.getOperation().equalsIgnoreCase("d")){
							transaction.setDoCommitOrAbort("a");
							transaction = client.doCommitOrAbortDeleteFile(transaction);
						}
					}
					logTransaction(transaction);
					tTransport.close();
			} catch (TTransportException e1) {
				e1.printStackTrace();
				transaction.setStatus("a");
				return transaction;
			} catch (TException e) {
				e.printStackTrace();
				transaction.setStatus("a");
				return transaction; 
			}
		}
		return transaction;
	}
	protected void canCommit() {/*
		System.out.println("in canCommit");
		int noOfTransactions = transactions.size();
		while(true){
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			//System.out.println(transactions.isEmpty());
			if(!transactions.isEmpty()){
				///spawning thread 
				System.out.println("new trans thread");
				Runnable trans = new Runnable() {
					public void run() {
//						try {
//							//Thread.sleep(2000);
//						} catch (InterruptedException e2) {
//							// TODO Auto-generated catch block
//							e2.printStackTrace();
//						}
						Transaction transaction = transactions.remove();
						for (ServerID server : serversList){	
							System.out.println("new server thread");
							TTransport tTransport = new TSocket(server.getIp(), server.getPort());
							try {
									tTransport.open();
									TProtocol protocol = new TBinaryProtocol(tTransport);
									FileStore.Client client = new FileStore.Client(protocol);
									//to-do spawn new thread
									if(transaction.getOperation().equalsIgnoreCase("r")){
										//to-do
										RFile rFile = client.readFile(transaction.getFName());
										transaction.setContent(rFile.getContent());
										processedTransactions.add(transaction);
										break;
									}else{
										client.fileOperation(transaction);
										
									}
									//rFile.setFName("test.txt");
									//rFile.setContent("dsdsdsd");
									//client.writeFile(rFile);
									tTransport.close();
							} catch (TTransportException e1) {
								e1.printStackTrace();
							} catch (TException e) {
								e.printStackTrace();
							}
						}
					}
				};
				
					Thread transactionThread=new Thread(trans);		
					transactionThread.start();
				
			//
			}	
		}
	*/}

	
	public Transaction setTransaction(RFile rFile, String operation){
		Transaction transaction = new Transaction();
		transaction.setId(transactionId++);
		if(operation.equalsIgnoreCase("w")){
			transaction.setFName(rFile.getFName());
			transaction.setContent(rFile.getContent());
			transaction.setOperation("w");
		} else if(operation.equalsIgnoreCase("r")){
			transaction.setFName(rFile.getFName());
			transaction.setOperation("r");
		} else if(operation.equalsIgnoreCase("d")){
			transaction.setFName(rFile.getFName());
			transaction.setOperation("d");
		}
		return transaction;
	}
	
	public static List<Transaction> recovery(Coordinator coordinator){
		boolean unprocessed = true;
		deserializeTransactions();
		List<Transaction> unprocessedTransactions = new LinkedList<Transaction>();
		Transaction unprocessedTransaction = new Transaction();
		int index = 0;
		int counter = 0;
			try{
				for (Transaction transaction : transactions) {
				index = 0;
				counter = 0;
				if(!transaction.getOperation().equalsIgnoreCase("r")){
					for (Transaction tempTransaction : transactions) {
						counter++;
						if(transaction.getId() == tempTransaction.getId()){
							if(tempTransaction.isSetStatus()){
								if((tempTransaction.getStatus().equalsIgnoreCase("p")) && (tempTransaction.getCanCommit().equalsIgnoreCase("y"))){
									index = counter - 1;
									unprocessedTransaction = tempTransaction;
								}	
							}
						}
					}
					
					for(int i = index; i < transactions.size(); i++){
						if(unprocessedTransaction.getId() == transactions.get(i).getId()){
							if(transactions.get(i).isSetStatus()){
								if((transactions.get(i).getStatus().equalsIgnoreCase("a")) || (transactions.get(i).getStatus().equalsIgnoreCase("c"))){
									unprocessed = false;
								}	
							}
						}	
					}
					if(unprocessed){
						unprocessedTransactions.add(transactions.get(index));
					}
				}	
			}
			
			for (Transaction transaction : unprocessedTransactions) {
				RFile rFile = new RFile();
				rFile.setFName(transaction.getFName());
				rFile.setContent(transaction.getContent());
				coordinator.sendParticipantRequest(rFile, transaction.getOperation());
			}
		} catch(Exception e){
			return null;
		}
		return unprocessedTransactions;
	}
	
	private static void serializeTransactions() {
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(fileName+".txt");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(transactions);
			out.close();
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		return;
	}
	
	private static void createLogFile(){
		File f = new File(fileName+"log");
		try {
			f.createNewFile();
			FileWriter fileWriter = new FileWriter(".//" + fileName+"log");
			fileWriter.write("");
			fileWriter.close();
		} catch (IOException e) {
			//e.printStackTrace();
			return;
		}
	}

	private static void deserializeTransactions() {
		try {
			FileInputStream fileIn = new FileInputStream(fileName+".txt");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			transactions = (List<Transaction>) in.readObject();
			in.close();
			fileIn.close();
		} catch (Exception e) {
			//e.printStackTrace();
			return;
		}
	}
	
	private static void logTransaction(Transaction transaction) {
		System.out.println("before : "+transactions);
		//deserializeTransactions();
		System.out.println("after : "+transactions);
		deserializeTransactions();
		transactions.add(transaction);
		serializeTransactions();
		
		try
		{
		   // String filename= "MyFile.txt";
		    FileWriter fw = new FileWriter(fileName+"log",true); //the true will append the new data
		    fw.write(transaction.toString()+"\n");//appends the string to the file
		    fw.close();
		}
		catch(IOException ioe)
		{
		    System.err.println("IOException: " + ioe.getMessage());
		}
	}
}
