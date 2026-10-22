public class ItemStore {
 
    private final Item[] items;
    private int count;
 
    public ItemStore(int capacity) {
        items = new Item[capacity];
        count = 0;
    }
 
    public boolean addItem(Item item) {
        if (count == items.length) {
            return false;
        }
        items[count] = item;
        count++;
        return true;
    }
 
    public int size() {
        return count;
    }
 
    public void displayAll() {
        if (count == 0) {
            System.out.println("No items reported yet.");
            return;
        }
        for (int i = 0; i < count; i++) {
            System.out.println(items[i]);
        }
    }
 
    public Item findById(int id) {
        for (int i = 0; i < count; i++) {
            if (items[i].getId() == id) {
                return items[i];
            }
        }
        return null;
    }
 
    public LostItem[] getLostItems() {
        LostItem[] temp = new LostItem[count];
        int n = 0;
        for (int i = 0; i < count; i++) {
            if (items[i] instanceof LostItem) {
                temp[n] = (LostItem) items[i];
                n++;
            }
        }
        LostItem[] result = new LostItem[n];
        for (int i = 0; i < n; i++) {
            result[i] = temp[i];
        }
        return result;
    }
 
    public FoundItem[] getFoundItems() {
        FoundItem[] temp = new FoundItem[count];
        int n = 0;
        for (int i = 0; i < count; i++) {
            if (items[i] instanceof FoundItem) {
                temp[n] = (FoundItem) items[i];
                n++;
            }
        }
        FoundItem[] result = new FoundItem[n];
        for (int i = 0; i < n; i++) {
            result[i] = temp[i];
        }
        return result;
    }
}
