import java.util.HashMap;
import java.util.List;

public class Expense {
    private String id;
    private Integer amount;
    private String paidBy;
    private SplitType splitType;
    private List<String> splitBetween;
    private HashMap<String, Double>splitDetails;

    public Expense(String id, Integer amount, String paidBy, SplitType splitType, List<String> splitBetween){
        this.id = id;
        this.amount = amount;
        this.paidBy = paidBy;
        this.splitType = splitType;
        this.splitBetween = splitBetween;
        this.splitDetails =  new HashMap<>();
    }

    public String getId(){ return this.id; }

    public Integer getAmount(){ return this.amount; }

    public String getPaidBy(){ return this.paidBy; }

    public SplitType getSplitType(){ return this.splitType; }

    public List<String> getSplitBetween(){ return this.splitBetween;  }

    public HashMap<String, Double> getSplitDetails(){ return this.splitDetails;}

    public void setSplitDetails(HashMap<String, Double> percentMap){
        this.splitDetails = percentMap;
    }

}