public class ItemNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public ItemNotFoundException(int id) {
        super("No item with ID " + id);
    }
}
