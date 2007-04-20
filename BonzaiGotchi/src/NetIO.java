import java.io.*;
import java.util.Date;

import javax.microedition.io.*;

public class NetIO implements Runnable {

	private String url = "http://andreas.tschabuschnig.com/bonzaiGotchi.php";
	private Thread threadInet; 
	
	private FileIO data;
	private boolean succeded = false;
	
	public NetIO() {
	}
	
	public void sendData(FileIO data) {
		succeded = false;
		this.data = data;
		
		threadInet = new Thread(this);
		threadInet.start();
	}
	
	public void run() {
		try {
			postViaHttpConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
/*
	public void sendViaGet() {
        StreamConnection c = null;
        InputStream s = null;
        try {
            c = (StreamConnection)javax.microedition.io.Connector.open("http://andreas.tschabuschnig.com/bonzaiGotchi.php?data=test");
            s = c.openInputStream();
            int ch;
//            while ((ch = s.read()) != -1) {}
            if (s != null) 
                s.close();
            if (c != null)
                c.close();
        } catch (Exception e) {
        } 	
        System.out.println("fertig");
	}
*/
	
	
    void postViaHttpConnection() throws IOException {
        HttpConnection c = null;
        InputStream is = null;
        OutputStream os = null;

        try {
            c = (HttpConnection)Connector.open(url);
            System.out.println("Connector.open");

            // Set the request method and headers
            c.setRequestMethod(HttpConnection.POST);
//            c.setRequestProperty("If-Modified-Since", "29 Oct 1999 19:43:31 GMT");
            c.setRequestProperty("User-Agent", "Profile/MIDP-1.0 Configuration/CLDC-1.0");
            c.setRequestProperty("Content-Language", "de-AT");
            c.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // Getting the output stream may flush the headers
            os = c.openOutputStream();
            os.write("data=".getBytes());
//            os.write("test2".getBytes());
            os.write(parseXML().getBytes());
            os.flush();                // Optional, openInputStream will flush

//            process(data);
            System.out.println("x");
            
            // Opening the InputStream will open the connection
            // and read the HTTP headers. They are stored until
            // requested.
            is = c.openInputStream();

            // Get the ContentType
//            String type = c.getType();
//            processType(type);

            int len = (int)c.getLength();
            if (len > 0) {
                byte[] data = new byte[len];
                int actual = is.read(data);
                process(data);
            } else {
                int ch;
                while ((ch = is.read()) != -1) {
                    process((byte)ch);
                }
            }
            
            System.out.println("y");

//            if ((char)is.read() == (char)1) succeded = true;
//    		if (succeded) System.out.println("fertig - 1");
//    		else  System.out.println("fertig - 0");

        } finally {
            if (is != null)
                is.close();
            if (os != null)
                os.close();
            if (c != null)
                c.close();
        }
    }
	
    private String parseXML() {
    	
    	int childCounter = 0;
    	
    	String xml = "<?xml version=\"1.0\"?>\n";

    	// from Core    	
    	xml +=  "<BonzaiGotchi saveVersion="+data.readDataInit()+">\n";
    	   	
    	// from ScreenTree
    	xml +=  "<pot "+
    			"frozen=\""         +data.readDataBoolean()+"\" "+
    			"timeStamp=\""      +data.readDataLong()   +"\" "+
    			"water=\""          +data.readDataInt()    +"\" "+
    			"potSize=\""        +data.readDataByte()   +"\" "+
    			"dung=\""           +data.readDataShort()  +"\" "+
    			"dungCounter=\""    +data.readDataShort()  +"\">\n";   	    	
		
		// from Element
    	do {
    	
    	xml +=  "<element "+
				"length=\""         +data.readDataInt()    +"\" "+
				"thickness=\""      +data.readDataInt()    +"\" "+
				"angle=\""          +data.readDataShort()  +"\" "+
				"water=\""          +data.readDataInt()    +"\" "+
				"health=\""          +data.readDataShort()  +"\" "+
				"growStop=\""       +data.readDataBoolean()+"\" "+
				"color=\""          +data.readDataInt()    +"\" "+
				"colorNoAdaption=\""+data.readDataBoolean()+"\" ";
    	
    	boolean tmpChildLeft = data.readDataBoolean();
		boolean tmpChildCenter = data.readDataBoolean();
		boolean tmpChildRight = data.readDataBoolean();
		
		xml += 	"childLeft=\""      +tmpChildLeft          +"\" "+
				"childCenter=\""    +tmpChildCenter        +"\" "+
				"childRight=\""     +tmpChildRight         +"\" />\n";
		
		if (tmpChildLeft) {
			childCounter++;
		}
		if (tmpChildCenter) {
			childCounter++;
			
		}
		if (tmpChildRight) {
			childCounter++;
		}
		
		
    	} while (childCounter-- > 0);
		
		xml +=  "</pot>\n";
		xml +=  "</BonzaiGotchi>\n";
		
		return xml;
    }
    
    private void process(byte[] data) {
    	for (int i=0;i<data.length;i++) process(data[i]);
    }
    
    private void process(byte data) {
    	System.out.print((char)data);
    }
}

