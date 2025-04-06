import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Splitwise {
    private HashMap<String, User>users;
    private HashMap<String, HashMap<String, Double>> balances; //who owes whom
    private HashMap<String, Expense> expenses;

    public Splitwise() {
        this.balances=new HashMap<>();
        this.expenses = new HashMap<>();
        this.users = new HashMap<>();
    }

    public User loginUser(String email, String password) {
        for (User user : users.values()) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                System.out.println("User logged in Successfully");
                return user;
            }
        }
        System.out.println("No such user exists");
        return null;
    }

    public void registerUser(User user){
        users.put(user.getId(), user);
        System.out.println(user.getName()+" is successfully registered");
    }

    public String generateUUID(){
        return UUID.randomUUID().toString();
    }

    public void createGroup(String grpId, List<String> memberIds){
        Group grp = new Group(grpId, "FrndsWhoTravel");
        for (String id : memberIds) {
            grp.getMembers().add(id);
            User user=users.get(id);
            user.getGroups().add(grp.getId());
        }
        System.out.println("Group created Successfully");
    }

    public void addExpense(String groupId, Expense expense){
        expenses.put(expense.getId(),expense);
        List<String> participants = expense.getSplitBetween();
        SplitType splitType = expense.getSplitType();
        String paidBy=expense.getPaidBy();
        if(splitType == SplitType.EQUAL){
            double share = (double) expense.getAmount()/participants.size();
            for(String id: participants){
                if(!id.equals(paidBy)){
                    balances.putIfAbsent(id, new HashMap<>()); //id has to pay 
                    balances.putIfAbsent(paidBy, new HashMap<>());
                    balances.get(id).put(paidBy, balances.get(id).getOrDefault(paidBy, 0.0)+share);
                    balances.get(paidBy).put(id, balances.get(paidBy).getOrDefault(id, 0.0)-share);
                }
                expense.getSplitDetails().put(id, share);
            }
        } else if(splitType == SplitType.PERCENTAGE){
            double amount = expense.getAmount();
            HashMap<String, Double> percentMap = expense.getSplitDetails();
            for(String id: participants){
                if(!id.equals(paidBy)){
                    double percent = percentMap.getOrDefault(id, 0.0);
                    double share = (percent * amount) / 100.0;
                    balances.putIfAbsent(id, new HashMap<>()); //id has to pay 
                    balances.putIfAbsent(paidBy, new HashMap<>());
                    balances.get(id).put(paidBy, balances.get(id).getOrDefault(paidBy, 0.0)+share);
                    balances.get(paidBy).put(id, balances.get(paidBy).getOrDefault(id, 0.0)-share);
                }
            }
        }
    }

    public void printBalance(String userId){
        User user = users.get(userId);
        Boolean hasBalance = false;
        if(balances.containsKey(userId)){
            for(Map.Entry<String, Double> mpp: balances.get(userId).entrySet()){
                double amount = mpp.getValue();
                if (amount > 0.0) {
                    User owesTo = users.get(mpp.getKey());
                    System.out.println(user.getName() + " has to pay Rs- " + String.format("%.2f", amount) + " to " + owesTo.getName());
                    hasBalance = true;
                }
            }
        }
        for(Map.Entry<String, HashMap<String, Double>> mpp: balances.entrySet()){
            String otherUserId=mpp.getKey();
            if (otherUserId.equals(userId)) continue;
            HashMap<String, Double> innerMap = mpp.getValue();
            if(innerMap.containsKey(userId)){
                double amount = innerMap.get(userId);
                if(amount>0.0){
                    User owesTo = users.get(otherUserId);
                    System.out.println(owesTo.getName()+" has to pay Rs- "+String.format("%.2f", amount)+" to "+user.getName());
                    hasBalance=true;
                }
            }
        }
        if(!hasBalance){
            System.out.println(user.getName()+" has no balance.");
        }
    }

    public void settleUp(String fromUserId, String toUserId, double amount){
        if(!balances.containsKey(fromUserId) || !balances.get(fromUserId).containsKey(toUserId)){
            System.out.println("No pending balances");
            return;
        }
        double currentOwed = balances.get(fromUserId).get(toUserId);
        if(amount>currentOwed){
            System.out.println("Amount exceeds the owed balance. You only owe Rs-"+currentOwed);
            return;
        }
        balances.get(fromUserId).put(toUserId, currentOwed-amount);
        if(balances.get(fromUserId).get(toUserId)==0.0){
            balances.get(fromUserId).remove(toUserId);
        }
        if(balances.containsKey(toUserId)){
            double reverse = balances.get(toUserId).getOrDefault(fromUserId, 0.0);
            balances.get(toUserId).put(fromUserId, reverse+amount);
            if(balances.get(toUserId).get(fromUserId)==0.0){
                balances.get(toUserId).remove(fromUserId);
            }
        }
        System.out.println("Payment from "+users.get(fromUserId).getName()+" to "+users.get(toUserId).getName()+" of Rs- "+amount+"is settled.");
    }

}
