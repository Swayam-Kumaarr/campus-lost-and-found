public class MatchEngine {
 
    public static class MatchResult {
        private final LostItem lost;
        private final FoundItem found;
        private final double score;
 
        public MatchResult(LostItem lost, FoundItem found, double score) {
            this.lost = lost;
            this.found = found;
            this.score = score;
        }
 
        @Override
        public String toString() {
            int percent = (int) (score * 100);
            return percent + "% match: LOST #" + lost.getId() + " " + lost.getName()
                    + "  <->  FOUND #" + found.getId() + " " + found.getName()
                    + " (kept at " + found.getHandedTo() + ")";
        }
    }
 
    private final Matcher matcher;
 
    public MatchEngine(Matcher matcher) {
        this.matcher = matcher;
    }
 
    public MatchResult[] findMatches(ItemStore store) {
        LostItem[] lost = store.getLostItems();
        FoundItem[] found = store.getFoundItems();
        MatchResult[] temp = new MatchResult[lost.length * found.length];
        int n = 0;
        for (LostItem l : lost) {
            for (FoundItem f : found) {
                double s = matcher.score(l, f);
                if (s >= Matcher.MIN_SCORE) {
                    temp[n] = new MatchResult(l, f, s);
                    n++;
                }
            }
        }
        return trim(temp, n);
    }
 
    public MatchResult[] findMatches(Item item, ItemStore store) {
        if (item instanceof LostItem) {
            LostItem l = (LostItem) item;
            FoundItem[] found = store.getFoundItems();
            MatchResult[] temp = new MatchResult[found.length];
            int n = 0;
            for (FoundItem f : found) {
                double s = matcher.score(l, f);
                if (s >= Matcher.MIN_SCORE) {
                    temp[n] = new MatchResult(l, f, s);
                    n++;
                }
            }
            return trim(temp, n);
        } else {
            FoundItem f = (FoundItem) item;
            LostItem[] lost = store.getLostItems();
            MatchResult[] temp = new MatchResult[lost.length];
            int n = 0;
            for (LostItem l : lost) {
                double s = matcher.score(l, f);
                if (s >= Matcher.MIN_SCORE) {
                    temp[n] = new MatchResult(l, f, s);
                    n++;
                }
            }
            return trim(temp, n);
        }
    }
 
    private MatchResult[] trim(MatchResult[] temp, int n) {
        MatchResult[] result = new MatchResult[n];
        for (int i = 0; i < n; i++) {
            result[i] = temp[i];
        }
        return result;
    }
}
