package MyGlobalFunc;

import MusicStore.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class MCF {
	
	static Scanner  input = new Scanner(System.in);
	
	//////////////////////##########(Help Functions)############///////////////////////////////////
	public static void logo()
	{
	System.out.println(" +++++++++++++++++++++++++++++") ;
	System.out.println("||  ______________   _____   ||");
	System.out.println("|| |   __    __   | |  ___|  ||") ;
	System.out.println("|| |  |  |  |  |  | |__\\__   ||") ;
	System.out.println("|| |  |  |  |  |  |  ___| |  ||") ;
	System.out.println("|| |__|  |__|  |__| |_____|  ||") ;
	System.out.println(" +++++++++++++++++++++++++++++") ;
	System.out.println("") ;
	System.out.println("      BY  Mohamed Omar    ") ;
	}
	
	public static void main_nav()
	{
	 System.out.println("") ;
	 System.out.println("NAV : (l) : login , (e) : exit \n");
	 System.out.println("") ;
	}
	
	public static Boolean onlyalpha(String word) {
		Boolean test_result = true;
		for(int counter = 0 ; counter < word.length() ; counter++) {
			if(!(word.charAt(counter) == ' ' || (word.charAt(counter) >= 'a' && word.charAt(counter) <= 'z') ||  (word.charAt(counter) >= 'A' && word.charAt(counter) <= 'Z'))) {
				test_result = false;
				break;
			}
		}
		return test_result; 
	}
	
	public static Boolean atleastonealpha(String word) {
		Boolean test_result = false;
		for(int counter = 0 ; counter < word.length() ; counter++) {
			if(((word.charAt(counter) >= 'a' && word.charAt(counter) <= 'z') ||  (word.charAt(counter) >= 'A' && word.charAt(counter) <= 'Z'))) {
				test_result = true;
				break;
			}
		}
		return test_result; 
	}
	
	public static Boolean withoutsymbols(String word) {
		Boolean test_result = false;
		for(int counter = 0 ; counter < word.length() ; counter++) {
			if(((word.charAt(counter) >= 'a' && word.charAt(counter) <= 'z') || (word.charAt(counter) >= '0' && word.charAt(counter) <= '9') ||  (word.charAt(counter) >= 'A' && word.charAt(counter) <= 'Z'))) {
				test_result = true;
				break;
			}
		}
		return test_result; 
	}
	
    public static void Create_user(Boolean manager) {
		@SuppressWarnings("unused")
		Employee x = new Employee();
		String username;
		String password;
		Boolean flag =false;
		while(true){
			System.out.print("\n\n- Enter Name : ");
			String name = input.nextLine();
			if(!onlyalpha(name) || name.contains("\n")) {
				System.out.println("Invalid Name\n");
				continue;
			}else {
			while(true) {
				System.out.print("\n- Enter UserName : ");
				username = input.nextLine();
				System.out.print("\n- Enter Password : ");
				password = input.nextLine();
				if(username.contains("@") || password.contains("@") || username.contains("#") || password.contains("#") || password.contains("\n")) {
				    System.out.println("\nDetect @ or # which are invalid");
				}else {
					if(atleastonealpha(username) && atleastonealpha(password)) {
						x = new Employee(name,username,password,manager,true);		
						flag = true;
						admin_login();
						break;
					}else {
						System.out.println("\nUsername & Password should have at least 1  character");
						continue;
					}
				
	            }
			}
		  }
			if(flag.equals(true))
				break;
	   }
    }

	//////////////////////##########Admin Part0 (After Point)############///////////////////////////////////
	
	public static void First_point()
	{
		String nav_value = input.nextLine() ;
		System.out.println("");
		if(nav_value.equals("l")) {
			Second_point();
		}else if(nav_value.equals("e")) {
			System.exit(0);
		}else {
			System.out.println("\nU R JOKING WITH ME , I am out . \n") ;
            System.exit(1);
	    }
	}
			
	public static void Second_point() {
		Employee x = new Employee();
		if(x.num_of_employee() == 0)
		{
			System.out.println("\nOK Time to Create admin user");
			Create_user(true);
			System.out.print("\nNow , I will exit and login using username and password in next time");
		}else {
			String username;
			String password;
			System.out.print("UserName : ");
			username = input.nextLine();	
			if(!x.CheckUserName(username)) {
				System.out.println("\nInvalid Username\n");
				MCFF.Logcat("W : W12 Unknown  is trying to login");
				System.exit(12);
			}else {
				System.out.print("\nPassword : ");
				password = input.nextLine();
				if(!x.CheckPassword(password)) {
					System.out.println("\nInvalid Password\n");
					MCFF.Logcat("W : W13 Password of "+username+" is wrong !");
					System.exit(13);
				}else {
					MCFF.Logcat("I : Second part "+x.getId()+"$"+x.getName()+" signed in");
					System.out.println("\nHello "+x.getName()+"\n");
					try {
						TimeUnit.SECONDS.sleep(1);		
						if(x.CheckManger()) {
							admin_login();
						}else{
							if(x.CheckStatus()) {
								user_login();
							}
							else {
								System.out.println("\nSorry Your account was deactivated !\n");
							}		
						}
					} catch (InterruptedException e) {
						
					}
				}	
			}
					
		}
	}

	//////////////////////########## Part0 (After Login)############///////////////////////////////////
	
	public static void admin_login() {
		String select = "" ;
		while(true) {
			System.out.print("\nWelcome to Administrator Part : \n");
			System.out.println("\n1 - Item \n2 - Employee \n3 - Report \n4 - Change Customer Discount \n5 - Exit \n");
			System.out.print("Choice : ");
			select =input.nextLine();
			if(select.equals("1")) {
				admin_p1();
			}else if(select.equals("2")) {
				admin_p2();
			}else if(select.equals("3")) {
				admin_p3();
			}else if(select.equals("4")){
				admin_p4();
			}else if(select.equals("5")){
				System.exit(0);
			}else {
				System.out.println("\nInvalid Input");
			}
		}
		
	}
	
	public static void user_login() {
		String select = "" ;
		System.out.println("\nWelcome to user Part");
        System.out.println("\n1 - Create Order \n2 - Search for Item Details \n3 - Exit \n");
		while(true) {
			System.out.print("Choice : ");
			select =input.nextLine();
			if(select.equals("1")) {
				MCF_Trans.user_p1();
				break ;
			}else if(select.equals("2")) {
				user_p2();
				break ;
			}else if(select.equals("3")){
				System.exit(0);
			}else {
				System.out.println("\nInvalid Input");
			}
		}
	}
    
	///////////////////////////////////############## GLOBAL TOOLS ################///////////////////////////////////
	
    public static void print_all_Parts() {
    	Instrument_Part part = new Instrument_Part();
		Integer number_parts = part.getnumObj();
		System.out.println("");
		for(int count = 0 ; count < number_parts ; count++) {
			part = new Instrument_Part();
			part.FillById(7000+1+count);
			System.out.print((count+1) + " - " + part.getname() +"\n");
		}
		
    }

    public static void print_all_Partsofintruments(Instrument instrument) {
        ArrayList<Instrument_Part> parts = instrument.parts; 
		Integer number_parts = parts.size();
		System.out.println("");
		for(int count = 0 ; count < number_parts ; count++) {
			System.out.print((count+1) + " - " + parts.get(count).getname() +"\n");
		}
		
    }
    
    public static void print_all_Instruments() {
    	Instrument instrument = new Instrument();
		Integer number_Instruments = instrument.getnumObj();
		System.out.println("");
		for(int count = 0 ; count < number_Instruments ; count++) {
			instrument = new Instrument();
			instrument.FillById(5000+1+count);
			System.out.print((count+1) + " - " + instrument.getname() +"\n");
		}
		
    }
    
    ///////////////////////////////////##########################################///////////////////////////////////

	
	//////////////////////##########Admin Part1 (Items)############///////////////////////////////////
	
	public static void admin_p1() {
		String select = "" ;
		System.out.println("\n\n1 - Instrument \n2 - Instrument Part \n3 - Connect Part to Intrument\n4 - Disconnect Part from Instrument \n5 - Back \n6 - Exit \n");
		while(true) {
			System.out.print("Choice : ");
			select =input.nextLine();
			if(select.equals("1")) {
				admin_p1_I();
				admin_p1();
				break ;
			}else if(select.equals("2")) {
				admin_p1_IP();
				admin_p1();
				break ;
			}else if(select.equals("3")) {
				admin_CPI();
				admin_p1();
				break ;
			}else if(select.equals("4")) {
				admin_DPI();
				admin_p1();
				break ;
			}else if(select.equals("5")){
				admin_login();
			}else if(select.equals("6")){
				System.exit(0);
			}else {
				System.out.println("\nInvalid Input");
			}
		}
	}
	
	public static void admin_p1_I(){
		Double price;
		Boolean flag =false;
		while(true){
			System.out.print("\n\n- Enter Name : ");
			String name = input.nextLine();
			if(!withoutsymbols(name) || name.contains("\n")) {
				System.out.println("Invalid Name");
				continue;
			}else {
			while(true) {
				while(true){
					System.out.print("\n- Enter Price : ");
					if(input.hasNextDouble()){
						price = input.nextDouble();
						break;
					}else{
					      System.out.println("Wrong value entered");
					      input.nextLine();				    
					}
				}
				System.out.print("");
			    ArrayList<Instrument_Part> parts = new ArrayList<Instrument_Part>();
				while(true) {
				    String select = "" ;
					System.out.println("\n\n1 - Add Part \n2 - Add Existing Part \n3 - Later or Enough\n");
					if(input.hasNextLine())
			    		input.nextLine();
					select = input.nextLine();
					System.out.print("Choice : ");
					if(select.equals("1")) {
						System.out.print("\n\nPart "+(parts.size()+1)+" :\n");
						Instrument_Part part = new Instrument_Part();
						part.FillById(admin_p1_IP_forI());
						parts.add(part);
					}else if(select.equals("2")) {
						Instrument_Part part = new Instrument_Part();
						print_all_Parts();
						int choice = 0;
					/* use number_parts to reduce resources*/
					while(true){
						System.out.print("\n Choice : ");
						if(input.hasNextInt()){
							choice = input.nextInt();
							if(choice > 0 && choice <=part.getnumObj())
							   break;
						}else{
						      System.out.println("Wrong value entered");
						      if(input.hasNextLine())
						    		input.nextLine();		    
						}
				    } 
						part.FillById(7000+choice);
						parts.add(part);
					}else if(select.equals("3")) {
						break;
					}else if(select.equals("b")){
						admin_login();
					}else if(select.equals("e")){
						System.exit(0);
					}
				}

			@SuppressWarnings("unused")
			Instrument x = new Instrument(name,price,parts,true);
				flag = true;
				admin_p1();
				break;
			}
	       }
			if(flag.equals(true))
				break;
	  }
	}
	
	public static Integer admin_p1_IP_forI(){
		String name;
		Double price;
		while(true){
			System.out.print("\n\n- Enter Name : ");
			name = input.nextLine();
			if(!withoutsymbols(name) || name.contains("\n")) {
				System.out.println("Invalid Name");
				continue;
			}else {
			while(true) {
				while(true){
					System.out.print("\n- Enter Price : ");
					if(input.hasNextDouble()){
						price = input.nextDouble();
						break;
					}else{
					      System.out.println("Wrong value entered");
				    }
				}			
			Instrument_Part x = new Instrument_Part(name,price,true);
				return x.getid();
			}
	       }

	  }
	}
		
	public static void admin_p1_IP(){
		String name;
		Double price;
		Boolean flag =false;
		while(true){
			System.out.print("\n\n- Enter Name : ");
			name = input.nextLine();
			if(!withoutsymbols(name) || name.contains("\n")) {
				System.out.println("Invalid Name");
				continue;
			}else {
			while(true) {
				while(true){
					System.out.print("\n- Enter Price : ");
					if(input.hasNextDouble()){
						price = input.nextDouble();
						break;
					}else{
					      System.out.println("Wrong value entered");
				    }
				}			
		    @SuppressWarnings("unused")
			Instrument_Part x = new Instrument_Part(name,price,true);
				flag = true;
				admin_p1();
				break;
			}
	       }
			if(flag.equals(true))
				break;
	  }
	}

    public static void admin_CPI(){
    	Instrument instrument = new Instrument();
    	Instrument_Part part = new Instrument_Part();
		int choice = 0;
		System.out.print("\nInstrument Fetching . . .\n");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
		}		
    	print_all_Instruments();
    	while(true){
     		System.out.print("\n Choice : ");
     		if(input.hasNextInt()){
    			choice = input.nextInt();
    			if(choice > 0 && choice <= instrument.getnumObj())
    			   break;
     		}else{
    		    System.out.println("Wrong value entered");
    		    if(input.hasNextLine())
    	    		input.nextLine();			    
    		}
        } 
    	
    	instrument.FillById(5000+choice);
    	
		System.out.print("\nInstrument Parts Fetching . . .\n");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
    	print_all_Parts();
     	while(true){
     		System.out.print("\n Choice : ");
     		if(input.hasNextInt()){
    			choice = input.nextInt();
    			if(choice > 0 && choice <= part.getnumObj())
    			   break;
     		}else{
    		    System.out.println("Wrong value entered");
    		    if(input.hasNextLine())
    	    		input.nextLine();			    
    		}
        } 
		part.FillById(7000+choice);
		instrument.addInstrument_part(instrument.getid(), part);
		admin_login();
	}

    public static void admin_DPI(){
    	Instrument instrument = new Instrument();
    	Instrument_Part part = new Instrument_Part();
		int choice = 0;
		System.out.print("\nInstrument Fetching . . .\n");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {

		}		
    	print_all_Instruments();
    	while(true){
     		System.out.print("\n Choice : ");
     		if(input.hasNextInt()){
    			choice = input.nextInt();
    			if(choice > 0 && choice <= instrument.getnumObj())
    			   break;
     		}else{
    		    System.out.println("Wrong value entered");
    		    if(input.hasNextLine())
    	    		input.nextLine();			    
    		}
        } 
    	
    	instrument.FillById(5000+choice);
    	
		System.out.print("\nInstrument Parts Fetching . . .\n");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	

		print_all_Partsofintruments(instrument);

		while(true){
     		System.out.print("\n Choice : ");
     		if(input.hasNextInt()){
    			choice = input.nextInt();
    			if(choice > 0 && choice <= instrument.parts.size())
    			   break;
     		}else{
    		    System.out.println("Wrong value entered");
    		    if(input.hasNextLine())
    	    		input.nextLine();			    
    		}
        } 
     	part.FillById(instrument.parts.get(choice-1).getid());
		instrument.removeInstrument_part(instrument.getid(), part);
		
		admin_login();
	}
    
    
    //////////////////////##########Admin Part2 (Employee)############///////////////////////////////////
    
	public static void admin_p2() {
		String select = "" ;
		System.out.println("\n\n1 - Add Employee \n2 - Active Account \n3 - Deactive Account \n4 - Back \n5 - Exit \n");
		while(true) {
			System.out.print("Choice : ");
			select =input.nextLine();
			if(select.equals("1")) {
				admin_p2_AddE();
				break ;
			}else if(select.equals("2")) {
				admin_p2_ActiveE();
				break ;
			}else if(select.equals("3")) {
				admin_p3_DeactiveE();
				break ;
			}else if(select.equals("4")){
				admin_login();
			}else if(select.equals("5")){
				System.exit(0);
			}else {
				System.out.println("\nInvalid Input");
			}
		}
	}
    
	public static void admin_p2_AddE() {
		String select = "" ;
		System.out.println("\n\n1 - Admin \n2 - Normal \n");
		while(true) {
			select =input.nextLine();
			System.out.print("Choice : ");
			if(select.equals("1")) {
				Create_user(true);
				break ;
			}else if(select.equals("2")) {
				Create_user(false);
				break ;
			}
		}
	}

	public static void admin_p2_ActiveE() {
    	ArrayList<Integer> E_list = get_all_DeactivatedE();
		Employee employee = new Employee();
    	int count = 0 ;
    	for(Integer num : E_list) {
    		count++;
    		employee.FillById(num);
    		System.out.print("\n"+count+" - "+employee.getName()+"\n");
    	}
    	
    	Integer choice ;
    	while(true){
     		System.out.print("\n Choice : ");
     		if(input.hasNextInt()){
    			choice = input.nextInt();
    			if(choice > 0 && choice <= E_list.size())
    			   break;
     		}else{
    		    System.out.println("\nWrong value entered");
    		    if(input.hasNextLine())
    	    		input.nextLine();			    
    		}
        } 
    	employee.FillById(E_list.get(choice-1));
    	employee.ActivatedE(employee.getId());
		admin_login();
	}

	public static void admin_p3_DeactiveE() {
    	ArrayList<Integer> E_list = get_all_ActivatedE();
		Employee employee = new Employee();
    	int count = 0 ;
    	for(Integer num : E_list) {
    		count++;
    		employee.FillById(num);
    		System.out.print("\n"+count+" - "+employee.getName()+"\n");
    	}
		
    	Integer choice ;
    	while(true){
     		System.out.print("\n Choice : ");
     		if(input.hasNextInt()){
    			choice = input.nextInt();
    			if(choice > 0 && choice <= E_list.size())
    			   break;
     		}else{
    		    System.out.println("\nWrong value entered");
    		    if(input.hasNextLine())
    	    		input.nextLine();		    
    		}
        } 
    	employee.FillById(E_list.get(choice-1));
    	employee.DactivatedE(employee.getId());
		admin_login();
	}

    public static ArrayList<Integer>  get_all_DeactivatedE() {
    	ArrayList<Integer> Deactivated = new ArrayList<Integer>();
    	Employee employee = new Employee();
		Integer number_Employees = employee.getnumObj();
		System.out.println("");
		for(int count = 0 ; count < number_Employees ; count++) {
			employee = new Employee();
			employee.FillById(2000+1+count);
			if(employee.CheckManger() || employee.CheckStatus()) {
				continue;
			}else {
				Deactivated.add(employee.getId());
			}
		}
		return Deactivated;
    }

    public static ArrayList<Integer>  get_all_ActivatedE() {
    	ArrayList<Integer> Activated = new ArrayList<Integer>();
    	Employee employee = new Employee();
		Integer number_Employees = employee.getnumObj();
		System.out.println("");
		for(int count = 0 ; count < number_Employees ; count++) {
			employee = new Employee();
			employee.FillById(2000+1+count);
			if((employee.CheckManger() || !employee.CheckStatus())) {
				continue;
			}else {
				Activated.add(employee.getId());
			}
		}
		return Activated;
    }
	
    //////////////////////##########Admin Part3 (Reports)############///////////////////////////////////

    public static void admin_p3() {
    	transaction trans = new transaction();
    	Integer num_transaction = trans.num_of_transaction();
    	for(int counter = 1  ;counter <= num_transaction ; counter++) {
    		trans.FillById(10000+counter);
    		System.out.println("- "+trans.toString2()+"\n");
    	}
	}

    //////////////////////##########Admin Part4 (Customer)############///////////////////////////////////

    public static Integer[] user_p1_c_cutomer() {
    	Integer[] flags = new Integer[2];
    	System.out.print("\nEnter Name : ");
		String name  = input.nextLine();
		Customer customer = new Customer();
		if(customer.FillByName(name)) {
			System.out.println("\nCustomer was Founded!");
			flags[0] = 1;
			flags[1] = customer.getId();
		}else {
				flags[0] = 0;
				flags[1] = customer.getId();
		}
		
		return flags;
    	
    }
    
    public static void admin_p4() {
    	Integer[] Flag =user_p1_c_cutomer();
		Double discount = 0.0;
    	if(Flag[0].equals(1)) {
    		while(true){
        		System.out.print("\nEnter Discount percentage : ");
    			if(input.hasNextDouble()){
    				discount = input.nextDouble();
        			if(discount >= 0.0 && discount <= 15.0)
        			   break;
        			else 
        				System.out.println("Invalid value entered");
         		}else{
        		    System.out.println("Wrong value entered");
        		    if(input.hasNextLine())
        	    		input.nextLine();			    
        		}	
    		}
    		Customer customer = new Customer();
        	customer.FillById(Flag[1]);
        	customer.setDiscount(discount);
    	}else {
    		System.out.print("\nCannot Find this customer !");
    	}
    	if(input.hasNextLine())
    		input.nextLine();
    }

    //////////////////////########## USER PART ############///////////////////////////////////
    
  
    
    public static void user_p2() {
    	String select = "" ;
        System.out.println("\n1 - Instruments \n2 - Parts \n3 - Back \n4 - Exit \n");
		while(true) {
			System.out.print("Choice : ");
			select =input.nextLine();
			if(select.equals("1")) {
				user_p2_I();
				break ;
			}else if(select.equals("2")) {
				user_p2_IP();
				break ;
			}else if(select.equals("3")){
				user_login();
			}else if(select.equals("4")){
				System.exit(0);
			}else {
				System.out.println("\nInvalid Input");
			}
		}
    }
    

                               //########## part 1  ############//
    
    
        
                        //########## part 2 Instrument ############//

    public static void user_p2_I() {
    	String select = "" ;
        System.out.println("\n1 - By Choosing \n2 - ByName \n3 - Back \n4 - Exit \n");
		while(true) {
			System.out.print("Choice : ");
			select =input.nextLine();
			if(select.equals("1")) {
		    	user_p2_I_Choosing();
				break ;
			}else if(select.equals("2")) {
				user_p2_I_Name();
				break ;
			}else if(select.equals("3")){
				user_p2();
			}else if(select.equals("4")){
				System.exit(0);
			}else {
				System.out.println("\nInvalid Input");
			}
		}
		user_login();
    	
    }
    
    public static void user_p2_I_Choosing() {
    	Instrument instrument = new Instrument();
		int choice = 0;
		System.out.print("\nInstrument Fetching . . .\n");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
		}		
    	print_all_Instruments();
    	while(true){
     		System.out.print("\nChoice : ");
     		if(input.hasNextInt()){
    			choice = input.nextInt();
    			if(choice > 0 && choice <= instrument.getnumObj())
    			   break;
     		}else{
    		    System.out.println("Wrong value entered");
    		    if(input.hasNextLine())
    	    		input.nextLine();			    
    		}
        } 
    	
    	instrument.FillById(5000+choice);
    	System.out.println("\n"+instrument.toString());
    	if(input.hasNextLine())
    		input.nextLine();
		user_login();
    }
    
    public static void user_p2_I_Name() {
    	String name  = "";
    	Instrument instrument = new Instrument();
    	System.out.println("\nInstrument Name : ");
    	name = input.nextLine();
		System.out.print("\nInstrument Fetching . . .\n");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
		}		
	    instrument.FillByName(name);
	    if(instrument.getname() != null) {
	    	System.out.println("\n"+instrument.toString());
	    	if(input.hasNextLine())
	    		input.nextLine();
	    }else {
	    	System.out.print("Sorry ,I can not find name in stored Data");
	    }
		user_login();
    }
    
                       //########## part 2 Instrument Part ############//
    
    public static void user_p2_IP() {
    	String select = "" ;
        System.out.println("\n1 - By Choosing \n2 - ByName \n3 - Back \n4 - Exit \n");
		while(true) {
			System.out.print("Choice : ");
			select =input.nextLine();
			if(select.equals("1")) {
		    	user_p2_IP_Choosing();
				break ;
			}else if(select.equals("2")) {
				user_p2_IP_Name();
				break ;
			}else if(select.equals("3")){
				user_p2();
			}else if(select.equals("4")){
				System.exit(0);
			}else {
				System.out.println("\nInvalid Input");
			}
		}
		user_login();
    	
    }
    
    public static void user_p2_IP_Choosing() {
    	Instrument_Part part = new Instrument_Part();
		int choice = 0;
		System.out.print("\nInstrument Part Fetching . . .\n");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
		}		
		print_all_Parts();
    	while(true){
     		System.out.print("\nChoice : ");
     		if(input.hasNextInt()){
    			choice = input.nextInt();
    			if(choice > 0 && choice <= part.getnumObj())
    			   break;
     		}else{
    		    System.out.println("Wrong value entered");
    		    if(input.hasNextLine())
    	    		input.nextLine();			    
    		}
        } 
    	
    	part.FillById(7000+choice);
    	System.out.println("\n"+part.toString());
    	if(input.hasNextLine())
    		input.nextLine();
		user_login();
    }
    
    public static void user_p2_IP_Name() {
    	String name  = "";
    	Instrument_Part part = new Instrument_Part();
    	System.out.println("\nInstrument Part Name : ");
    	name = input.nextLine();
		System.out.print("\nInstrument Part Fetching . . .\n");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
		}		
		part.FillByName(name);
	    if(part.getname() != null) {
	    	System.out.println("\n"+part.toString());
	    }else {
	    	System.out.print("Sorry ,I can not find name in stored Data");
	    }
		user_login();
    }
    
    /*#############################################################################################################*/
}