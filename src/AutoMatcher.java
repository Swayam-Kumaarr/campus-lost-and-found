public class AutoMatcher implements Runnable {

    private final ItemStore store;
    private volatile boolean running = true;
    private volatile boolean paused = false;

    public AutoMatcher(ItemStore store) {
        this.store = store;
    }

    @Override
    public void run() {
        int lastSeen = store.getAddCount();
        while (running) {
            Item newest;
            synchronized (store) {
                while (running && (paused || store.getAddCount() == lastSeen)) {
                    try {
                        store.wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                }
                lastSeen = store.getAddCount();
                newest = store.getLatest();
            }
            if (running && newest != null) {
                store.printMatches(newest, "\n[Auto-Matcher] Possible match found! ");
            }
        }
        System.out.println("[Auto-Matcher] stopped.");
    }

    public boolean isPaused() { return paused; }
    public void pause() { paused = true; }

    public void resume() {
        paused = false;
        store.wakeUp();
    }

    public void stop() {
        running = false;
        store.wakeUp();
    }
}
