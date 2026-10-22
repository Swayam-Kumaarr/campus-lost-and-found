public interface Matcher {
 
    double MIN_SCORE = 0.5;
 
    double score(LostItem lost, FoundItem found);
}
