import java.io.IOException;
 
public class AutoSaver extends Thread {
 
    private final ItemStore store;
    private final FileManager files;
    private volatile boolean running = true;
 
    public AutoSaver(ItemStore store, FileManager files) {
        super("AutoSaver");
        this.store = store;
        this.files = files;
    }
 
    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(30000);
                files.save(store);
            } catch (InterruptedException e) {
                running = false;
            } catch (IOException e) {
                System.out.println("[AutoSaver] could not save: " + e.getMessage());
            }
        }
    }
 
    public void stopSaving() {
        running = false;
        interrupt();
    }
}
