public class LostItem extends Item {
 
    private double reward;
 
    public LostItem(String name, String category, String location, Student reporter, double reward) {
        super(name, category, location, reporter);
        this.reward = reward;
    }
 
    public LostItem(int id, String name, String category, String location, Student reporter, double reward) {
        super(id, name, category, location, reporter);
        this.reward = reward;
    }
 
    public double getReward() { return reward; }
 
    @Override
    public String getType() {
        return "LOST";
    }
 
    @Override
    public String getExtraInfo() {
        if (reward > 0) {
            return "Reward: Rs." + reward;
        }
        return "No reward";
    }
}


