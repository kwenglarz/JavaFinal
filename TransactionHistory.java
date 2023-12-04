/*
 ** Class Name: IFT 210
 ** Kayana Wenglarz
 ** Date Created: 09/17/2021
 ** Purpose: Final Course Project
 */

class TransactionHistory {
    // attributes (variables)
    private String ticker;      // Will store ticker of the Stock or Cash
    private String transDate;       // Date transaction occurred
    private String transType;       // Type of transaction BUY/SELL for stock, DEPOSIT/WITHDRAW for CASH
    private double qty;
    private double costBasis;       //Const basis of stock. For cash this will be 1.00

    // constructors - constructs the object and sets initial values
    public TransactionHistory() {
        ticker = "N/A";
        transDate = "N/A";
        transType = "N/A";
        qty = 0.0;
        costBasis = 0.0;
    }

    public TransactionHistory( String ticker, String transDate, String transType, double qty, double costBasis) {
        this.ticker = ticker;
        this.transDate = transDate;
        this.transType = transType;
        this.qty = qty;
        this.costBasis = costBasis;
    }

    // getters and setters for all attributes
    public String getTicker() {
        return ticker;
    }
    
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
    
    public String getTransDate() {
        return transDate;
    }
    
    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getTransType() {
        return transType;
    }
    
    public void setTransType(String transType) {
        this.transType = transType;
    }

    public double getQty() {
        return qty;
    }
    
    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getCostBasis() {
        return costBasis;
    }
    
    public void setCostBasis(Double costBasis) {
        this.costBasis = costBasis;
    }
}
