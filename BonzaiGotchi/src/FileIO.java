// BonzaiGotchi
// 2006-12-14 class created by chappy
// 2006-12-19       altered by chappy
//                  added read/write long

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.microedition.rms.*;



public class FileIO {
	
	private String recordName;
	
	private ByteArrayOutputStream baos;
	private DataOutputStream dos;
	
	private RecordStore rs;
	private ByteArrayInputStream bais;
	private DataInputStream dis;
	
	public FileIO(String recordName) {

		this.recordName = recordName;
	}
	
	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}
	
	public boolean copyRecord(String recordNameTarget) {
		byte [] record = getRecord();
		closeRecordStore();
		if (record != null) {
			if (writeRecord(recordNameTarget, record)) return true;
			else return false;
		}
		else return false;
	}
	
	public static String[] getRecordList() {
		return RecordStore.listRecordStores();
	}
	
	// IO Operations
	
	// writeDataInit has to be started before writing can begin
	public void writeDataInit(short versionSave) {
//		System.out.println("--- IO WRITE INIT BEGINN ---");
		
		baos = new ByteArrayOutputStream();
		dos = new DataOutputStream(baos);
		
		writeData (versionSave);
//		System.out.println("--- IO WRITE INIT ENDE ---");
	}
	
	public void writeData(String data) {

		// System.out.println("--- IO WRITE (STRING)---");				
		try {
			dos.writeUTF(data);
			// System.out.println("--- IO WRITE VALUE: " + data + " ---");
			
		} catch (IOException e) {
			// System.out.println("--- IO WRITE ERROR VALUE: " + data + " ---");
			e.printStackTrace();
		}
	}
	
	public void writeData(byte data) {
		
		// System.out.println("--- IO WRITE (BYTE)---");
		try {
			dos.writeByte(data);
			// System.out.println("--- IO WRITE VALUE: " + data + " ---");
			
		} catch (IOException e) {
			// System.out.println("--- IO WRITE ERROR VALUE: " + data + " ---");
			e.printStackTrace();
		}
		
	}
	
	public void writeData(short data) {
		
		// System.out.println("--- IO WRITE (SHORT)---");
		
		try {
			dos.writeShort(data);
			// System.out.println("--- IO WRITE VALUE: " + data + " ---");
			
		} catch (IOException e) {
			// System.out.println("--- IO WRITE ERROR VALUE: " + data + " ---");
			e.printStackTrace();
		}
		
	}
	
	public void writeData(int data) {

		// System.out.println("--- IO WRITE (INT)---");

		try {
			dos.writeInt(data);
			// System.out.println("--- IO WRITE VALUE: " + data + " ---");
			
		} catch (IOException e) {
			// System.out.println("--- IO WRITE ERROR VALUE: " + data + " ---");
			e.printStackTrace();
		}
		
	}
	
	public void writeData(boolean data) {

		// System.out.println("--- IO WRITE (BOOLEAN)---");

		try {
			dos.writeBoolean(data);
			// System.out.println("--- IO WRITE VALUE: " + data + " ---");
			
		} catch (IOException e) {
			// System.out.println("--- IO WRITE ERROR VALUE: " + data + " ---");
			e.printStackTrace();
		}
		
	}
	
	public void writeData(long data) {

		// System.out.println("--- IO WRITE (LONG)---");

		try {
			dos.writeLong(data);
			// System.out.println("--- IO WRITE VALUE: " + data + " ---");
			
		} catch (IOException e) {
			// System.out.println("--- IO WRITE ERROR VALUE: " + data + " ---");
			e.printStackTrace();
		}
		
	}
	
	// execute when writing has been finished
	public boolean writeDataFinalize() {
//		System.out.println("--- IO WRITE FINALIZE BEGINN ---");
			
		writeRecord(recordName, baos.toByteArray());
		
		
		baos = null;
		dos = null;
		
//		System.out.println("--- IO WRITE FINALIZE ENDE ---");
		return true;
	}

	
	// execute before starting to read
	public short readDataInit() {
		
//		System.out.println("--- IO READ INIT BEGINN ---");
		
		byte[] record = getRecord();
		
		if (record != null) {
		
	        bais = new ByteArrayInputStream(record);
	        dis = new DataInputStream(bais);
//	        System.out.println("--- IO READ INIT END ---");
	        try {
	        	return readDataShort(); // = versionSave
	        }
	        catch (Exception e) {
//	        	System.out.println("--- IO READ INIT END | ERROR ---");
	        	return 0;
	        }
	
		}
		else {
//			System.out.println("--- IO READ INIT END | 0 ---");
			return 0;
		}
	}
		
	public String readDataString() {
		
		// System.out.println("--- IO READ (STRING) ---");
		
		String tmpData = null;
		
		try {
			tmpData = dis.readUTF();
			// System.out.println("--- IO READ VALUE: " + tmpData + " ---");
			
		} catch (IOException e) {
			// System.out.println("--- IO READ ERROR ---");
			e.printStackTrace();
		}
		
		return tmpData;
		
	}
	
	public byte readDataByte() {
        
		// System.out.println("--- IO READ (BYTE) ---");
		
		byte tmpData = 0;
		
		try {
			tmpData = dis.readByte();
			// System.out.println("--- IO READ VALUE: " + tmpData + " ---");
			
		} catch (IOException e) {
			// System.out.println("--- IO READ ERROR ---");
			e.printStackTrace();
		}
		
		return tmpData;
		
	}
	
	public short readDataShort() {
        
		// System.out.println("--- IO READ (SHORT) ---");
		
		short tmpData = 0;
		
		try {
			tmpData = dis.readShort();
			// System.out.println("--- IO READ VALUE: " + tmpData + " ---");
			
		} catch (IOException e) {
			// System.out.println("--- IO READ ERROR ---");
			e.printStackTrace();
		}
		
		return tmpData;
		
	}

	public int readDataInt() {
				
		// System.out.println("--- IO READ (INT) ---");
		
		int tmpData = 0;

		try {
			tmpData = dis.readInt();
			// System.out.println("--- IO READ VALUE: " + tmpData + " ---");
			
		} catch (IOException e) {
			// System.out.println("--- IO READ ERROR ---");
			e.printStackTrace();
		}
		
		return tmpData;

	}
	
	public boolean readDataBoolean() {
		
		// System.out.println("--- IO READ (BOOLEAN) ---");
		
		boolean tmpData = false;

		try {
			tmpData = dis.readBoolean();
			// System.out.println("--- IO READ VALUE: " + tmpData + " ---");
			
		} catch (IOException e) {
			// System.out.println("--- IO READ ERROR ---");
			e.printStackTrace();
		}
		
		return tmpData;

	}
	
	public long readDataLong() {
		// System.out.println("--- IO READ (LONG) ---");
		
		long tmpData = 0;

		try {
			tmpData = dis.readLong();
			// System.out.println("--- IO READ VALUE: " + tmpData + " ---");
			
		} catch (IOException e) {
			// System.out.println("--- IO READ ERROR ---");
			e.printStackTrace();
		}
		
		return tmpData;
	}
	
	
	// just to clear vars
	// execute when finished reading
	public void readDataFinalize() {
//		System.out.println("--- IO READ FINALIZE BEGINN ---");
		bais = null;
		dis = null;
		closeRecordStore();
//		System.out.println("--- IO READ FINALIZE END ---");
	}
	
	public void deleteRecord() {
		deleteRecord(recordName);
	}
	
	

	public byte[] getRecord() {
		if (checkRecord(recordName)) {
			
			try {			
				rs = RecordStore.openRecordStore(recordName, false);
//		        System.out.println("--- IO OPEN RECORD ---");
			} catch (Exception e) {
				e.printStackTrace();
//				System.out.println("--- IO OPEN RECORD ERROR ---");
			}
		


	        RecordEnumeration re;
			try {
				re = rs.enumerateRecords(null,null,false);
//				System.out.println("--- IO READ RECORD ---");
				return rs.getRecord(re.nextRecordId());
				
				
			} catch (Exception e) {
//				System.out.println("--- IO READ RECORD ERROR ---");
				return null;
			}
		}
		else return null;

	}
	
	// internal Methods
	private boolean checkRecord (String recordName) {
		boolean recordExists = false;
		
		String[] recordList = RecordStore.listRecordStores();
		
		if (recordList != null) {
			
//			System.out.println ("--- IO RECORDNAME: " + recordName + " ---");
//			System.out.println ("--- IO RECORD LIST: " + recordList.length + " ---");
			
			for (int i = 0; i < recordList.length; i++) {
//				System.out.println ("--- IO RECORD LIST: " + i + ": " + recordList[i] + " ---");
				if (recordList[i].compareTo(recordName) == 0) {
					recordExists = true;
					break;
//					System.out.println ("--- IO RECORD EXISTS: " + i + ": " + recordList[i] + " ---");
				}
			}
		}
		return recordExists;
	}
	
	private boolean writeRecord(String recordName, byte[] record) {
		deleteRecord(recordName);		

		try {
			rs = RecordStore.openRecordStore(recordName, true);
			rs.addRecord(record, 0, record.length);
			closeRecordStore();
			return true;
//			System.out.println("--- IO RECORD " + recordName + " SAVED | " + record.length + " BYTES WRITTEN ---");
		} catch (RecordStoreException e) {
//			System.out.println("--- IO WRITE RECORD ERROR ---");
			return false;
		}
	}
	
	private void closeRecordStore(){
		try {
			if (rs != null) {
				rs.closeRecordStore();
			}
		} catch (RecordStoreNotOpenException e) {
//			System.out.println("--- IO RecordStore CLOSED ---");
			// e.printStackTrace();
		} catch (RecordStoreException e) {
//			System.out.println("--- IO RecordStore CLOSE ERROR ---");
//			e.printStackTrace();
		}
	}
	
	private void deleteRecord(String recordName) {
		if (checkRecord(recordName)) {
			try {
				RecordStore.deleteRecordStore (recordName);
			} catch (RecordStoreNotFoundException e) {
				// I dont really care
				e.printStackTrace();
			} catch (RecordStoreException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
