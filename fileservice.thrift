typedef string UserID
typedef i64 Timestamp

struct ServerID {
  1:string ip;
  2:i32 port;
}


exception SystemException {
  1: optional string message
}

enum Status {
  FAILED = 0;
  SUCCESSFUL = 1;
}

struct StatusReport {
  1: required Status status;
}

struct RFile {
  1: optional string fName;
  2: optional string content;
  3: optional string isLocked;
}

struct Transaction {
  1: optional i32 id;	
  2: optional string fName;
  3: optional string content;
  4: optional string doCommitOrAbort;
  5: optional string operation;
  6: optional string canCommit;
  7: optional string status; 
}


service FileStore {
  
  Transaction writeFile(1: RFile rFile)
    throws (1: SystemException systemException),
  
  RFile readFile(1: string filename)
    throws (1: SystemException systemException),

  Transaction deleteFile(1: string fName)
    throws (1: SystemException systemException),

  Transaction canCommitWriteFile(1: Transaction transaction)
    throws (1: SystemException systemException),
  
  string participantReadFile(1: Transaction transaction)
    throws (1: SystemException systemException),

  Transaction canCommitDeleteFile(1: Transaction transaction)
    throws (1: SystemException systemException),
	
  string fileOperation(1: Transaction transaction )
    throws (1: SystemException systemException),
	
  Transaction doCommitOrAbortWriteFile(1: Transaction transaction)
    throws (1: SystemException systemException),
  
  Transaction doCommitOrAbortDeleteFile(1: Transaction transaction)
    throws (1: SystemException systemException),
	 	
}
