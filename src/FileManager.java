import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
 
public class FileManager {
 
    private final String fileName;
 
    public FileManager(String fileName) {
        this.fileName = fileName;
    }
 
    public synchronized void save(ItemStore store) throws IOException {
        Item[] items = store.getAll();
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        try {
            for (Item item : items) {
                writer.write(toLine(item));
                writer.newLine();
            }
        } finally {
            writer.close();
        }
    }
 
    public int load(ItemStore store) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            return 0;
        }
        int loaded = 0;
        BufferedReader reader = new BufferedReader(new FileReader(file));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    store.addItem(fromLine(line));
                    loaded++;
                } catch (RuntimeException e) {
                    System.out.println("Skipped a damaged line: " + line);
                }
            }
        } finally {
            reader.close();
        }
        return loaded;
    }
 
    public void backup(String backupName) throws IOException {
        FileInputStream in = new FileInputStream(fileName);
        FileOutputStream out = new FileOutputStream(backupName);
        try {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        } finally {
            in.close();
            out.close();
        }
    }
 
    private String toLine(Item item) {
        String common = item.getId() + "|" + item.getName() + "|" + item.getCategory() + "|"
                + item.getLocation() + "|" + item.getReporter().getName() + "|" + item.getReporter().getContact();
 
        if (item instanceof LostItem) {
            LostItem lost = (LostItem) item;
            return "LOST|" + common + "|" + lost.getReward();
        } else if (item instanceof ValuableFoundItem) {
            ValuableFoundItem v = (ValuableFoundItem) item;
            return "VALUABLE|" + common + "|" + v.getHandedTo() + "|" + v.getSecretDetail();
        } else {
            FoundItem found = (FoundItem) item;
            return "FOUND|" + common + "|" + found.getHandedTo();
        }
    }
 
    private Item fromLine(String line) {
        String[] p = line.split("\\|");
        int id = Integer.parseInt(p[1]);
        Student student = new Student(p[5], p[6]);
 
        switch (p[0]) {
            case "LOST":
                return new LostItem(id, p[2], p[3], p[4], student, Double.parseDouble(p[7]));
            case "FOUND":
                return new FoundItem(id, p[2], p[3], p[4], student, p[7]);
            case "VALUABLE":
                return new ValuableFoundItem(id, p[2], p[3], p[4], student, p[7], p[8]);
            default:
                throw new IllegalArgumentException("Unknown item type: " + p[0]);
        }
    }
}
