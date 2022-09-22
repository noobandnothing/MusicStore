package MusicStore;

public abstract class Item {
	protected Integer id;
	protected String name;
	protected Double price;

	Item(){
		
	}
	Item(Integer id,String name,Double price){
		this.id = id;
		this.name = name;
		this.price = price;
	}
	Item(String name,Double price){
		this.name = name;
		this.price = price;
	}
	protected abstract void push_obj(String File_name);
	protected abstract Integer create_id();
	protected abstract Integer getNumObjs();
	protected abstract Integer SearchByName(String element);
	public abstract Boolean FillById(Integer id);
	public abstract Boolean FillByName(String element);
	public abstract Boolean RenameById(Integer id,String NewName);
	public abstract Boolean ChangePriceById(Integer id,Double NewPrice) ;
	

}
