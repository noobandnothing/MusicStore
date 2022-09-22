package MusicStore;

import java.io.IOException;
import java.util.ArrayList;

import MyGlobalFunc.MCFF;

public class Employee extends Person{
	private final String path = "Employee";
	private String username;
	private String password;
	private Boolean manager;
	private Boolean status;
	
	public Employee() {
		
	}
	
	public Employee(String Name,String username ,String password,Boolean manager,Boolean Writing_Switch){
		super(Name);
		this.id = create_id();
		this.username = username; 
		this.password = password;
		this.manager = manager;
		this.status = true;
		if(Writing_Switch) {
			push_obj(this.path);
		}
	   }

	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}
	
	public Integer getnumObj() {
		return this.getNumObjs();
	}	
	
	public Integer num_of_employee() {
		return getNumObjs(); 
	}
	
	@Override
	protected  Integer create_id() {
		Integer GeneratedNum = 0;
		try {
			GeneratedNum =  (2000+1+getNumObjs());
		} catch (Exception e) {
			MCFF.Logcat("E : Employee Id Creation.");
		}
		return GeneratedNum;
	}
	
	@Override
	protected Integer getNumObjs() {
		Integer objs_num = 0;
		try {
			MCFF.CreateFile(this.path);
			objs_num = MCFF.get_FileContent(this.path).size();
		} catch (Exception e) {
			MCFF.Logcat("E : Employee Getting Number of Objects.");
		}
		return objs_num;
	}
	
	@Override
	protected void push_obj(String File_name) {
		String content;
		if(getNumObjs() == 0) {
     		content = this.id+"#"+this.name+"#"+this.username+"#"+this.password+"#"+this.manager+"#"+this.status;
     		MCFF.SetFileFree(File_name);
			MCFF.WriteFile(this.path,content, true);
			MCFF.SetFileReadonly(File_name);
		}else {
			Integer founded_id = SearchByName(this.name);
			if(founded_id == null){
				content = "\n"+this.id+"#"+this.name+"#"+this.username+"#"+this.password+"#"+this.manager+"#"+this.status;
				MCFF.SetFileFree(File_name);
				MCFF.Backup(this.path);
				MCFF.WriteFile(this.path,content, true);
				MCFF.SetFileReadonly(File_name);
			}else {
				MCFF.Logcat("W : Employee is already exist");
			}
		}
	}
	
	@Override
	public Boolean FillById(Integer id) {
		 Boolean flag = false;
		 ArrayList<ArrayList<Object>> Data = MCFF.get_FileContent(this.path);
		 if(id >= Integer.valueOf(Data.get(0).get(0).toString())  && id <= Integer.valueOf(Data.get(Data.size()-1).get(0).toString())) {
			   this.id = id;
			   this.name = String.valueOf(Data.get(id-2001).get(1));
			   this.username = String.valueOf(Data.get(id-2001).get(2));
			   this.password = String.valueOf(Data.get(id-2001).get(3));
			   this.manager = Boolean.parseBoolean((String) Data.get(id-2001).get(4));
			   this.status = Boolean.parseBoolean((String) Data.get(id-2001).get(5));
			   flag = true;
		 }
		 return flag;
	}
	
	@Override
	public  Boolean FillByName(String element) {
		Boolean flag = false;
		Integer founded_id = SearchByName(element);
		 if(founded_id != null) {
			 if(FillById(founded_id) == true) {
				 flag = true;
			 }
		 }
		 return flag;
	}
	
	@Override
	protected Integer SearchByName(String element) {
			 ArrayList<ArrayList<Object>> Data = MCFF.get_FileContent(this.path) ;
			  for(int counter =0 ; counter < Data.size() ; counter++) {
				  if(element.equals(String.valueOf(String.valueOf(Data.get(counter).get(1))))) {
					   return (Integer.valueOf(Data.get(counter).get(0).toString()));
				 }
			  }
			  return null;
	}
	
	public  void SearchByUserName(String element) {
		 ArrayList<ArrayList<Object>> Data = MCFF.get_FileContent(this.path) ;
		 for(int counter =0 ; counter < Data.size() ; counter++) {
			   this.username = String.valueOf(Data.get(counter).get(2));
			   if(username.equals(element)) {
				   this.FillById(Integer.valueOf(Data.get(counter).get(0).toString()));
				   break;
			   }
		 }
    }

	@Override
	public  Boolean RenameById(Integer id,String NewName) {
		 Boolean flag = false;
		 ArrayList<ArrayList<Object>> Data = MCFF.get_FileContent(this.path);
			 if(FillById(id)) {
				   Data.get(id-2001).set(1, NewName);	
				   MCFF.Backup(this.path);
				   MCFF.Write_FileContent(this.path,Data);
				   MCFF.Logcat("E : Employee " +id+" Name was changed.");
				   flag = true;
		     }
		 return flag;
	}
	
	
	public Boolean CheckUserName(String username) {
		this.SearchByUserName(username);
		if(this.password != null) {
				return true;
		}
		return false;
	}
	
	public Boolean CheckPassword(String password) {
		if(this.password != null) {
			if(this.password.equals(password))
				return true;
		}
		return false;
	}
	
	public Boolean CheckManger() {
		if(this.manager != null) {
			if(this.manager.equals(true)) {
				return true;
			}
		}
		return false;
	}
	
	public Boolean CheckStatus() {
		if(this.status != null) {
			if(CheckManger()) {
				return true;
			}else {
				if(this.status.equals(true))
					return true;
			}
		}
		return false;
	}
	
	public Boolean ActivatedE(Integer id) {
		Boolean flag = false;
		   if(FillById(id)) {
				 String content;
				 this.status = true;
		     	 content = this.id+"#"+this.name+"#"+this.username+"#"+this.password+"#"+this.manager+"#"+this.status;
				 MCFF.Backup(this.path);
				 MCFF.SetFileFree(this.path);
				 try {
					MCFF.ReplaceLine(this.path,this.id-2000,content);
				} catch (IOException e) {
					 MCFF.Logcat("E : Employee "+ this.name +" Account Cannot be Activated .");
					 System.out.print("\nEmployee \"+ this.name +\" Account Cannot be Activated .\n");
				}finally {
				 MCFF.SetFileReadonly(this.path);
				 MCFF.Logcat("I : Employee \"+ this.name +\" Account   Activated .");
				 flag = true;
				}
			 }
	 return flag;
	}

	public Boolean DactivatedE(Integer id) {
		Boolean flag = false;
		   if(FillById(id)) {
				 String content;
				 this.status = false;
		     	 content = this.id+"#"+this.name+"#"+this.username+"#"+this.password+"#"+this.manager+"#"+this.status;
				 MCFF.Backup(this.path);
				 MCFF.SetFileFree(this.path);
				 try {
					MCFF.ReplaceLine(this.path,this.id-2000,content);
				} catch (IOException e) {
					 MCFF.Logcat("E : Employee "+ this.name +" Account Cannot be Activated .");
					 System.out.print("\nEmployee \"+ this.name +\" Account Cannot be Activated .\n");
				}finally {
				 MCFF.SetFileReadonly(this.path);
				 MCFF.Logcat("I : Employee \"+ this.name +\" Account   Activated .");
				 flag = true;
				}
			 }
	 return flag;
	}
	
}
