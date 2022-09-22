package MusicStore;
import java.util.ArrayList;
import MyGlobalFunc.MCFF;

public class Instrument_Part extends Item {
		private final String path = "InstrumentP";
		
		// Constructors to fire Reading Methods From Files
		public Instrument_Part() {
			
		}
		
		// Constructor to Read object's members and fire Writing Methods To Files
		 public Instrument_Part(String name,Double price,Boolean Writing_Switch){
			super(name,((price-price.intValue()) > 0.0)?price:price-0.001);
			this.id = create_id();
			if(Writing_Switch) {
				push_obj(this.path);
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
		
		@Override
		public String toString() {
			return "Instrument_Part : id=" + id + ", name=" + name + ", price=" + price ;
		}
		
		@Override
		protected void push_obj(String File_name) {
			String content;
			if(getNumObjs() == 0) {
	     		content = this.id+"#"+this.name+"#"+this.price;
	     		MCFF.SetFileFree(File_name);
				MCFF.WriteFile(this.path,content, true);
				MCFF.SetFileReadonly(File_name);
			}else {
				Integer founded_id = SearchByName(this.name);
				if(founded_id == null){
					content = "\n"+this.id+"#"+this.name+"#"+this.price;
					MCFF.SetFileFree(File_name);
					MCFF.WriteFile(this.path,content, true);
					MCFF.SetFileReadonly(File_name);
				}else {
					System.out.println("\nInstrument_Part is already exist");
					MCFF.Logcat("W : Instrument_Part is already exist");
				}
			}
		}
		
		@Override
		protected  Integer create_id() {
			Integer GeneratedNum = 0;
			try {
				GeneratedNum =  (7000+1+getNumObjs());
			} catch (Exception e) {
				MCFF.Logcat("E : Inst_Part Id Creation.");
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
				MCFF.Logcat("E : Inst_Part Getting Number of Objects.");
			}
			return objs_num;
		}
		
		@Override
		public Boolean FillById(Integer id) {
			 Boolean flag = false;
			 ArrayList<ArrayList<Object>> Data = MCFF.get_FileContent(this.path);
			 if(id >= Integer.valueOf(Data.get(0).get(0).toString())  && id <= Integer.valueOf(Data.get(Data.size()-1).get(0).toString())) {
				   this.id = id;
				   this.name = String.valueOf(Data.get(id-7001).get(1));
				   double tmp_price= Double.valueOf(Data.get(id-7001).get(2).toString());
				   if((tmp_price-(int)tmp_price) > 0.99){
					   this.price= tmp_price+0.001;
				   } else {
					   this.price = tmp_price;
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
				 if(FillById(id)) {
					   Data.get(id-7001).set(1, NewName);	
					   MCFF.Backup(this.path);
					   MCFF.Write_FileContent(this.path,Data);
					   flag = true;
			     }
			 return flag;
		}
		
		@Override
		public  Boolean ChangePriceById(Integer id,Double NewPrice) {
			 Boolean flag = false;
			 ArrayList<ArrayList<Object>> Data = MCFF.get_FileContent(this.path);
				 if(FillById(id)) {
					   Data.get(id-7001).set(2, ((NewPrice-NewPrice.intValue()) > 0.0)?NewPrice:NewPrice-0.001);	
					   MCFF.Backup(this.path);
					   MCFF.Write_FileContent(this.path,Data);
					   flag = true;
			     }
			 return flag;
		}
		
}
