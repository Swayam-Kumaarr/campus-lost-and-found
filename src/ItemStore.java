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
 
    public Item find(int id) {
        for (int i = 0; i < count; i++) {
            if (items[i].getId() == id) {
                return items[i];
            }
        }
        throw new ItemNotFoundException(id);
    }
 
    public Item[] find(String keyword) {
        String k = keyword.toLowerCase();
        Item[] temp = new Item[count];
        int n = 0;
        for (int i = 0; i < count; i++) {
            Item it = items[i];
            if (it.getName().toLowerCase().contains(k)
                    || it.getCategory().toLowerCase().contains(k)
                    || it.getLocation().toLowerCase().contains(k)) {
                temp[n] = it;
                n++;
            }
        }
        Item[] result = new Item[n];
        for (int i = 0; i < n; i++) {
            result[i] = temp[i];
        }
        return result;
    }
 
    public Item removeItem(int id) {
        for (int i = 0; i < count; i++) {
            if (items[i].getId() == id) {
                Item removed = items[i];
                for (int j = i; j < count - 1; j++) {
                    items[j] = items[j + 1];
                }
                items[count - 1] = null;
                count--;
                return removed;
            }
        }
        throw new ItemNotFoundException(id);
    }
 
    public Item[] getAll() {
        Item[] result = new Item[count];
        for (int i = 0; i < count; i++) {
            result[i] = items[i];
        }
        return result;
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
