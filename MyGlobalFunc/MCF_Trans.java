package MyGlobalFunc;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import MusicStore.Customer;
import MusicStore.Instrument;
import MusicStore.Instrument_Part;
import MusicStore.transaction;

public class MCF_Trans {
	static Scanner  input = MCF.input;
 	static ArrayList<Instrument> instrusmets = new ArrayList<Instrument>();
 	static ArrayList<Integer> instrusmets_quantity = new ArrayList<Integer>();
 	static ArrayList<Boolean> insurance_instruments = new  ArrayList<Boolean>();
 	static ArrayList<Instrument_Part> parts = new  ArrayList<Instrument_Part>();
 	static ArrayList<Integer> parts_quantity = new ArrayList<Integer>();
 	static ArrayList<Boolean> insurance_parts = new ArrayList<Boolean>();
	       
 	  
	    public static void CLEAR_ALL() {
	    	instrusmets.clear() ;
	     	instrusmets_quantity.clear();
	     	insurance_instruments.clear();
	    	parts.clear();
	    	parts_quantity.clear();
	        insurance_parts.clear();
	    }
	    
	    
	    public static void user_p1() {
	    	CLEAR_ALL();
	    	Integer[] flag = user_p1_c_cutomer();
	    	if(!flag[0].equals(-1)) {
	    		Integer customer_id =  flag[1];
	    		Customer customer = new Customer();
	    		customer.FillById(customer_id);
	    		Boolean Discount = false;
	    		
	    		String select = "" ;
				while(true) {
			        System.out.println("\n1 - Add OR Remove OR Change Item \n2 - List Items\n3 - Save\n4 - Cancel \n5 - Exit \n");
					System.out.print("Choice : ");
					select =input.nextLine();
					if(select.equals("1")) {
			    		user_p1_AR();
					}else if(select.equals("2")) {
						ListItems();
					}else if(select.equals("3")) {
						if(flag[0].equals(1)) {
			    			System.out.println("\nDo you need Discount (y/n)? ");
				  		    char discount = input.next().charAt(0);
				  		    if(discount == 'y')
				  		    	Discount = true;
			    		}
			    		transaction trans = new  transaction(customer,Discount, instrusmets,instrusmets_quantity,insurance_instruments,parts,parts_quantity,insurance_parts,true);
			    		System.out.println("\n"+trans.toString());
						break ;
					}else if(select.equals("4")) {
						break ;
					}else if(select.equals("5")){
						System.exit(0);
					}
				}
	    	}
	    	if(input.hasNextLine())
	    		input.nextLine();
	    	MCF.user_login();
	    }
	    
	    
	    public static Integer[] user_p1_c_cutomer() {
	    	Integer[] flags = new Integer[2];
	    	System.out.print("\nEnter Name : ");
			String name  = input.nextLine();
			Customer customer = new Customer();
			MCFF.RemoveEmpty("Customer");
			if(customer.FillByName(name)) {
				System.out.println("\nCustomer was Founded!");
				flags[0] = 1;
				flags[1] = customer.getId();
			}else {
				customer = new Customer(name,true);
				if(customer.getName() == null) {
					System.out.println("\nSorry I can not add this name !");
					flags[0] = -1;
					flags[1] = -1;
				}else {
					System.out.println("\nCustomer was added!");
					flags[0] = 0;
					flags[1] = customer.getId();
		 		}
			}
			
			return flags;
	    	
	    }
	    
	    
	    public static void user_p1_AR() {
	    	String select = "" ;
	        System.out.println("\n1 - Add \n2 - Remove \n3 - Change Quantity \n4 - Exit \n");
			while(true) {
				System.out.print("Choice : ");
				select =input.nextLine();
				if(select.equals("1")) {
					add_item();
					break ;
				}else if(select.equals("2")) {
					remove_item();
					break ;
				}else if(select.equals("3")) {
					Change_Q_item();
					break ;
				}else if(select.equals("4")){
					System.exit(0);
				}else {
					System.out.println("\nInvalid Input");
				}
			}
	    }
	    
	    public static void add_item() {
	    	String select = "" ;
	        System.out.println("\n1 - Instrument \n2 - Part \n3 - Exit \n");
			while(true) {
				System.out.print("Choice : ");
				select =input.nextLine();
				if(select.equals("1")) {
					add_Instrument();
					break ;
				}else if(select.equals("2")) {
					add_Part();
					break ;
				}else if(select.equals("3")){
					System.exit(0);
				}else {
					System.out.println("\nInvalid Input");
				}
			}	
	    }
	    
	    public static void remove_item() {
	    	String select = "" ;
	        System.out.println("\n1 - Instrument \n2 - Part \n3 - Exit \n");
			while(true) {
				System.out.print("Choice : ");
				select =input.nextLine();
				if(select.equals("1")) {
					remove_Instrument();
					break ;
				}else if(select.equals("2")) {
					remove_Part();
					break ;
				}else if(select.equals("3")){
					System.exit(0);
				}else {
					System.out.println("\nInvalid Input");
				}
			}
	    }
	  
        public static void add_Instrument() {
		    instrusmets.add(repeatedInstrumentcode());
		    Integer Quantity = 0;
		    while(true){
	     		System.out.print("\nQuantity : ");
	     		if(input.hasNextInt()){
	     			Quantity = input.nextInt();
	    			if(Quantity > 0)
	    			   break;
	    			else
		    		    System.out.println("Invalid value entered");
	     		}else{
	    		    System.out.println("Wrong value entered");
	    		    input.nextLine();			    
	    		}
	        } 
		    instrusmets_quantity.add(Quantity);
		    System.out.println("\nDo you need insurance (y/n)?");
		    char insurance = input.next().charAt(0);
		    if(insurance == 'y')
		    	insurance_instruments.add(true);
		    else
		    	insurance_instruments.add(false);
	   }
        
        public static Instrument repeatedInstrumentcode() {
        	Instrument instrument = new Instrument();
			int choice = 0;
			System.out.print("\nInstrument Fetching . . .\n");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
			}		
			MCF.print_all_Instruments();
	    	while(true){
	     		System.out.print("\nChoice : ");
	     		if(input.hasNextInt()){
	    			choice = input.nextInt();
	    			if(choice > 0 && choice <= instrument.getnumObj())
	    			   break;
	     		}else{
	    		    System.out.println("Wrong value entered");
	    		    input.nextLine();			    
	    		}
	        } 
	    	
	    	instrument.FillById(5000+choice);
	    	return instrument;
        }

        public static void add_Part() {
           Instrument instrument = repeatedInstrumentcode();
           System.out.print("\nInstrument Part Fetching . . .\n");
           MCF.print_all_Partsofintruments(instrument);
    	   Instrument_Part part  = new Instrument_Part();
    	   int choice = 0;
      		try {
      			TimeUnit.SECONDS.sleep(1);
      		} catch (InterruptedException e) {
      		}		
          	while(true){
           		System.out.print("\nChoice : ");
           		if(input.hasNextInt()){
          			choice = input.nextInt();
          			if(choice > 0 && choice <= part.getnumObj())
          			   break;
           		}else{
          		    System.out.println("Wrong value entered");
          		    input.nextLine();			    
          		}
              } 
          	
          	part.FillById(7000+choice);
          	parts.add(part);
 		    Integer Quantity = 0;
 		    while(true){
 	     		System.out.print("\nQuantity : ");
 	     		if(input.hasNextInt()){
 	     			Quantity = input.nextInt();
 	    			if(Quantity > 0)
 	    			   break;
 	     		}else{
 	    		    System.out.println("Wrong value entered");
 	    		    input.nextLine();			    
 	    		}
 	        } 
    		parts_quantity.add(Quantity);
 		    System.out.println("\nDo you need insurance (y/n)? ");
 		    char insurance = input.next().charAt(0);
 		    if(insurance == 'y')
 		    	insurance_parts.add(true);
 		    else
 		    	insurance_parts.add(false);
       }
	    	    
        public static void remove_Instrument() {
       		System.out.print("\nInstrument Fetching in Customer Order . . .\n");
        	printInstrumentOrder();
       		try {
       			TimeUnit.SECONDS.sleep(1);
       		} catch (InterruptedException e) {
       		}		
        	int choice = 0;
       		while(true){
            		System.out.print("\nChoice : ");
            		if(input.hasNextInt()){
           			choice = input.nextInt();
           			if(choice > 0 && choice <= instrusmets.size())
           			   break;
            		}else{
           		    System.out.println("Wrong value entered");
           		    input.nextLine();			    
           		}
               } 
        	instrusmets.remove(choice-1);
        	instrusmets_quantity.remove(choice-1);
        	insurance_instruments.remove(choice-1);
         	
        }
        
        public static void printInstrumentOrder() {
        	for(int counter = 0 ; counter < instrusmets.size() ; counter++) {
        		System.out.println("\n"+(counter+1)+" - "+instrusmets.get(counter).getname());
        	}
        }
        
        public static void printPartOrder() {
        	for(int counter = 0 ; counter < parts.size() ; counter++) {
        		System.out.println("\n"+(counter+1)+" - "+parts.get(counter).getname());
        	}
        }
       
        public static void remove_Part() {
       		System.out.print("\nInstrument Part Fetching in Customer Order . . .\n");
       		try {
       			TimeUnit.SECONDS.sleep(1);
       		} catch (InterruptedException e) {
       		}
       		printPartOrder();
        	int choice = 0;
           	while(true){
            		System.out.print("\nChoice : ");
            		if(input.hasNextInt()){
           			choice = input.nextInt();
           			if(choice > 0 && choice <= parts.size())
           			   break;
            		}else{
           		    System.out.println("Wrong value entered");
           		    input.nextLine();			    
           		}
               } 
           	parts.remove(choice-1);
        	parts_quantity.remove(choice-1);
        	insurance_parts.remove(choice-1);
        }
        
        public static void ListItems() {
        	String content= "\n\n";
        	for(int counter = 0 ; counter < instrusmets.size() ; counter++) {
    			content+= instrusmets.get(counter).getname()+" || "+ instrusmets_quantity.get(counter) + " || "+ insurance_instruments.get(counter) +"\n";			
    		}
    		for(int counter = 0 ; counter < parts.size() ; counter++) {
    			content+= parts.get(counter).getname()+" || "+ parts_quantity.get(counter) + " || "+ insurance_parts.get(counter) +"\n";			
    		}	
    		System.out.print(content);
        }
        

        public static void Change_Q_item() {
        	String select = "" ;
	        System.out.println("\n1 - Instrument \n2 - Part \n3 - Exit \n");
			while(true) {
				System.out.print("Choice : ");
				select =input.nextLine();
				if(select.equals("1")) {
					Change_Q_Instrument();
					break ;
				}else if(select.equals("2")) {
					Change_Q_Part();
					break ;
				}else if(select.equals("3")){
					System.exit(0);
				}else {
					System.out.println("\nInvalid Input");
				}
			}
        }
        
        
        public static void Change_Q_Instrument() {
        	System.out.print("\nInstrument  Fetching in Customer Order . . .\n");
       		try {
       			TimeUnit.SECONDS.sleep(1);
       		} catch (InterruptedException e) {
       		}
       		printInstrumentOrder();
        	int choice = 0;
           	while(true){
            		System.out.print("\nChoice : ");
            		if(input.hasNextInt()){
           			choice = input.nextInt();
           			if(choice > 0 && choice <= instrusmets.size())
           			   break;
            		}else{
           		    System.out.println("Wrong value entered");
           		    input.nextLine();			    
           		}
               } 
           	Integer Quantity =  instrusmets_quantity.get(choice-1);
		    while(true){
	     		System.out.print("\nQuantity : ");
	     		if(input.hasNextInt()){
	     			Quantity = input.nextInt();
	    			if(Quantity > 0)
	    			   break;
	    			else
		    		    System.out.println("Invalid value entered");
	     		}else{
	    		    System.out.println("Wrong value entered");
	    		    input.nextLine();			    
	    		}
	        } 
		    instrusmets_quantity.set(choice-1, Quantity);
        }
        
        
        /*
         * 
         * parts = new  ArrayList<Instrument_Part>();
 	static ArrayList<Integer> parts_quantity = new ArrayList<Integer>();
 	static ArrayList<Boolean> insurance_parts = new ArrayList<Boolean>();
         */
        
        public static void Change_Q_Part() {
        	System.out.print("\nInstrument Part Fetching in Customer Order . . .\n");
       		try {
       			TimeUnit.SECONDS.sleep(1);
       		} catch (InterruptedException e) {
       		}
       		printPartOrder();
        	int choice = 0;
           	while(true){
            		System.out.print("\nChoice : ");
            		if(input.hasNextInt()){
           			choice = input.nextInt();
           			if(choice > 0 && choice <= parts.size())
           			   break;
            		}else{
           		    System.out.println("Wrong value entered");
           		    input.nextLine();			    
           		}
               } 
           	Integer Quantity =  parts_quantity.get(choice-1);
		    while(true){
	     		System.out.print("\nQuantity : ");
	     		if(input.hasNextInt()){
	     			Quantity = input.nextInt();
	    			if(Quantity > 0)
	    			   break;
	    			else
		    		    System.out.println("Invalid value entered");
	     		}else{
	    		    System.out.println("Wrong value entered");
	    		    input.nextLine();			    
	    		}
	        } 
		    parts_quantity.set(choice-1, Quantity);
        }
}
