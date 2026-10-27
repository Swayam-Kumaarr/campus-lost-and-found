public class FoundItem extends Item {

    protected String handedTo;

    public FoundItem(String name, String category, String location, String reporter, String contact, String handedTo) {
        super(name, category, location, reporter, contact);
        this.handedTo = handedTo;
    }

    public String getHandedTo() { return handedTo; }

    @Override
    public String getType() { return "FOUND"; }

    @Override
    public String getExtraInfo() { return "Kept at: " + handedTo; }

    @Override
    public String toFileLine() { return "FOUND|" + commonFields() + "|" + handedTo; }
}
