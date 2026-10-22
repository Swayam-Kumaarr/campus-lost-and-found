public abstract class Item {
 
    private static int nextId = 1;
 
    private final int id;
    protected String name;
    protected String category;
    protected String location;
    protected Student reporter;
 
    public Item(String name, String category, String location, Student reporter) {
        this(nextId, name, category, location, reporter);
    }
 
    public Item(int id, String name, String category, String location, Student reporter) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.location = location;
        this.reporter = reporter;
        if (id >= nextId) {
            nextId = id + 1;
        }
    }
 
    public final int getId()      { return id; }
    public String getName()       { return name; }
    public String getCategory()   { return category; }
    public String getLocation()   { return location; }
    public Student getReporter()  { return reporter; }
 
    public abstract String getType();
    public abstract String getExtraInfo();
 
    @Override
    public String toString() {
        return "#" + id + " [" + getType() + "] " + name + " | " + category + " | " + location
                + " | by " + reporter + " | " + getExtraInfo();
    }
}
