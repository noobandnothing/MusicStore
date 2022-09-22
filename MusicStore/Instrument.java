package MusicStore;
import java.io.IOException;
import java.util.ArrayList;

import MyGlobalFunc.MCFF;

public class Instrument extends Item {
    public  ArrayList<Instrument_Part> parts ; 
    private ArrayList<Integer> parts_id = new ArrayList<Integer>();
	private final String path = "Instruments";
	// Constructors to fire Reading Methods From Files
	public Instrument() {
		parts = new ArrayList<Instrument_Part>(); 
	}
	

	// Constructor to Read object's members and fire Writing Methods To Files
	public Instrument(String name,Double price,ArrayList<Instrument_Part> parts,Boolean Writing_Switch){
		super(name,((price-price.intValue()) > 0.0)?price:price-0.001);
		this.id = create_id();
		this.parts = parts;
		parts_id_read();
		if(Writing_Switch) {
			push_obj(this.path);
		}
	}

	//#################

	public void  parts_id_read() {
		 this.parts_id.clear();
		 Integer part_id;
		 for(int counter = 0 ; counter < parts.size() ; counter++) {
			 part_id = parts.get(counter).id;
			 if(!parts_id.contains(part_id))
				 parts_id.add(part_id);
		 }
	 }
	 
	public void  parts_read() {
		 this.parts.clear();
			Integer p_id;
			 for(int counter = 0 ; counter < this.parts_id.size() ; counter++) {
				Instrument_Part obj = new Instrument_Part();
				p_id = parts_id.get(counter);
				obj.FillById(p_id);
				if(!parts.contains(obj))
					   this.parts.add(obj);		 	
			 }
	 }
			 
	public String getname() {
		return this.name;
	}
   
    public double getprice() {
		return this.price;
	}
	
    public int getid() {
		return this.id;
	}
	
	public Integer getnumObj() {
		return this.getNumObjs();
	}	

	//#################
	
	@Override
	public String toString() {
		String  content = "";
		content = "Instrument :  id=" + id + ", name=" + name + ", price=" + price + "\n";
		 for(int counter = 0 ; counter < parts.size() ; counter++)
			 content += "\n- " + parts.get(counter) ;
		 
		 return content.replace("[", "").replace("]","");
	}

	@Override
	protected void push_obj(String File_name) {
		String content;
		if(getNumObjs() == 0) {
     		content = this.id+"#"+this.name+"#"+this.price+"#@arrL"+parts_id;
     		MCFF.SetFileFree(File_name);
		    MCFF.Backup(this.path);
			MCFF.WriteFile(this.path,content, true);
			MCFF.SetFileReadonly(File_name);
		}else {
			Integer founded_id = SearchByName(this.name);
			if(founded_id == null){
				content = "\n"+this.id+"#"+this.name+"#"+this.price+"#@arrL"+parts_id;
				MCFF.SetFileFree(File_name);
			    MCFF.Backup(this.path);
				MCFF.WriteFile(this.path,content, true);
				MCFF.SetFileReadonly(File_name);
			}else {
				MCFF.Logcat("W : Instrument is already exist");
			}
		}
	}
	
	@Override
	protected  Integer create_id() {
		Integer GeneratedNum = 0;
		try {
			GeneratedNum =  (5000+1+getNumObjs());
		} catch (Exception e) {
			MCFF.Logcat("E : Instruments Id Creation.");
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
			MCFF.Logcat("E : Instruments Getting Number of Objects.");
		}
		return objs_num;
	}
	
	@Override
	public Boolean FillById(Integer id) {
		 Boolean flag = false;
		 ArrayList<ArrayList<Object>> Data = MCFF.get_FileContent(this.path);
		 if(id >= Integer.valueOf(Data.get(0).get(0).toString()) && id <= Integer.valueOf(Data.get(Data.size()-1).get(0).toString())) {
			   this.id = id;
			   this.name = String.valueOf(Data.get(id-5001).get(1).toString());
			   Double tmp_price = Double.valueOf(Data.get(id-5001).get(2).toString());
			   if((tmp_price-tmp_price.intValue()) > 0.99){
				   this.price= Double.valueOf(tmp_price+0.001);
			   } else {
				   this.price = tmp_price;
			   }
			   String tmp  =  String.valueOf(Data.get(id-5001).get(3));
			   convert_stringOBJ_to_IntegerArrayList(this.parts_id,tmp);
			   parts_read();
			   flag = true;
		 }
		 return flag;
	}
	
	protected static void  convert_stringOBJ_to_IntegerArrayList(ArrayList<Integer> IList,String tmp) {
		String arr_id[] = tmp.replace("@arrL", "").replace("[","").replace("]", "").replace(" ", "").split(",",0);
		IList.clear();	
		for(String num : arr_id) {
			if(!num.equals(""))
				IList.add(Integer.valueOf(num));
		   }
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

	@Override
	public  Boolean RenameById(Integer id,String NewName) {
		 Boolean flag = false;
		 ArrayList<ArrayList<Object>> Data = MCFF.get_FileContent(this.path);
		 if(this.id == null) {
			 if(FillById(id)) {
				   Data.get(id-5001).set(1, NewName);	
				   MCFF.Backup(this.path);
				   MCFF.Write_FileContent(this.path,Data);
				   flag = true;
		     }
		 }
		 return flag;
	}
	
	@Override
	public  Boolean ChangePriceById(Integer id,Double NewPrice) {
		 Boolean flag = false;
		 ArrayList<ArrayList<Object>> Data = MCFF.get_FileContent(this.path);
			 if(FillById(id)) {
				   Data.get(id-5001).set(2, ((NewPrice-NewPrice.intValue()) > 0.0)?NewPrice:NewPrice-0.001);	
				   MCFF.Backup(this.path);
				   MCFF.Write_FileContent(this.path,Data);
				   flag = true;
		     }
		 return flag;
	}
	
	public  Boolean addInstrument_part(Integer id,Instrument_Part obj) {
		Boolean flag = false;
		   if(FillById(id)) {
			   if(!this.parts_id.contains(obj.id)) {
				 this.parts.add(obj);
				 this.parts_id_read();
				 String content;
			     content = this.id+"#"+this.name+"#"+this.price+"#@arrL"+this.parts_id;
				 MCFF.Backup(this.path);
				 MCFF.SetFileFree(this.path);
				 try {
					MCFF.ReplaceLine(this.path,this.id-5000,content);
				} catch (IOException e) {
					 MCFF.Logcat("E : addInstrument_part can not add Part .");
					 System.out.print("\naddInstrument_part can not add part\n");
				}finally {
				 MCFF.SetFileReadonly(this.path);
				 MCFF.Logcat("I : Part was added to Instrument .");
				 flag = true;
				}
			 }
	     }
	 return flag;
	}	
	
	public  Boolean removeInstrument_part(Integer id,Instrument_Part obj) {
		Boolean flag = false;
		   if(FillById(id)) {
			   if(this.parts_id.contains(obj.id)) {
				 this.parts_id.remove(obj.id);
				 this.parts_read();
				 String content;
			     content = this.id+"#"+this.name+"#"+this.price+"#@arrL"+this.parts_id;
				 MCFF.Backup(this.path);
				 MCFF.SetFileFree(this.path);
				 try {
					MCFF.ReplaceLine(this.path,this.id-5000,content);
				} catch (IOException e) {
					 MCFF.Logcat("E : addInstrument_part can not remove Part .");
					 System.out.print("\naddInstrument_part can not add part\n");
				}finally {
				 MCFF.SetFileReadonly(this.path);
				 MCFF.Logcat("I : Part was removed from Instrument .");
				 flag = true;
				}
			 }
	     }
	 return flag;
   }
		
		
}