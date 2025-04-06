import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SplitwiseClient {
    public static void main(String[] args) {
        Splitwise app = new Splitwise();

        // Register Users
        User u1 = new User("1", "Alice", "alice@gmail.com", "pass123");
        User u2 = new User("2", "Bob", "bob@gmail.com", "pass123");
        User u3 = new User("3", "Charlie", "charlie@gmail.com", "pass123");

        app.registerUser(u1);
        app.registerUser(u2);
        app.registerUser(u3);

        List<String> groupMembers = Arrays.asList(u1.getId(), u2.getId(), u3.getId());
        app.createGroup("group1", groupMembers);

        Expense e1 = new Expense(app.generateUUID(), 300, u1.getId(), SplitType.EQUAL, groupMembers);
        app.addExpense("group1", e1);

        Expense e2 = new Expense(app.generateUUID(), 200, u2.getId(), SplitType.PERCENTAGE, groupMembers);
        HashMap<String, Double> percentMap = new HashMap<>();
        percentMap.put(u1.getId(), 50.0); // Alice 50%
        percentMap.put(u2.getId(), 0.0);  // Bob 0%
        percentMap.put(u3.getId(), 50.0); // Charlie 50%
        e2.setSplitDetails(percentMap);
        app.addExpense("group1", e2);

        // Print Balances
        System.out.println("\n--- Balances after expenses e2---");
        app.printBalance(u1.getId());
        app.printBalance(u2.getId());
        app.printBalance(u3.getId());

        // Settle Up: Charlie pays Alice Rs. 100
        System.out.println("\n--- Settle Up ---");
        app.settleUp(u3.getId(), u1.getId(), 100);

        // Print Balances after settlement
        System.out.println("\n--- Balances after settlement ---");
        app.printBalance(u1.getId());
        app.printBalance(u3.getId());

    }
}
