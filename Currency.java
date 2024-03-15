public class Currency {
       
    private String Symbol;
    private double purchasePrice;
    private double sellingPrice;
    private String fromSource;
    private String toSource;

    public Currency( String symbol, double purchasePrice, double sellingPrice, String fromSource, String toSource) {
        Symbol = symbol;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.fromSource = fromSource;
        this.toSource = toSource;
    }

    public Currency() {}

    public String getSymbol() {
        return Symbol;
    }

    public double getpurchasePrice() {
        return purchasePrice;
    }

    public double getsellingPrice() {
        return sellingPrice;
    }
  
    public String getFromSource() {
        return fromSource;
    }

    public String getToSource() {
        return toSource;
    }
}
