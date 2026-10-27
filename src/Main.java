import java.io.IOException;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        ItemStore store = new ItemStore();
        System.out.println("=====================================");
        System.out.println("Campus lost and found matching system");
        System.out.println("=====================================");
        try {
            System.out.println("Loaded " + store.load("items.txt") + " item(s) from items.txt");
        } catch (IOException e) {
            System.out.println("Could not load saved items: " + e.getMessage());
        }

        AutoMatcher autoMatcher = new AutoMatcher(store);
        Thread matcherThread = new Thread(autoMatcher, "AutoMatcher");
        matcherThread.start();

        int choice;
        do {
            System.out.println("\n1.Report an item you lost inside the campus\n2.Report an item you found un-attended in campus");
            System.out.println("3.View all items\n4.Search item by ID\n5.Run matching\n6.Mark an item as returned");
            System.out.println("7.Save and back up\n8.Pause / resume auto-matcher\n0.Exit");
            choice = readInt("Enter your choice: ");
            try {
                switch (choice) {
                case 1: reportItem(store, true); break;
                case 2: reportItem(store, false); break;
                case 3: store.displayAll(); break;
                case 4: System.out.println(store.find(readInt("Enter item ID: "))); break;
                case 5:
                    if (store.printMatches(null, "") == 0) {
                        System.out.println("No likely matches yet.");
                    }
                    break;
                case 6: markReturned(store); break;
                case 7:
                    store.save("items.txt");
                    store.backup("items.txt", "items_backup.txt");
                    System.out.println("Saved to items.txt and backed up to items_backup.txt");
                    break;
                case 8:
                    if (autoMatcher.isPaused()) {
                        autoMatcher.resume();
                    } else {
                        autoMatcher.pause();
                    }
                    System.out.println("Auto-matcher " + (autoMatcher.isPaused() ? "paused." : "resumed."));
                    break;
                case 0: System.out.println("Thank you for choosing us"); break;
                default: System.out.println("Please choose a valid options");
                }
            } catch (ItemNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println("File error: " + e.getMessage());
            }
        } while (choice != 0);

        autoMatcher.stop();
        try {
            matcherThread.join();
            store.save("items.txt");
            System.out.println("All items saved. Goodbye!");
        } catch (InterruptedException | IOException e) {
            System.out.println("Could not save: " + e.getMessage());
        }
    }

    static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    static String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String text = sc.nextLine().trim();
            if (!text.isEmpty() && !text.contains("|")) {
                return text;
            }
            System.out.println("This field cannot be empty or contain |");
        }
    }

    static void reportItem(ItemStore store, boolean lost) {
        String reporter = readText("Your name: ");
        String contact = readText("Your phone/email: ");
        String name = readText("Item name: ");
        String category = readText("Category (e.g. Electronics, Books, ID Card): ");
        String location = readText(lost ? "Where did you lose it: " : "Where did you find it: ");

        Item item;
        if (lost) {
            double reward = readInt("Reward in Rs (0 for none): ");
            item = new LostItem(name, category, location, reporter, contact, reward);
        } else {
            String handedTo = readText("Where is it kept now (e.g. Security desk): ");
            String valuable = readText("Is it valuable, like a phone, wallet or ID card? (y/n): ");
            if (valuable.toLowerCase().startsWith("y")) {
                String secret = readText("A hidden detail only the owner would know: ");
                item = new ValuableFoundItem(name, category, location, reporter, contact, handedTo, secret);
            } else {
                item = new FoundItem(name, category, location, reporter, contact, handedTo);
            }
        }
        if (store.add(item)) {
            System.out.println("Saved: " + item);
        } else {
            System.out.println("Storage full, item not saved.");
        }
    }

    static void markReturned(ItemStore store) throws ItemNotFoundException {
        int id = readInt("ID of the item that was returned: ");
        Item item = store.find(id);
        if (item instanceof ValuableFoundItem) {
            String answer = readText("Owner, describe the hidden detail: ");
            if (!((ValuableFoundItem) item).checkSecret(answer)) {
                System.out.println("Detail does not match. Item NOT released.");
                return;
            }
        }
        store.remove(id);
        System.out.println("Removed: " + item);
    }
}
