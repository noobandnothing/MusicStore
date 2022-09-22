package MyGlobalFunc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.io.BufferedWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

// My Custom Files Functions
public class MCFF {

	    public static void CreateFile(String FileName) {
	      try {
	        File file = new File(FileName);
	        if (file.createNewFile()) {
	        	//if(FileName.contains("Logs.txt") == false)
	              Logcat("I : File created: " + file.getName());
	        } else {
	        	//if(FileName.contains("Logs.txt") == false)
	              Logcat("I : File already exists.");
	        }
	      } catch (IOException e) {
	        //	if(FileName.contains("Logs.txt") == false)
	        		Logcat("E : An error occurred.");
	      }
	    }
	    
	    public static void ReplaceLine(String filename,int lineNumber, String data) throws IOException {
	        Path path = Paths.get(filename);
	        List<String> lines = Files.readAllLines(path);
	        lines.set(lineNumber - 1, data);
	        Files.write(path, lines);
	    }
	
	    public static void CreateFolder(String FolderName) {
	    	File folder = new File(FolderName);  
	        if(folder.mkdir()){  
	        	Logcat("I : Folder "+FolderName+" is created successfully");  
	        }else{  
	        	Logcat("E : Error Found!");  
	        }  
	    }
	    
	    public static ArrayList<String> ReadFile(String FileName) {
	    	ArrayList<String> data = new ArrayList<String>();
		    try {
			      File file = new File(FileName);
			      Scanner myReader = new Scanner(file);
			      while (myReader.hasNextLine()) {
			        data.add(myReader.nextLine());
			      }
			      myReader.close();
			    } catch (FileNotFoundException e) {
			      Logcat("E: An error occurred.");
			    }
		    return data;
	    }
	    
	    public static void WriteFile(String FileName ,String Content,Boolean append) {
	    	if (append == false) {
	    		  try {
	    	          FileWriter myWriter = new FileWriter(FileName);
	    	          myWriter.write(Content);
	    	          myWriter.close();
	    	          Logcat("Successfully wrote to "+FileName+".");
	    	        } catch (IOException e) {
	    	          Logcat("An error occurred.");
	    	        }
	    		
	    	}else if (append == true){
	    		  try {
	    			  File file = new File(FileName);
	  	            if(!file.exists()) {
	  	            	file.createNewFile();
	  	            }
	  	            FileWriter myWriter = new FileWriter(file.getName(),true);
	  	            BufferedWriter bufferwriter = new BufferedWriter(myWriter);
	  	            bufferwriter.write(Content);
	  	            bufferwriter.close();
	    		  } catch (IOException e) {
	    	          Logcat("An error occurred.");
	    	      }
	    	}
	      
	     }
	    
	    public static void RemoveEmpty(String File_name) {
		      String content="";
				Scanner file;
				try {
					file =new Scanner(new File(File_name));
					while(file.hasNext()){
						String line = file.nextLine();
						if(!line.equals("")) {
							content+=line+"\n";
						}
					}
					StringBuffer sb= new StringBuffer(content);
					sb.deleteCharAt(content.length()-1);
					content = sb.toString();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
				SetFileFree(File_name);
				Backup(File_name);
				WriteFile(File_name, content,false);
				SetFileReadonly(File_name);
		}
	    
	    public static void RemoveEmptyN(String File_name) {
		      String content="";
				Scanner file;
				try {
					file =new Scanner(new File(File_name));
					while(file.hasNext()){
						String line = file.nextLine();
						if(!line.equals("")) {
							content+=line+"\n";
						}
					}
					content = content.trim();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
				SetFileFree(File_name);
				Backup(File_name);
				WriteFile(File_name, content,false);
				SetFileReadonly(File_name);
		}
	    
        public static void Logcat(String Statment) {
	    	WriteFile("Logs",datetimenow(1)+" "+Statment+"\n",true);
	    }
            	    
	    public static ArrayList<ArrayList<Object>> get_FileContent(String FileName) {
	    	ArrayList<String> data = new ArrayList<String>();
	    	data = (ArrayList<String>) ReadFile(FileName);
	        ArrayList<ArrayList<Object>> AllInfo = new ArrayList<ArrayList<Object>>();
	    	for(int counterline = 0 ; counterline < data.size() ; counterline++) {
	    		String[] line= data.get(counterline).split("#",0);
		        ArrayList<Object> info = new ArrayList<Object>();
		        for(int counter = 0 ; counter < line.length ;counter++) {
		        			info.add(line[counter]);
		        	}
		            AllInfo.add(info);
	    	    }
	    	return AllInfo;
           }
	   
	    public static void Write_FileContent(String FileName,ArrayList<ArrayList<Object>> Data) {
	    	SetFileFree(FileName);
	    	WriteFile(FileName,"",false);
	    	String Line_objs = "";
	    	for(ArrayList<Object> objs_line  : Data) {
	    		   Line_objs = "";
				   for(Object obj : objs_line) {
					   if(Line_objs.equals("")) {
						   Line_objs += String.valueOf(obj);
					   }else {
						   Line_objs += "#"+String.valueOf(obj);	
					   }
				   }
				   Line_objs += "\n";
				   WriteFile(FileName,Line_objs,true);
			  }
	    	SetFileReadonly(FileName);
        }
	    
	    public static void Backup(String FileName) {
	    	CreateFolder("Backup");
	    	ArrayList<ArrayList<Object>> Data = MCFF.get_FileContent(FileName) ;
	    	String dest = FileName+"-"+datetimenow(5);
			MCFF.Write_FileContent(dest, Data);
			MoveFile(dest,"Backup\\"+dest);
	    }
		
	    public static Boolean MoveFile(String sourcePath, String targetPath) {
	        try {

	            Files.move(Paths.get(sourcePath), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
	            Logcat(sourcePath+ " was moved to "+targetPath+ " is in read only mode");
	        } catch (Exception e) {
	            Logcat("Moving " +sourcePath+ " to "+targetPath+ "Failed");
	            return false;
	            
	        }

	        return true;
	    }
	   
	    public static void SetFileReadonly(String FileName) {
	        File file = new File(FileName);
	        if(file.exists()) {
	        	file.setReadOnly();
	            file.setWritable(false);
	            Logcat(FileName+" is in read only mode");
	        }
	     }
	    
	    public static void SetFileFree(String FileName) {
	        File file = new File(FileName);
	        if(file.exists()) {
	        	file.setWritable(true);
		        Logcat(FileName+" is free");
	        }
 	       
	    }
	    
	    public static void DeleteFile(String FileName) { 
	        File myObj = new File(FileName); 
	        if (myObj.delete()) { 
	          Logcat("Deleted : " + FileName);
	        } else {
	          Logcat("Failed to delete "+FileName);
	        } 
	      } 
	    
	    public static String datetimenow(Integer Mode) {
	    	DateTimeFormatter dtf;
	    	switch(Mode) {
	    	case 1:
	 	        dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");  
	    	default:
		        dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");  
	    	}
	 		   return dtf.format(LocalDateTime.now());  

		}

	    public static Date Convert_FDStringtoDate(String FormatedDate,Integer mode) {
	    	Date dat = null;
	    	try {
	    		switch(mode) {
	    		case 1:
					dat = (Date)new SimpleDateFormat("yyyyMMddHHmmss").parse(FormatedDate);
	    			break;
	    		case 2:
					dat = (Date)new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").parse(FormatedDate);
	    			break;
	    		}
			} catch (ParseException e) {
				Logcat("E : MCFF Convert_FDStringtoDate()");
			}
	    	return dat;
	    }
	    
		public static String Convert_DatetoFDString(Date date , Integer mode) {
	    	String FD ;
			Calendar Getter = Calendar.getInstance();
			Getter.setTime(date);
			int year = Getter.get(Calendar.YEAR);
			int month = Getter.get(Calendar.MONTH)+1;
			int day = Getter.get(Calendar.DAY_OF_MONTH);
			int hour = Getter.get(Calendar.HOUR_OF_DAY);
			int minute = Getter.get(Calendar.MINUTE);
			int second = Getter.get(Calendar.SECOND);
	    	FD = String.valueOf(String.valueOf(year));
			switch(mode) {
			case 1:
				if(month < 10) {
					FD+="0"+String.valueOf(month);
				}else {
					FD+=String.valueOf(month);
				}
				if((day) < 10) {
					FD+="0"+String.valueOf(day);
				}else {
					FD+=String.valueOf(day);
				}
				if(hour < 10) {
					FD+="0"+String.valueOf(hour);
				}else {
					FD+=String.valueOf(hour);
				}
				if(minute < 10) {
					FD+="0"+String.valueOf(minute);
				}else {
					FD+=String.valueOf(minute);
				}
				if(second < 10) {
					FD+="0"+String.valueOf(second);
				}else {
					FD+=String.valueOf(second);
				}
				break;
			case 2:
				FD +="-";
				if(month < 10) {
					FD+="0"+String.valueOf(month);
				}else {
					FD+=String.valueOf(month);
				}
				FD +="-";
				if((day) < 10) {
					FD+="0"+String.valueOf(day);
				}else {
					FD+=String.valueOf(day);
				}
				FD +="-";
				if(hour < 10) {
					FD+="0"+String.valueOf(hour);
				}else {
					FD+=String.valueOf(hour);
				}
				FD +=":";
				if(minute < 10) {
					FD+="0"+String.valueOf(minute);
				}else {
					FD+=String.valueOf(minute);
				}
				FD +=":";
				if(second < 10) {
					FD+="0"+String.valueOf(second);
				}else {
					FD+=String.valueOf(second);
				}
				break;
			}

	    	return FD;
	    }
		
		// Backup
		public static void fillstart_LIN(ArrayList<Integer> arrlist,int size) {
			for(int counter = 0 ; counter < size ; counter++)
				arrlist.add(0);
		}
		
		public static void backup_LIN(ArrayList<Integer> arrlistS,ArrayList<Integer> arrlistD) {
			arrlistD.clear();
			fillstart_LIN(arrlistD,arrlistS.size());
			Collections.copy(arrlistD, arrlistS);
		}
		
		
		public static void fillstart_LBN(ArrayList<Boolean> arrlist,int size) {
			for(int counter = 0 ; counter < size ; counter++)
				arrlist.add(false);
		}
		
		public static void backup_LBN(ArrayList<Boolean> arrlistS,ArrayList<Boolean> arrlistD) {
			arrlistD.clear();
			fillstart_LBN(arrlistD,arrlistS.size());
			Collections.copy(arrlistD, arrlistS);
		}
		
		public static Integer check_boolean(Boolean input) {
			if(input.equals(true))
				return 1;
			return 0;
		}
		
		public static String  print_spaces(Integer num_spaces) {
			String content = "";
			for(int counter  = 0 ; counter < num_spaces ; counter++) {
				content+=" ";
			}
			return content;
		}

}
