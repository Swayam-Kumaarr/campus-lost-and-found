public class KeywordMatcher implements Matcher {
 
    @Override
    public double score(LostItem lost, FoundItem found) {
        double score = 0;
 
        if (lost.getCategory().equalsIgnoreCase(found.getCategory())) {
            score += 0.4;
        }
 
        String lostPlace = lost.getLocation().toLowerCase();
        String foundPlace = found.getLocation().toLowerCase();
        if (lostPlace.contains(foundPlace) || foundPlace.contains(lostPlace)) {
            score += 0.2;
        }
 
        String[] lostWords = lost.getName().toLowerCase().trim().split("\\s+");
        String[] foundWords = found.getName().toLowerCase().trim().split("\\s+");
        int common = countCommonWords(lostWords, foundWords, 0);
        int total = Math.max(lostWords.length, foundWords.length);
 
        score += 0.4 * ((double) common / total);
        return score;
    }
 
    private int countCommonWords(String[] a, String[] b, int index) {
        if (index == a.length) {
            return 0;
        }
        int thisWord = contains(b, a[index]) ? 1 : 0;
        return thisWord + countCommonWords(a, b, index + 1);
    }
 
    private boolean contains(String[] words, String target) {
        for (String w : words) {
            if (w.equals(target)) {
                return true;
            }
        }
        return false;
    }
}
