package sn.ucad.esp.master;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileKuiper {
	public  static  byte [] readFully ( InputStream stream )  throws  IOException 
	{ 
	    byte [] buffer =  new  byte [ 8192 ]; 
	    ByteArrayOutputStream baos =  new  ByteArrayOutputStream ();

	    int bytesRead ; 
	    while  (( bytesRead = stream . read ( buffer ))  !=  - 1 ) 
	    { 
	        baos . write ( buffer ,  0 , bytesRead ); 
	    } 
	    return baos . toByteArray (); 
	}
	
	
	public  static  byte [] loadFile ( String sourcePath )  throws  IOException 
	{ 
	    InputStream inputStream =  null ; 
	    try  
	    { 
	        inputStream =  new  FileInputStream ( sourcePath ); 
	        return readFully ( inputStream ); 
	    }  
	    finally 
	    { 
	        if  ( inputStream !=  null ) 
	        { 
	            inputStream . close (); 
	        } 
	    } 
	}
}