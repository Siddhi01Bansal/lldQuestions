import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private List<String> friends;
    private HashMap<String, Double>balance;
    private List<String> groups;

    public User(String id, String name, String email, String password){
        this.id=id;
        this.name=name;
        this.email=email;
        this.password=password;
        this.groups= new ArrayList<>();
        this.friends = new ArrayList<>();
        this.balance = new HashMap<>();
    }

    public String getId(){ return this.id; }

    public String getEmail(){ return this.email; }

    public String getName(){ return this.name; }

    public String getPassword(){ return this.password; }

    public List<String> getFriends(){ return this.friends; }

    public HashMap<String, Double> getBalance(){return this.balance; }

    public List<String> getGroups(){ return this.groups;  }

}
