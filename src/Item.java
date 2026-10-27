public abstract class Item {

    private static int nextId = 1;

    private final int id;
    protected String name;
    protected String category;
    protected String location;
    protected String reporter;
    protected String contact;

    public Item(String name, String category, String location, String reporter, String contact) {
        this.id = nextId++;
        this.name = name;
        this.category = category;
        this.location = location;
        this.reporter = reporter;
        this.contact = contact;
    }

    public final int getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getLocation() { return location; }

    public abstract String getType();
    public abstract String getExtraInfo();
    public abstract String toFileLine();

    protected String commonFields() {
        return name + "|" + category + "|" + location + "|" + reporter + "|" + contact;
    }

    @Override
    public String toString() {
        return "#" + id + " [" + getType() + "] " + name + " | " + category + " | " + location
                + " | by " + reporter + " (" + contact + ") | " + getExtraInfo();
    }
}
