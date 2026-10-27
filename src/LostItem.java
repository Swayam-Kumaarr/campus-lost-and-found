public class LostItem extends Item implements Matchable {

    private double reward;

    public LostItem(String name, String category, String location, String reporter, String contact, double reward) {
        super(name, category, location, reporter, contact);
        this.reward = reward;
    }

    @Override
    public String getType() { return "LOST"; }

    @Override
    public String getExtraInfo() { return reward > 0 ? "Reward: Rs." + reward : "No reward"; }

    @Override
    public String toFileLine() { return "LOST|" + commonFields() + "|" + reward; }

    @Override
    public double matchScore(FoundItem found) {
        double score = 0;
        if (category.equalsIgnoreCase(found.getCategory())) {
            score += 0.4;
        }
        String here = location.toLowerCase();
        String there = found.getLocation().toLowerCase();
        if (here.contains(there) || there.contains(here)) {
            score += 0.2;
        }
        String[] myWords = name.toLowerCase().trim().split("\\s+");
        String[] foundWords = found.getName().toLowerCase().trim().split("\\s+");
        int common = countCommonWords(myWords, foundWords, 0);
        score += 0.4 * ((double) common / Math.max(myWords.length, foundWords.length));
        return score;
    }

    private int countCommonWords(String[] mine, String[] other, int index) {
        if (index == mine.length) {
            return 0;
        }
        int match = 0;
        for (String word : other) {
            if (word.equals(mine[index])) {
                match = 1;
            }
        }
        return match + countCommonWords(mine, other, index + 1);
    }
}
