import java.util.Scanner;
 
public class InputHelper {
 
    private final Scanner sc;
 
    public InputHelper(Scanner sc) {
        this.sc = sc;
    }
 
    public int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String text = sc.nextLine().trim();
            try {
                Integer value = Integer.valueOf(text);
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }
 
    public double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String text = sc.nextLine().trim();
            try {
                double value = Double.parseDouble(text);
                if (value >= 0) {
                    return value;
                }
                System.out.println("Please enter 0 or more.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number, e.g. 200 or 0.");
            }
        }
    }
 
    public String readText(String prompt) throws InvalidInputException {
        System.out.print(prompt);
        String text = sc.nextLine().trim();
        if (text.isEmpty()) {
            throw new InvalidInputException("This field cannot be empty.");
        }
        if (text.contains("|")) {
            throw new InvalidInputException("The | character is not allowed (it is used in the save file).");
        }
        return text;
    }
}
