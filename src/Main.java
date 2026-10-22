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
 
        AutoMatcher autoMatcher = new AutoMatcher(store, engine);
        Thread matcherThread = new Thread(autoMatcher, "AutoMatcher");
        matcherThread.start();
 
        AutoSaver saver = new AutoSaver(store, files);
        saver.setPriority(Thread.MIN_PRIORITY);
        saver.start();
 
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
            System.out.println("8.Pause / resume auto-matcher");
            System.out.println("9.System status");
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
                case 8:
                    if (autoMatcher.isPaused()) {
                        autoMatcher.resume();
                        System.out.println("Auto-matcher resumed.");
                    } else {
                        autoMatcher.pause();
                        System.out.println("Auto-matcher paused.");
                    }
                    break;
                case 9:
                    showStatus(store, matcherThread, autoMatcher, saver);
                    break;
                case 0:
                    System.out.println("Thank you for choosing us");
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
 
        shutdown(store, files, autoMatcher, matcherThread, saver);
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
 
    static void showStatus(ItemStore store, Thread matcherThread, AutoMatcher autoMatcher, AutoSaver saver) {
        store.getSummary().print();
        System.out.println("AutoMatcher thread: " + matcherThread.getState()
                + (autoMatcher.isPaused() ? " (paused)" : ""));
        System.out.println("AutoSaver thread: " + saver.getState() + ", priority " + saver.getPriority());
 
        Runtime rt = Runtime.getRuntime();
        long before = rt.totalMemory() - rt.freeMemory();
        System.gc();
        long after = rt.totalMemory() - rt.freeMemory();
        System.out.println("Memory used: " + before / 1024 + " KB -> " + after / 1024 + " KB after garbage collection");
    }
 
    static void shutdown(ItemStore store, FileManager files, AutoMatcher autoMatcher,
            Thread matcherThread, AutoSaver saver) {
        autoMatcher.stop();
        saver.stopSaving();
        try {
            matcherThread.join();
            saver.join();
            files.save(store);
            System.out.println("All items saved. Goodbye!");
        } catch (InterruptedException e) {
            System.out.println("Interrupted while stopping threads.");
        } catch (IOException e) {
            System.out.println("Could not save: " + e.getMessage());
        }
    }
}
