import java.util.Scanner;
 
public class Main {
 
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ItemStore store = new ItemStore(100);
        Matcher matcher = new KeywordMatcher();
        MatchEngine engine = new MatchEngine(matcher);
        int choice;
 
        System.out.println("=====================================");
        System.out.println("Campus lost and found matching system");
        System.out.println("=====================================");
 
        do {
            System.out.println();
            System.out.println("1.Report an item you lost inside the campus");
            System.out.println("2.Report an item you found un-attended in campus");
            System.out.println("3.View all items");
            System.out.println("4.Find an item by ID");
            System.out.println("5.Run matching");
            System.out.println("0.Exit");
            System.out.print("Enter your choice: ");
 
            choice = sc.nextInt();
            sc.nextLine();
 
            switch (choice) {
            case 1:
                reportLost(sc, store);
                break;
            case 2:
                reportFound(sc, store);
                break;
            case 3:
                store.displayAll();
                break;
            case 4:
                System.out.print("Enter item ID: ");
                int id = sc.nextInt();
                sc.nextLine();
                Item found = store.findById(id);
                if (found == null) {
                    System.out.println("No item with ID " + id);
                } else {
                    System.out.println(found);
                }
                break;
            case 5:
                printMatches(engine.findMatches(store));
                break;
            case 0:
                System.out.println("Thank you for choosing us");
                break;
            default:
                System.out.println("Please choose a valid options");
            }
        } while (choice != 0);
        sc.close();
    }
 
    static Student readStudent(Scanner sc) {
        System.out.print("Your name: ");
        String name = sc.nextLine();
        System.out.print("Your phone/email: ");
        String contact = sc.nextLine();
        return new Student(name, contact);
    }
 
    static void reportLost(Scanner sc, ItemStore store) {
        Student student = readStudent(sc);
        System.out.print("Item name: ");
        String name = sc.nextLine();
        System.out.print("Category (e.g. Electronics, Books, ID Card): ");
        String category = sc.nextLine();
        System.out.print("Where did you lose it: ");
        String location = sc.nextLine();
        System.out.print("Reward in Rs (0 for none): ");
        double reward = sc.nextDouble();
        sc.nextLine();
 
        Item item = new LostItem(name, category, location, student, reward);
        addToStore(store, item);
    }
 
    static void reportFound(Scanner sc, ItemStore store) {
        Student student = readStudent(sc);
        System.out.print("Item name: ");
        String name = sc.nextLine();
        System.out.print("Category (e.g. Electronics, Books, ID Card): ");
        String category = sc.nextLine();
        System.out.print("Where did you find it: ");
        String location = sc.nextLine();
        System.out.print("Where is it kept now (e.g. Security desk): ");
        String handedTo = sc.nextLine();
        System.out.print("Is it valuable, like a phone, wallet or ID card? (y/n): ");
        String valuable = sc.nextLine();
 
        Item item;
        if (valuable.equalsIgnoreCase("y")) {
            System.out.print("A hidden detail only the owner would know: ");
            String secret = sc.nextLine();
            item = new ValuableFoundItem(name, category, location, student, handedTo, secret);
        } else {
            item = new FoundItem(name, category, location, student, handedTo);
        }
        addToStore(store, item);
    }
 
    static void addToStore(ItemStore store, Item item) {
        if (store.addItem(item)) {
            System.out.println("Saved: " + item);
        } else {
            System.out.println("Storage full, item not saved.");
        }
    }
 
    static void printMatches(MatchEngine.MatchResult[] results) {
        if (results.length == 0) {
            System.out.println("No likely matches yet.");
            return;
        }
        for (MatchEngine.MatchResult r : results) {
            System.out.println(r);
        }
    }
}
