package MusicStore;

public abstract class Person {
	protected Integer id;
	protected String name;

	Person(){
		
	}
	Person(String name){
		this.name = name;
	}

	protected abstract void push_obj(String File_name);
	protected abstract Integer create_id();
	protected abstract Integer getNumObjs();
	protected abstract Integer SearchByName(String element);
	public abstract Boolean FillById(Integer id);
	public abstract Boolean FillByName(String element);
	public abstract Boolean RenameById(Integer id,String NewName);
	
}
