package MusicStore;

import java.io.IOException;
import java.util.ArrayList;
import MyGlobalFunc.MCFF;

public class Customer extends Person{
	private final String path = "Customer";
	private Double discount =  0.0;
	
    public Customer(){
		
	}
	
	public Customer(String Name,Double discount,Boolean Writing_Switch){
		super(Name);
		this.id = create_id();
		this.discount = ((discount-discount.intValue()) > 0.0)?discount:discount-0.001;
		if(Writing_Switch) {
			push_obj(this.path);
		}
	}
	
	// For Faster new customer
	public Customer(String Name,Boolean Writing_Switch){
		super(Name);
		this.id = create_id();
		if(Writing_Switch) {
			push_obj(this.path);
		}
	 }
	
// ###############################################################

	public Integer getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Double getDiscount() {
		return discount;
	}
	
	public Boolean setDiscount(Double discount) {
		Boolean flag = false;
		   if(FillById(this.id) != null) {
				 String content;
				 this.discount = discount;
				 content = this.id+"#"+this.name+"#"+this.discount;
				 MCFF.Backup(this.path);
				 MCFF.SetFileFree(this.path);
				 try {
					MCFF.ReplaceLine(this.path,this.id-9000,content);
				} catch (IOException e) {
					 MCFF.Logcat("E : Customer "+ this.name +" Discount Cannot be Changed .");
					 System.out.print("\nCustomer \"+ this.name +\" Discount Cannot be Changed .\n");
				}finally {
				 MCFF.SetFileReadonly(this.path);
				 MCFF.Logcat("I : Customer \"+ this.name +\" Discount was Added .");
				 flag = true;
				}
			 }
	 return flag;
	}
			
// ###############################################################

	@Override
	protected  Integer create_id() {
		Integer GeneratedNum = 0;
		try {
			GeneratedNum =  (9000+1+getNumObjs());
		} catch (Exception e) {
			MCFF.Logcat("E : Customer Id Creation.");
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
			MCFF.Logcat("E : Customer Getting Number of Objects.");
		}
		return objs_num;
	}
	
	@Override
	protected void push_obj(String File_name) {
		/*
		ADD CHECKER IF EXIST
		*/
		String content;
		if(getNumObjs() == 0) {
     		content = this.id+"#"+this.name+"#"+this.discount;
     		MCFF.SetFileFree(File_name);
			MCFF.WriteFile(this.path,content, true);
			MCFF.SetFileReadonly(File_name);
		}else {
			Integer founded_id = SearchByName(this.name);
			if(founded_id == null){
				content = "\n"+this.id+"#"+this.name+"#"+this.discount;
				MCFF.SetFileFree(File_name);
				MCFF.Backup(this.path);
				MCFF.WriteFile(this.path,content, true);
				MCFF.SetFileReadonly(File_name);
			}else {
				MCFF.Logcat("W : Customer is already exist");
			}
		}
	}
	
	@Override
	public Boolean FillById(Integer id) {
		 Boolean flag = false;
		 ArrayList<ArrayList<Object>> Data = MCFF.get_FileContent(this.path);
		 if(id >= Integer.valueOf(Data.get(0).get(0).toString())  && id <= Integer.valueOf(Data.get(Data.size()-1).get(0).toString())) {
			   this.id = id;
			   this.name = Data.get(id-9001).get(1).toString();
			   double tmp= Double.valueOf(Data.get(id-9001).get(2).toString());
			   if((tmp-(int)tmp) > 0.99){
				   this.discount= tmp+0.001;
			   } else {
				   this.discount = tmp;
			   }
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
		 MCFF.RemoveEmpty(this.path);
		 ArrayList<ArrayList<Object>> Data = MCFF.get_FileContent(this.path) ;
		  for(int counter =0 ; counter < Data.size() ; counter++) {
			  if(element.equals(Data.get(counter).get(1).toString())) {
				   return (Integer.valueOf(Data.get(counter).get(0).toString()));
			 }
		  }
		  return null;
	}
	
	@Override
	public  Boolean RenameById(Integer id,String NewName) {
		 Boolean flag = false;
		 ArrayList<ArrayList<Object>> Data = MCFF.get_FileContent(this.path);
			 if(FillById(id)) {
				   Data.get(id-9001).set(1, NewName);	
				   MCFF.Backup(this.path);
				   MCFF.Write_FileContent(this.path,Data);
				   MCFF.Logcat("E : Customer " +id+" Name was changed.");
				   flag = true;
		     }
		 return flag;
	}
}
