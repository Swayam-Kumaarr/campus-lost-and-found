public class AutoMatcher implements Runnable {
 
    private final ItemStore store;
    private final MatchEngine engine;
 
    private volatile boolean running = true;
    private volatile boolean paused = false;
 
    public AutoMatcher(ItemStore store, MatchEngine engine) {
        this.store = store;
        this.engine = engine;
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
                if (!running) {
                    break;
                }
                lastSeen = store.getAddCount();
                newest = store.getLatest();
            }
            if (newest == null) {
                continue;
            }
            MatchEngine.MatchResult[] results = engine.findMatches(newest, store);
            for (MatchEngine.MatchResult r : results) {
                System.out.println();
                System.out.println("[Auto-Matcher] Possible match found! " + r);
            }
        }
        System.out.println("[Auto-Matcher] stopped.");
    }
 
    public void pause() {
        paused = true;
    }
 
    public void resume() {
        paused = false;
        store.wakeUpAll();
    }
 
    public void stop() {
        running = false;
        store.wakeUpAll();
    }
 
    public boolean isPaused() {
        return paused;
    }
}
