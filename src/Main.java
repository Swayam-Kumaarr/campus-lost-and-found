import java.io.IOException;
import java.util.Scanner;
 
public class Main {
 
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        InputHelper in = new InputHelper(sc);
        ItemStore store = new ItemStore(100);
        Matcher matcher = new KeywordMatcher();
        MatchEngine engine = new MatchEngine(matcher);
        FileManager files = new FileManager("items.txt");
 
        System.out.println("=====================================");
        System.out.println("Campus lost and found matching system");
        System.out.println("=====================================");
 
        try {
            int loaded = files.load(store);
            System.out.println("Loaded " + loaded + " item(s) from items.txt");
        } catch (IOException e) {
            System.out.println("Could not load saved items: " + e.getMessage());
        }
 
        int choice;
        do {
            System.out.println();
            System.out.println("1.Report an item you lost inside the campus");
            System.out.println("2.Report an item you found un-attended in campus");
            System.out.println("3.View all items");
            System.out.println("4.Search items");
            System.out.println("5.Run matching");
            System.out.println("6.Mark an item as returned");
            System.out.println("7.Save and back up");
            System.out.println("0.Exit");
            choice = in.readInt("Enter your choice: ");
 
            try {
                switch (choice) {
                case 1:
                    reportLost(in, store);
                    break;
                case 2:
                    reportFound(in, store);
                    break;
                case 3:
                    store.displayAll();
                    break;
                case 4:
                    search(in, store);
                    break;
                case 5:
                    printMatches(engine.findMatches(store));
                    break;
                case 6:
                    markReturned(in, store);
                    break;
                case 7:
                    files.save(store);
                    files.backup("items_backup.txt");
                    System.out.println("Saved to items.txt and backed up to items_backup.txt");
                    break;
                case 0:
                    files.save(store);
                    System.out.println("All items saved. Thank you for choosing us");
                    break;
                default:
                    System.out.println("Please choose a valid options");
                }
            } catch (InvalidInputException e) {
                System.out.println("Cancelled: " + e.getMessage());
            } catch (ItemNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println("File error: " + e.getMessage());
            }
        } while (choice != 0);
        sc.close();
    }
 
    static Student readStudent(InputHelper in) throws InvalidInputException {
        String name = in.readText("Your name: ");
        String contact = in.readText("Your phone/email: ");
        return new Student(name, contact);
    }
 
    static void reportLost(InputHelper in, ItemStore store) throws InvalidInputException {
        Student student = readStudent(in);
        String name = in.readText("Item name: ");
        String category = in.readText("Category (e.g. Electronics, Books, ID Card): ");
        String location = in.readText("Where did you lose it: ");
        double reward = in.readDouble("Reward in Rs (0 for none): ");
 
        Item item = new LostItem(name, category, location, student, reward);
        addToStore(store, item);
    }
 
    static void reportFound(InputHelper in, ItemStore store) throws InvalidInputException {
        Student student = readStudent(in);
        String name = in.readText("Item name: ");
        String category = in.readText("Category (e.g. Electronics, Books, ID Card): ");
        String location = in.readText("Where did you find it: ");
        String handedTo = in.readText("Where is it kept now (e.g. Security desk): ");
        String valuable = in.readText("Is it valuable, like a phone, wallet or ID card? (y/n): ");
 
        Item item;
        if (valuable.equalsIgnoreCase("y")) {
            String secret = in.readText("A hidden detail only the owner would know: ");
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
 
    static void search(InputHelper in, ItemStore store) throws InvalidInputException {
        int mode = in.readInt("Search by 1.ID or 2.Keyword: ");
        if (mode == 1) {
            int id = in.readInt("Enter item ID: ");
            System.out.println(store.find(id));
        } else {
            String keyword = in.readText("Enter keyword: ");
            Item[] results = store.find(keyword);
            if (results.length == 0) {
                System.out.println("Nothing matches \"" + keyword + "\"");
            }
            for (Item item : results) {
                System.out.println(item);
            }
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
 
    static void markReturned(InputHelper in, ItemStore store) throws InvalidInputException {
        int id = in.readInt("ID of the item that was returned: ");
        Item item = store.find(id);
        if (item instanceof ValuableFoundItem) {
            ValuableFoundItem v = (ValuableFoundItem) item;
            String answer = in.readText("Owner, describe the hidden detail: ");
            if (!answer.equalsIgnoreCase(v.getSecretDetail())) {
                System.out.println("Detail does not match. Item NOT released.");
                return;
            }
        }
        store.removeItem(id);
        System.out.println("Removed: " + item);
    }
}
