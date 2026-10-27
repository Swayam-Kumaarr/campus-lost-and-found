import java.io.*;

public class ItemStore {

    private final Item[] items = new Item[100];
    private int count = 0;
    private int addCount = 0;

    public synchronized boolean add(Item item) {
        if (count == items.length) {
            return false;
        }
        items[count] = item;
        count++;
        addCount++;
        notifyAll();
        return true;
    }

    public synchronized int getAddCount() { return addCount; }
    public synchronized void wakeUp() { notifyAll(); }

    public synchronized Item getLatest() {
        return count == 0 ? null : items[count - 1];
    }

    public synchronized void displayAll() {
        if (count == 0) {
            System.out.println("No items reported yet.");
        }
        for (int i = 0; i < count; i++) {
            System.out.println(items[i]);
        }
    }

    public synchronized Item find(int id) throws ItemNotFoundException {
        for (int i = 0; i < count; i++) {
            if (items[i].getId() == id) {
                return items[i];
            }
        }
        throw new ItemNotFoundException(id);
    }

    public synchronized void remove(int id) throws ItemNotFoundException {
        for (int i = 0; i < count; i++) {
            if (items[i].getId() == id) {
                for (int j = i; j < count - 1; j++) {
                    items[j] = items[j + 1];
                }
                items[count - 1] = null;
                count--;
                return;
            }
        }
        throw new ItemNotFoundException(id);
    }

    public synchronized int printMatches(Item only, String prefix) {
        int matches = 0;
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                if (items[i] instanceof LostItem && items[j] instanceof FoundItem
                        && (only == null || only == items[i] || only == items[j])) {
                    LostItem lost = (LostItem) items[i];
                    FoundItem found = (FoundItem) items[j];
                    double score = lost.matchScore(found);
                    if (score >= Matchable.MIN_SCORE) {
                        System.out.println(prefix + (int) (score * 100) + "% match: LOST #" + lost.getId() + " "
                                + lost.getName() + "  <->  FOUND #" + found.getId() + " " + found.getName()
                                + " (kept at " + found.getHandedTo() + ")");
                        matches++;
                    }
                }
            }
        }
        return matches;
    }

    public synchronized void save(String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        try {
            for (int i = 0; i < count; i++) {
                writer.write(items[i].toFileLine());
                writer.newLine();
            }
        } finally {
            writer.close();
        }
    }

    public int load(String fileName) throws IOException {
        if (!new File(fileName).exists()) {
            return 0;
        }
        int loaded = 0;
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split("\\|");
                try {
                    Item item;
                    if (p[0].equals("LOST")) {
                        item = new LostItem(p[1], p[2], p[3], p[4], p[5], Double.parseDouble(p[6]));
                    } else if (p[0].equals("VALUABLE")) {
                        item = new ValuableFoundItem(p[1], p[2], p[3], p[4], p[5], p[6], p[7]);
                    } else {
                        item = new FoundItem(p[1], p[2], p[3], p[4], p[5], p[6]);
                    }
                    if (add(item)) {
                        loaded++;
                    }
                } catch (RuntimeException e) {
                    System.out.println("Skipped a damaged line: " + line);
                }
            }
        } finally {
            reader.close();
        }
        return loaded;
    }

    public void backup(String from, String to) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(from));
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(to));
        try {
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
        } finally {
            in.close();
            out.close();
        }
    }
}
