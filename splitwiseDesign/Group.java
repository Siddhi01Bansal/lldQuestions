import java.util.ArrayList;
import java.util.List;

public class Group {
    private String id;
    private String name;
    private List<String>members;
    private List<Expense>expenses;

    public Group(String id, String name ){
        this.id = id;
        this.name = name;
        this.expenses= new ArrayList<>();
        this.members= new ArrayList<>();
    }

    public String getId(){ return this.id; }

    public String getName(){ return this.name; }

    public List<String> getMembers(){ return this.members; }

    public List<Expense> getExpenses(){ return this.expenses; }
}
