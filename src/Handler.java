import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.thrift.TException;

public class Handler implements FileStore.Iface {
	private ArrayList<RFile> files = new ArrayList<>();
	private Coordinator coordinator = new Coordinator();
	private List<Transaction> transactions = new LinkedList<Transaction>();
	private List<Transaction> processingTransactions = new LinkedList<Transaction>();
	private List<ServerID> participants = new ArrayList<ServerID>();
	private int transactionId = 0;
	private String fileName = "transactions";

	public Handler() {
		System.out.println("recovery : "+recovery());
		serializeTransactions();
		createLogFile();
	}

	public Handler(Coordinator coordinator, List<ServerID> serversList) {
		this.coordinator = coordinator;
		this.participants = serversList;
		this.transactionId = 0;
	}

	public Coordinator getCoordinator() {
		return coordinator;
	}

	public void setCoordinator(Coordinator coordinator) {
		this.coordinator = coordinator;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public List<ServerID> getParticipants() {
		return participants;
	}

	public void setParticipants(List<ServerID> participants) {
		this.participants = participants;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public ArrayList<RFile> getFiles() {
		return files;
	}

	public void setFiles(ArrayList<RFile> files) {
		this.files = files;
	}

	@Override
	public Transaction deleteFile(String fName) throws SystemException{
		System.out.println("deleteFile");
		RFile rFile = new RFile();
		rFile.setFName(fName);
		Transaction transaction = coordinator.sendParticipantRequest(rFile, "d");
		return transaction;
	}

	@Override
	public Transaction writeFile(RFile rFile) throws SystemException {
		System.out.println("writeFile");
		Transaction transaction = coordinator.sendParticipantRequest(rFile, "w");
		return transaction;
	}

	@Override
	public RFile readFile(String filename) throws SystemException {
		RFile rFile = new RFile();
		rFile.setFName(filename);
		Transaction transaction = coordinator.sendParticipantRequest(rFile, "r");
		rFile.setContent(transaction.getContent());
		return rFile;
	}

	@Override
	public String participantReadFile(Transaction transaction)
			throws SystemException, TException {
		String readLine = "";
		StringBuffer line = new StringBuffer();
		try {
			File f = new File(transaction.getFName());
			if (!f.exists()) {
				return "File not found";
			}
			
			FileReader fileReader = new FileReader(transaction.getFName());
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((readLine = bufferedReader.readLine()) != null) {
				line.append(readLine);
				// line.append("\n");
				System.out.println("readFile : " + line.toString());
			}
			bufferedReader.close();
			fileReader.close();
			logTransaction(transaction);
			return line.toString();
		} catch (IOException e) {
			//e.printStackTrace();
			return "File not found.";
		}
	}

	@Override
	public Transaction canCommitWriteFile(Transaction transaction)
			throws SystemException, TException {
		try {
			File f = new File(transaction.getFName());
			System.out.println("cancommit : "+transactionHasLock(transaction));
			//Thread.sleep(5000);
			if (transactionHasLock(transaction)) {
				transaction.setCanCommit("n");
				transaction.setStatus("a");
				//logTransaction(transaction);
				//return transaction;
			} else {
				transaction.setCanCommit("y");
				transaction.setStatus("p");
			}
		} catch (Exception e) {
			logTransaction(transaction);
			return transaction;
		}
		logTransaction(transaction);
		return transaction;
	}

	@Override
	public Transaction canCommitDeleteFile(Transaction transaction)
			throws SystemException, TException {
		try {
			File f = new File(transaction.getFName());
			if (f.exists()) {
				if (transactionHasLock(transaction)) {
					transaction.setCanCommit("n");
					transaction.setStatus("a");
				} else {
					transaction.setStatus("p");
					transaction.setCanCommit("y");
				}
			}else{
				transaction.setCanCommit("n");
				transaction.setStatus("a");
			}
		} catch (Exception e) {
			logTransaction(transaction);
			return transaction;
		} 
		logTransaction(transaction);
		return transaction;
	}

	@Override
	public String fileOperation(Transaction transaction)
			throws SystemException, TException {
		transactions.add(transaction);
		System.out.println("in fileOperation : " + transactions.toString());
		return "test";
	}

	@Override
	public Transaction doCommitOrAbortWriteFile(Transaction transaction)
			throws SystemException, TException {
/*		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
*/		if (transaction.getDoCommitOrAbort().equalsIgnoreCase("c")) {
			// write file
			//tempFile.renameTo(new File(fileName));
			createFile(transaction.getFName(), transaction.getContent());
			transaction.setDoCommitOrAbort("c");
			transaction.setStatus("c");
			logTransaction(transaction);
		} else {
			// abort
			transaction.setDoCommitOrAbort("a");
			transaction.setStatus("a");
			logTransaction(transaction);
		// createFile(fileName, transaction.getContent());
		}
		return transaction;
	}

	@Override
	public Transaction doCommitOrAbortDeleteFile(Transaction transaction)
			throws SystemException, TException {
		String fileName = transaction.getFName();
		if (transaction.getDoCommitOrAbort().equalsIgnoreCase("c")) {
			// delete file
			File f = new File(fileName);
			if (f.delete()) {
				transaction.setDoCommitOrAbort("c");
				transaction.setStatus("c");
				logTransaction(transaction);
				return transaction;
			} else {
				transaction.setDoCommitOrAbort("a");
				transaction.setStatus("a");
				logTransaction(transaction);
				return transaction;
			}
		} else {
			// abort
			transaction.setDoCommitOrAbort("a");
			transaction.setStatus("a");
			logTransaction(transaction);
		}
		return transaction;
	}


	private void createFile1(String fileName, String contents) {
		try {
			File f = new File(fileName);
			f.createNewFile();
			FileWriter fileWriter = new FileWriter(".//" + fileName);
			fileWriter.write(contents);
			fileWriter.close();
		} catch (IOException e) {
			//e.printStackTrace();
			return;
		}
	}

	private void serializeTransactions() {
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(fileName+".txt");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(transactions);
			out.close();
			fileOut.close();
		} catch (Exception e) {
			//e.printStackTrace();
			return;
		}
		return;
	}
	
	private void createLogFile(){
		File f = new File(fileName+"log");
		try {
			if(!f.exists()){
				f.createNewFile();
				FileWriter fileWriter = new FileWriter(".//" + fileName+"log");
				fileWriter.write("");
				fileWriter.close();
			}	
		} catch (IOException e) {
			//e.printStackTrace();
			return;
		}
	}

	@SuppressWarnings("unchecked")
	private void deserializeTransactions() {
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

	private boolean transactionHasLock(Transaction currentTransaction) {
		boolean hasLock = false;
		int transactionId = -1;
		//deserializeTransactions();
		transactions = processingTransactions;
		//System.out.println("chk has lock for : "+currentTransaction.getId()+ " : "+transactions);
		for (Transaction transaction : transactions) {
			if (transaction.getFName().equalsIgnoreCase(currentTransaction.getFName())) {
				if ((currentTransaction.getId() != transaction.getId()) && (transaction.getStatus().equalsIgnoreCase("p"))) {
					hasLock = true;
					transactionId = transaction.getId();
				}
			}
		}
		if(hasLock && transactionId != -1){
			for (Transaction transaction : transactions) {
				if((transactionId == transaction.getId()) && 
						((transaction.getStatus().equalsIgnoreCase("c")) || (transaction.getStatus().equalsIgnoreCase("a")))){
					hasLock = false;
				}
			}
		}
		return hasLock;
	}

	private void logTransaction(Transaction transaction) {
		processingTransactions.add(transaction);
		//transactions = processingTransactions;
		deserializeTransactions();
		transactions.add(transaction);
		serializeTransactions();
		
		try
		{
		    FileWriter fw = new FileWriter(fileName+"log",true); 
		    fw.write(transaction.toString()+"\n");
		    fw.close();
		}
		catch(IOException ioe)
		{
			return;
		    //System.err.println("IOException: " + ioe.getMessage());
		}
	}

	private boolean createFile(String fileName, String contents) {
		boolean writeFlag = false;
		RandomAccessFile writeFile = null;
		FileChannel writeChannel = null;
		FileLock writeLock = null;
		try {
			File lockFile = new File(fileName);
			writeFile = new RandomAccessFile(lockFile, "rw");
			writeChannel = writeFile.getChannel();
			writeLock = writeChannel.tryLock();
			if (writeLock != null) {
				lockFile.deleteOnExit();
				writeChannel.write(ByteBuffer
						.wrap(contents.getBytes()));
				writeChannel.force(false);
				writeFlag = true;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		finally{
			// Always release the lock and close the file
			// Closing the RandomAccessFile also closes its FileChannel.
			try {
				if (writeLock != null && writeLock.isValid())
					writeLock.release();

				if (writeFile != null)
					writeFile.close();
			} catch (IOException e) {
				//e.printStackTrace();
				return false;
			}	
			return writeFlag;
		}
	}
	
	
	public List<Transaction> recovery(){
		boolean unprocessed = true;
		deserializeTransactions();
		List<Transaction> unprocessedTransactions = new LinkedList<Transaction>();
		Transaction unprocessedTransaction = new Transaction();
		int index = 0;
		int counter = 0;
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
			if(transaction.getOperation().equalsIgnoreCase("d")){
				File f = new File(transaction.getFName());
				f.delete();
				transaction.setDoCommitOrAbort("c");
				transaction.setStatus("c");
				logTransaction(transaction);
			}else if(transaction.getOperation().equalsIgnoreCase("w")){
				createFile(transaction.getFName(), transaction.getContent());
				transaction.setDoCommitOrAbort("c");
				transaction.setStatus("c");
				logTransaction(transaction);
			}
		}
		
		return unprocessedTransactions;
	}
}
