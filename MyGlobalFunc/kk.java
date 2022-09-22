package MyGlobalFunc;


public class kk {
/*
 * Path path = Paths.get("C:\\Users\\noob\\Desktop\\ss.sql");
	    BasicFileAttributes attr;
	    try {
	    attr = Files.readAttributes(path, BasicFileAttributes.class);
	    System.out.println("Creation date: " + attr.creationTime());
	    //System.out.println("Last access date: " + attr.lastAccessTime());
	    //System.out.println("Last modified date: " + attr.lastModifiedTime());
	    } catch (IOException e) {
	    System.out.println("oops error! " + e.getMessage());	
		  Calendar cal = Calendar.getInstance();
		  for(int counter = 15 ; counter >= 1 ; counter--) {
			  while(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
			        cal.add(Calendar.DAY_OF_WEEK, -1);
			    }
			    System.out.println("Week " +counter+" Monday : " + cal.getTime());  
			    cal.add(Calendar.DAY_OF_WEEK, -1);
		
			    */


/*package pp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class start {

	public static void main(String[] args) {
		
	Path path = Paths.get("C:\\Users\\noob\\Desktop\\");
	    BasicFileAttributes attr;
	    try {
	    attr = Files.readAttributes(path, BasicFileAttributes.class);
	    System.out.println("Creation date: " + attr.creationTime());
	    //System.out.println("Last access date: " + attr.lastAccessTime());
	    //System.out.println("Last modified date: " + attr.lastModifiedTime());
	    } catch (IOException e) {
	    System.out.println("oops error! " + e.getMessage());	
		ArrayList<String> Dates_Mondays = new ArrayList<String>();
		String[] dat = new String[2];
		  Calendar cal = Calendar.getInstance();
		  for(int counter = 15 ; counter >= 1 ; counter--) {
			  dat[0] = "";
			  dat[1] = "";
			  
			  while(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
			        cal.add(Calendar.DAY_OF_WEEK, -1);
			   }
			   System.out.println("Week " +counter+" Monday : " + cal.getTime() );  
			   
			   if(String.valueOf( cal.get(Calendar.MONTH)+1).length()  == 1) {
				   dat[0] = "0"+String.valueOf( (cal.get(Calendar.MONTH)+1));
			   }else {
				   dat[0] = String.valueOf( (cal.get(Calendar.MONTH)+1));
			   }
			   
			   if(String.valueOf( cal.get(Calendar.DATE)+1).length() == 1) {
				   dat[1] = "0"+String.valueOf( cal.get(Calendar.DATE));
			   }else {
				   dat[1] = String.valueOf( cal.get(Calendar.DATE));
			   }
	
				   Dates_Mondays.add(cal.get(Calendar.YEAR)+"-"+dat[0]+"-"+dat[1]);  
			   cal.add(Calendar.DAY_OF_WEEK, -1);
		  }
		  
		  for(int counter = 0 ; counter < Dates_Mondays.size() ; counter++) {
	    	  System.out.println(Dates_Mondays.get(counter));
	      }
		
		/*
		File directoryPath = new File("C:\\Users\\noob\\Desktop");
	      //List of all files and directories
	      String contents[] = directoryPath.list();
	      System.out.println("List of files and directories in the specified directory:");
	     
	      ArrayList<String> Files_name = new ArrayList<String>();
	      
	      ArrayList<String> Dates = new ArrayList<String>();
	      
	      
	      
	      
	      for(int i=0; i<contents.length; i++) {
	    	  Path path = Paths.get("C:\\Users\\noob\\Desktop\\"+contents[i]);
	    	  
	  	    BasicFileAttributes attr;
	  	    try {
	  	    attr = Files.readAttributes(path, BasicFileAttributes.class);
	  	  Files_name.add("C:\\Users\\noob\\Desktop\\"+contents[i]);
    	  Dates.add(attr.creationTime().toString().split("T")[0]);
	  	    //System.out.println("Last access date: " + attr.lastAccessTime());
	  	    //System.out.println("Last modified date: " + attr.lastModifiedTime());
	  	    } catch (IOException e) {
	  	    System.out.println("oops error! " + e.getMessage());	
	  	    }
	         //System.out.println(contents[i]);
	      }
	      
	      for(int counter = 0 ; counter < Files_name.size() ; counter++) {
	    	  System.out.println("\nFile : "+Files_name.get(counter)+"   \n Date\n"+Dates.get(counter));
	      }
	*/	   
		 
}
