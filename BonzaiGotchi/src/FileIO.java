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
	
	private ByteArrayInputStream bais;
	private DataInputStream dis;
	
	public FileIO(String tmpRecordName) {
		super();
		
		recordName = tmpRecordName;
	}
	
	// IO Operations
	
	// writeDataInit has to be started before writing can begin
	public void writeDataInit(short versionSave) {
		
		baos = new ByteArrayOutputStream();
		dos = new DataOutputStream(baos);
		
		writeData (versionSave);
		
	}
	
	public void writeData(String data) {

		System.out.println("--- IO WRITE (STRING)---");				
		try {
			dos.writeUTF(data);
			System.out.println("--- IO WRITE VALUE: " + data + " ---");
			
		} catch (IOException e) {
			System.out.println("--- IO WRITE ERROR VALUE: " + data + " ---");
			e.printStackTrace();
		}
	}
	
	public void writeData(byte data) {
		
		System.out.println("--- IO WRITE (BYTE)---");
		try {
			dos.writeByte(data);
			System.out.println("--- IO WRITE VALUE: " + data + " ---");
			
		} catch (IOException e) {
			System.out.println("--- IO WRITE ERROR VALUE: " + data + " ---");
			e.printStackTrace();
		}
		
	}
	
	public void writeData(short data) {
		
		System.out.println("--- IO WRITE (SHORT)---");
		
		try {
			dos.writeShort(data);
			System.out.println("--- IO WRITE VALUE: " + data + " ---");
			
		} catch (IOException e) {
			System.out.println("--- IO WRITE ERROR VALUE: " + data + " ---");
			e.printStackTrace();
		}
		
	}
	
	public void writeData(int data) {

		System.out.println("--- IO WRITE (INT)---");

		try {
			dos.writeInt(data);
			System.out.println("--- IO WRITE VALUE: " + data + " ---");
			
		} catch (IOException e) {
			System.out.println("--- IO WRITE ERROR VALUE: " + data + " ---");
			e.printStackTrace();
		}
		
	}
	
	public void writeData(boolean data) {

		System.out.println("--- IO WRITE (BOOLEAN)---");

		try {
			dos.writeBoolean(data);
			System.out.println("--- IO WRITE VALUE: " + data + " ---");
			
		} catch (IOException e) {
			System.out.println("--- IO WRITE ERROR VALUE: " + data + " ---");
			e.printStackTrace();
		}
		
	}
	
	public void writeData(long data) {

		System.out.println("--- IO WRITE (LONG)---");

		try {
			dos.writeLong(data);
			System.out.println("--- IO WRITE VALUE: " + data + " ---");
			
		} catch (IOException e) {
			System.out.println("--- IO WRITE ERROR VALUE: " + data + " ---");
			e.printStackTrace();
		}
		
	}
	
	// execute when reading has been finished
	public boolean writeDataFinalize() {
		
		RecordStore rs;
		
		byte[] record = baos.toByteArray();
		
		try {
			RecordStore.deleteRecordStore(recordName);
			System.out.println("--- DELETE RECORD ---");
		} catch (RecordStoreException e) {
			System.out.println("--- DELETE RECORD ERROR ---");
		}
		

		try {
			rs = RecordStore.openRecordStore(recordName, true);
			rs.addRecord(record, 0, record.length);
			rs.closeRecordStore();
			System.out.println("--- WRITE RECORD ---");
		} catch (RecordStoreException e) {
			System.out.println("--- WRITE RECORD ERROR ---");
			return false;
		}
		
		System.out.println("--- RECORD " + recordName + " SAVED ---");
		baos = null;
		dos = null;
		return true;
		
	}

	// execute before starting to read
	public short readDataInit() {
		
		System.out.println("--- IO READ ---");
		
		boolean recordExists = false;
		
		RecordStore rs = null;
		byte[] record = null;
		
		String[] recordList = RecordStore.listRecordStores() ;
		System.out.println ("--- IO RECORDNAME: " + recordName + " ---");
		System.out.println ("--- IO RECORD LIST ---");
		
		for (int i = 0; i < recordList.length; i++) {
			System.out.println ("--- IO RECORD LIST: " + recordList[i] + " ---");
			System.out.println ("--- IO RECORD Counter: " + i + " ---");
			if (recordList[i] == recordName) {
				System.out.println ("--- IO RECORD Counter: " + i + " ---");
				System.out.println ("--- IO RECORD LIST: " + recordList[i] + " ---");
				recordExists = true;
				System.out.println ("--- IO RECORD EXISTS: " + recordList[i] + " ---");
			}
		}
		
		if (recordExists) {
		
			try {			
				rs = RecordStore.openRecordStore(recordName, false);
		        System.out.println("--- IO OPEN RECORD ---");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("--- IO OPEN RECORD ERROR ---");
			}
		


	        RecordEnumeration re;
			try {
				re = rs.enumerateRecords(null,null,false);
				record = rs.getRecord(re.nextRecordId());
				System.out.println("--- IO READ RECORD ---");
			} catch (Exception e) {
				System.out.println("--- IO READ RECORD ERROR ---");
			}

	        bais = new ByteArrayInputStream(record);
	        dis = new DataInputStream(bais);
	        System.out.println("--- IO INPUTSTREAMS ---");
	        return readDataShort(); // = versionSave
	
		}
		else {
			return 0;
		}
	}
		
	public String readDataString() {
		
		System.out.println("--- IO READ (STRING) ---");
		
		String tmpData = null;
		
		try {
			tmpData = dis.readUTF();
			System.out.println("--- IO READ VALUE: " + tmpData + " ---");
			
		} catch (IOException e) {
			System.out.println("--- IO READ ERROR ---");
			e.printStackTrace();
		}
		
		return tmpData;
		
	}
	
	public byte readDataByte() {
        
		System.out.println("--- IO READ BYTE) ---");
		
		byte tmpData = 0;
		
		try {
			tmpData = dis.readByte();
			System.out.println("--- IO READ VALUE: " + tmpData + " ---");
			
		} catch (IOException e) {
			System.out.println("--- IO READ ERROR ---");
			e.printStackTrace();
		}
		
		return tmpData;
		
	}
	
	public short readDataShort() {
        
		System.out.println("--- IO READ BYTE) ---");
		
		short tmpData = 0;
		
		try {
			tmpData = dis.readShort();
			System.out.println("--- IO READ VALUE: " + tmpData + " ---");
			
		} catch (IOException e) {
			System.out.println("--- IO READ ERROR ---");
			e.printStackTrace();
		}
		
		return tmpData;
		
	}

	public int readDataInt() {
				
		System.out.println("--- IO READ (INT) ---");
		
		int tmpData = 0;

		try {
			tmpData = dis.readInt();
			System.out.println("--- IO READ VALUE: " + tmpData + " ---");
			
		} catch (IOException e) {
			System.out.println("--- IO READ ERROR ---");
			e.printStackTrace();
		}
		
		return tmpData;

	}
	
	public boolean readDataBoolean() {
		
		System.out.println("--- IO READ (BOOLEAN) ---");
		
		boolean tmpData = false;

		try {
			tmpData = dis.readBoolean();
			System.out.println("--- IO READ VALUE: " + tmpData + " ---");
			
		} catch (IOException e) {
			System.out.println("--- IO READ ERROR ---");
			e.printStackTrace();
		}
		
		return tmpData;

	}
	
	public long readDataLong() {
		System.out.println("--- IO READ (LONG) ---");
		
		long tmpData = 0;

		try {
			tmpData = dis.readLong();
			System.out.println("--- IO READ VALUE: " + tmpData + " ---");
			
		} catch (IOException e) {
			System.out.println("--- IO READ ERROR ---");
			e.printStackTrace();
		}
		
		return tmpData;
	}
	
	
	// just to clear vars
	// execute when finished reading
	public void readDataFinalize() {
		bais = null;
		dis = null;
	}
	
	
	
}
