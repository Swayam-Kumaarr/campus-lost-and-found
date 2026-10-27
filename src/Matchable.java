public interface Matchable {

    double MIN_SCORE = 0.5;

    double matchScore(FoundItem found);
}
