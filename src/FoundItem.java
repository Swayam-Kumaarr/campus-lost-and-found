public class FoundItem extends Item {
 
    protected String handedTo;
 
    public FoundItem(String name, String category, String location, Student reporter, String handedTo) {
        super(name, category, location, reporter);
        this.handedTo = handedTo;
    }
 
    public FoundItem(int id, String name, String category, String location, Student reporter, String handedTo) {
        super(id, name, category, location, reporter);
        this.handedTo = handedTo;
    }
 
    public String getHandedTo() { return handedTo; }
 
    @Override
    public String getType() {
        return "FOUND";
    }
 
    @Override
    public String getExtraInfo() {
        return "Kept at: " + handedTo;
    }
}
