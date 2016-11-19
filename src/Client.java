import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TIOStreamTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class Client {
	private static List<String> operations = new ArrayList<String>();
	
	public static void main(String[] args) throws SystemException {
		readFile(args[2],true);
		for (String transaction : operations) {
			TTransport tTransport = new TSocket(args[0], Integer.valueOf(args[1]));
			try {
				tTransport.open();
			} catch (TTransportException e1) {
				e1.printStackTrace();
			}
			TProtocol protocol = new TBinaryProtocol(tTransport);
			FileStore.Client client = new FileStore.Client(protocol);
			
			String operation = null;
			String fileName = null;
			String contents = null;
			operation = getArgument(transaction.split(" "), "--operation");
			fileName = getArgument(transaction.split(" "), "--filename");
			contents = getArgument(transaction.split(" "), "--contents");
			if(operation.equalsIgnoreCase("write")){ 
				if(contents == null){
					contents = readFile(fileName, false);
				}else{
					contents = transaction.substring(transaction.indexOf("(")+1, transaction.lastIndexOf(")"));
				}
			}
			if (operation.equalsIgnoreCase("read")) {
				RFile rFile = new RFile();
				try {
					rFile = client.readFile(fileName);
					System.out.println("Contents of "+fileName+"\n"+rFile.getContent());
					//TIOStreamTransport streamTransport = new TIOStreamTransport(System.out);
					//TJSONProtocol tjsonProtocol = new TJSONProtocol(streamTransport);
					
				} catch (TException e) {
						e.printStackTrace();
				}
			} else if (operation.equalsIgnoreCase("write")) {
				//TIOStreamTransport streamTransport = new TIOStreamTransport(System.out);
				//TJSONProtocol tjsonProtocol = new TJSONProtocol(streamTransport);
				try {
					RFile rFile = new RFile();
					rFile.setFName(fileName);
					rFile.setContent(contents);
					if(rFile != null){
						Transaction transactionResult = client.writeFile(rFile);
						//System.out.println("can commit : "+transactionResult.getCanCommit()+ " , do commit/abort : "+transactionResult.getDoCommitOrAbort());
						if(transactionResult.getStatus().equalsIgnoreCase("c")){
							System.out.println("Commit successful");
						}else{
							System.out.println("Commit unsuccessful");
						}
						//report.write(tjsonProtocol);
					}else{
						System.out.println("File doesn't exists.\n");
					}
				} catch (TException e) {
						e.printStackTrace();
				} 

			} else if(operation.equalsIgnoreCase("delete")){

				try {
					Transaction transactionResult = client.deleteFile(fileName);
					if(transactionResult.getStatus().equalsIgnoreCase("c")){
						System.out.println("Commit successful");
					}else{
						System.out.println("Commit unsuccessful");
					}
					//report.write(tjsonProtocol);
					//System.out.println("can commit : "+transactionResult.getCanCommit()+ " , do commit/abort : "+transactionResult.getDoCommitOrAbort());
				} catch (TException e) {
						e.printStackTrace();
				}
			}
			tTransport.close();
		}
	}

	private static String getArgument(String[] args, String token) {
		System.out.println(args.toString());
		String contents = "";
		int index = 0;
		if(token.equalsIgnoreCase("--contents")){
			for (int i = 0; i < args.length; i++) {
				if(args[i].equalsIgnoreCase("--contents")){
					index = i; 
				}	
			}
			for(int i = index+1; i < args.length;i++){
				contents += args[i];
				System.out.println(contents);
			}
			return contents;
		}
		
		
		for (int i = 0; i < args.length; i++) {
			if (args[i].equalsIgnoreCase(token)) {
				return args[++i];
			}
		}
		return contents;
	}


	public static String readFile(String fileName, boolean addToOperationsList){
		String readLine = "";
		StringBuffer line = new StringBuffer();
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while((readLine = bufferedReader.readLine())!=null){
				line.append(readLine);
				if(addToOperationsList){
					operations.add(readLine);
				}
			}
			bufferedReader.close();
			fileReader.close();
			return line.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
