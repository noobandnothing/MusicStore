package MusicStore;
import java.util.ArrayList;
import java.util.Date;

import MyGlobalFunc.MCFF;

public class transaction  {
	private final String path = "transaction";
	private Integer id;
	private Customer customer ;
	// Make place for them to avoid null (does not point to place) when using functions with empty constractor
	private ArrayList<Instrument> instrusmets ; 
    private ArrayList<Integer> instrusmets_id = new ArrayList<Integer>();  
    private ArrayList<Integer> instrusmets_quantity = new ArrayList<Integer>();
    private ArrayList<Boolean> insurance_instruments ;
    private ArrayList<Instrument_Part> parts ;
    private ArrayList<Integer> parts_id = new ArrayList<Integer>();
    private ArrayList<Integer> parts_quantity = new ArrayList<Integer>();
    private ArrayList<Boolean> insurance_parts ;
    private Boolean Discount_applied ;
    private Date date ;
    
	// Constructors to fire Reading Methods From Files
	public transaction() {
		customer  = new Customer() ;
        instrusmets = new ArrayList<Instrument>();
        insurance_instruments  = new ArrayList<Boolean>();
        parts = new ArrayList<Instrument_Part>(); 
        insurance_parts  = new ArrayList<Boolean>();
        date = new Date();
	}
	

	// Constructor to Read object's members and fire Writing Methods To Files
	public transaction(Customer customer,Boolean Discount_applied,ArrayList<Instrument> instrusmets,ArrayList<Integer> instrusmets_quantity,ArrayList<Boolean> insurance_instruments,ArrayList<Instrument_Part> parts,ArrayList<Integer> parts_quantity,ArrayList<Boolean> insurance_parts,Boolean Writing_Switch){
		this.id = create_id();
		this.customer = customer;
		this.Discount_applied = Discount_applied;
		this.instrusmets = instrusmets;
		this.instrusmets_quantity = instrusmets_quantity;
		this.insurance_instruments = insurance_instruments;
		this.parts = parts;
		this.parts_quantity = parts_quantity;
		this.insurance_parts = insurance_parts;
		instruments_id_read();
		parts_id_read();
		this.date = new Date();
		if(Writing_Switch) {
			push_obj(this.path);
		}
	}

	//#################
	
	public void  instruments_id_read() {
		this.instrusmets_id.clear();
		 Integer instrument_id;
		 for(int counter = 0 ; counter < instrusmets.size() ; counter++) {
			 instrument_id = instrusmets.get(counter).id;
			 if(!instrusmets_id.contains(instrument_id))
				 instrusmets_id.add(instrument_id);
		 }
	 }
	 	 
	public void  parts_id_read() {
		 this.parts_id.clear();
		 Integer part_id;
		 for(int counter = 0 ; counter < parts.size() ; counter++) {
			 part_id = parts.get(counter).id;
			 if(!parts_id.contains(part_id))
				 parts_id.add(part_id);
		 }
	 }

	//#################
	
	public Integer getId() {
		return id;
	}
	public Customer getCustomer() {
		return customer;
	}
	public ArrayList<Instrument> getInstrusmets() {
		return instrusmets;
	}
	public ArrayList<Instrument_Part> getParts() {
		return parts;
	} 
    public ArrayList<Boolean> getInsurance_instruments() {
		return insurance_instruments;
	}
	public ArrayList<Boolean> getInsurance_parts() {
		return insurance_parts;
	}
	public Boolean getDiscount_applied() {
		return Discount_applied;
	}   
	
	public Integer num_of_transaction() {
		return getNumObjs(); 
	}

	//#################


	public void  instruments_read() {
		 this.instrusmets.clear();
	   	 Integer i_id;
		 for(int counter = 0 ; counter < this.instrusmets_id.size() ; counter++) {
			Instrument obj = new Instrument();
			i_id = instrusmets_id.get(counter);
			obj.FillById(i_id);
			if(!this.instrusmets.contains(obj))
				this.instrusmets.add(obj);	 	
		 }
	 }
	
	public void  parts_read() {
		 this.parts.clear();
		 Integer p_id;
		 for(int counter = 0 ; counter < this.parts_id.size() ; counter++) {
			Instrument_Part obj = new Instrument_Part();
			p_id = parts_id.get(counter);
			obj.FillById(p_id);
			if(!this.parts.contains(obj))
				   this.parts.add(obj);		 	
		 }
	 }
	
	//#################

	protected void push_obj(String File_name) {
		String content;
		if(getNumObjs() == 0) {
     		content = this.id+"#"+this.customer.id+"#"+this.Discount_applied+"#@arrL"+this.instrusmets_id+"#@arrL"+this.instrusmets_quantity+"#@arrL"+this.insurance_instruments+"#@arrL"+this.parts_id+"#@arrL"+this.parts_quantity+"#@arrL"+this.insurance_parts+"#@Date:"+MCFF.Convert_DatetoFDString(this.date,2);
     		MCFF.SetFileFree(File_name);
     		MCFF.Backup(this.path);
			MCFF.WriteFile(this.path,content, true);
			MCFF.SetFileReadonly(File_name);
		}else {
				content = "\n"+this.id+"#"+this.customer.id+"#"+this.Discount_applied+"#@arrL"+this.instrusmets_id+"#@arrL"+this.instrusmets_quantity+"#@arrL"+this.insurance_instruments+"#@arrL"+this.parts_id+"#@arrL"+this.parts_quantity+"#@arrL"+this.insurance_parts+"#@Date:"+MCFF.Convert_DatetoFDString(this.date,2);
				MCFF.SetFileFree(File_name);
	     		MCFF.Backup(this.path);
				MCFF.WriteFile(this.path,content, true);
				MCFF.SetFileReadonly(File_name);
		}
	}
	
	protected  Integer create_id() {
		Integer GeneratedNum = 0;
		try {
			GeneratedNum =  (10000+1+getNumObjs());
		} catch (Exception e) {
			MCFF.Logcat("E : Instruments Id Creation.");
		}
		return GeneratedNum;
	}

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
	
	public Boolean FillById(Integer id) {
		 Boolean flag = false;
		 ArrayList<ArrayList<Object>> Data = MCFF.get_FileContent(this.path);
		 if(id >= Integer.valueOf(Data.get(0).get(0).toString())  && id <= Integer.valueOf(Data.get(Data.size()-1).get(0).toString())) {
			   this.id = id;
			   this.customer.FillById(Integer.valueOf(Data.get(id-10001).get(1).toString()));
			   this.Discount_applied = Boolean.valueOf((Data.get(id-10001).get(2).toString()));
			   //##
			   String tmp  =  Data.get(id-10001).get(3).toString();
			   convert_stringOBJ_to_IntegerArrayList(this.instrusmets_id,tmp);
			   tmp  =   Data.get(id-10001).get(4).toString();
			   convert_stringOBJ_to_IntegerArrayList(this.instrusmets_quantity,tmp);
			   tmp  =   Data.get(id-10001).get(5).toString();
			   convert_stringOBJ_to_BooleanArrayList(this.insurance_instruments,tmp);
			   //##
			   tmp  =   Data.get(id-10001).get(6).toString();
			   convert_stringOBJ_to_IntegerArrayList( this.parts_id,tmp);
			   tmp  =   Data.get(id-10001).get(7).toString();
			   convert_stringOBJ_to_IntegerArrayList(this.parts_quantity,tmp);
			   tmp  =   Data.get(id-10001).get(8).toString();
			   convert_stringOBJ_to_BooleanArrayList(this.insurance_parts,tmp);
			   //##
		       tmp  =   Data.get(id-10001).get(9).toString();
		       if(tmp != "") {
		    	   tmp=tmp.replace("@Date:", "");
			       this.date = MCFF.Convert_FDStringtoDate(tmp, 2);
		       }
		       instruments_read();
		       parts_read();
			   flag = true;
		 }
		 return flag;
	}

	protected void  convert_stringOBJ_to_IntegerArrayList(ArrayList<Integer> stored,String tmp) {
		ArrayList<Integer> IList = new ArrayList<Integer>();
		String arr_id[] = tmp.replace("@arrL", "").replace("[","").replace("]", "").replace(" ", "").split(",",0);
		IList.clear();	
		for(String num : arr_id) {
			if(num.equals("") == false)
			 IList.add(Integer.valueOf(num));
		   }
		MCFF.backup_LIN(IList,stored);
	}
	
    protected void  convert_stringOBJ_to_BooleanArrayList(ArrayList<Boolean> stored,String tmp) {
		ArrayList<Boolean> IList = new ArrayList<Boolean>();
		String arr_id[] = tmp.replace("@arrL", "").replace("[","").replace("]", "").replace(" ", "").split(",",0);
		IList.clear();	
		for(String obj : arr_id) {
			if(obj.equals("") == false)
			 IList.add(Boolean.valueOf(obj));
		   }
		MCFF.backup_LBN(IList,stored);
	}	
	
	
	@Override
	public String toString() {
		String content =  "Order :"+ id + ", Customer : "+customer.getName()+" ,Date :"+date +"\n\n" ;
		content+="||Item                                                     ||Quantity||Insurance   \n";
		for(int counter = 0 ; counter < instrusmets.size() ; counter++) {
			content+= "||"+instrusmets.get(counter).getname()+MCFF.print_spaces(56-instrusmets.get(counter).getname().length())+" ||"+ instrusmets_quantity.get(counter) + MCFF.print_spaces(7-instrusmets_quantity.get(counter).toString().length()) + " ||"+ insurance_instruments.get(counter) +"\n";			
		}
		for(int counter = 0 ; counter < parts.size() ; counter++) {
			content+= "||"+parts.get(counter).getname()+MCFF.print_spaces(56-parts.get(counter).getname().length())+" ||"+ parts_quantity.get(counter)+ MCFF.print_spaces(7-parts_quantity.get(counter).toString().length()) + " ||"+ insurance_parts.get(counter) +"\n";			
		}		
	    content+="Total Price :    "+total_price();
		return content;
	}

	public String toString2() {
		return "Order :"+ id + ", Customer : "+customer.getName()+",Total Price :"+this.total_price()+" ,Date :"+date +"\n" ;
	}

	public Double total_price() {
		Double Total = total_price_inst()+total_price_instp();
		return (Total)-(MCFF.check_boolean(this.Discount_applied)*Total*this.customer.getDiscount()/100);
	}
	
	public Double total_price_inst() {
		Double Total_price = 0.0;
		for(int counter  = 0 ; counter < this.instrusmets.size() ; counter++) {
			Total_price += ((this.instrusmets.get(counter).getprice()*this.instrusmets_quantity.get(counter))*(1+(0.1*MCFF.check_boolean(this.insurance_instruments.get(counter)))));
		}
		return Total_price;
	}
	
	public Double total_price_instp() {
		Double Total_price = 0.0;
		for(int counter  = 0 ; counter < this.parts.size() ; counter++) {
			Total_price += ((this.parts.get(counter).getprice()*this.parts_quantity.get(counter))*(1+(0.1*MCFF.check_boolean(this.insurance_parts.get(counter)))));
		}
		return Total_price;
	}
	
}