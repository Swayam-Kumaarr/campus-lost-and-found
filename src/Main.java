import java.util.Scanner;


public class Main {
	public static void main(String[] args) {
Scanner sc= new Scanner(System.in);
int choice;

System.out.println("=====================================");
System.out.println("Campus lost and found matching system");
System.out.println("=====================================");


do {
	System.out.println();
	System.out.println("1.Report an item you lost inside the campus");
	System.out.println("2.Report an item you found un-attended in campus");
	System.out.println("3.View all items");
	System.out.println("0.Exit");
	
	choice = sc.nextInt();
	
	switch(choice) {
	case 1:
		System.out.println("Report the name of your lost item");
		break;
	
	case 2:
		System.out.println("Report the name of the item you found");
		break;
	case 3:
		System.out.println("View all present items");
		break;
	case 0:
		System.out.println("Thank you for choosing us");
		break;
default : 
	System.out.println("Please choose a valid options");
	
}
}
while(choice !=0);
sc.close();
}
}
