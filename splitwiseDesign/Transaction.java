public class Transaction {
    private String id;
    private Integer amount;
    private String paidBy;
    private String paidTo;
    private TransactionStatus status;

    public Transaction(String id, Integer amount, String paidBy, String paidTo, TransactionStatus status){
        this.id = id;
        this.amount = amount;
        this.paidBy = paidBy;
        this.paidTo = paidTo;
        this.status = status;
    }

    public String getId(){ return this.id; }

    public Integer getAmount(){ return this.amount; }

    public String getPaidBy(){ return this.paidBy; }

    public String getPaidTo(){ return this.paidTo; }

    public TransactionStatus getStatus(){ return this.status; }

}
