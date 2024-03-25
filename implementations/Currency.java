package implementations;
/**
 * Represent a currency where it includes its symbol, purchase price, selling price and the sources for this price.
 * Also represent currency exchange operation, tracking the cost of buying and selling currencies.
 */
public class Currency {
    
    private String Symbol;
    private double purchasePrice;
    private double sellingPrice;
    private String fromSource;
    private String toSource;

    /**
     * Construct a new Currency object with symbol, purchase price, selling price, source currency and destination currency
     * @param symbol The symbol of the currency such as USD, SGD, etc
     * @param purchasePrice The price at which the currency can be purchased.
     * @param sellingPrice The price at which the currency can be sold
     * @param fromSource The source currency for the exchange
     * @param toSource The destination currency for the exchange
     */
    public Currency( String symbol, double purchasePrice, double sellingPrice, String fromSource, String toSource) {
        Symbol = symbol;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.fromSource = fromSource;
        this.toSource = toSource;
    }

    /**
     * Construct an empty Currency Object to be used as placeholder before setting specific currency details.
     */
    public Currency() {}

    /**
     * Return the symbol of the currency
     * @return The currency symbol
     */
    public String getSymbol() {
        return Symbol;
    }

     /**
     * Return the purchase price of the currency
     * @return The currency's purchase price
     */
    public double getpurchasePrice() {
        return purchasePrice;
    }

     /**
     * Return the selling price of the currency
     * @return The currency's selling price
     */
    public double getsellingPrice() {
        return sellingPrice;
    }
  
     /**
     * Return the exchange source of the currency 
     * @return The currency's exchange source
     */
    public String getFromSource() {
        return fromSource;
    }

     /**
     * Return the destination source of the currency
     * @return The currency's destination sourcec
     */
    public String getToSource() {
        return toSource;
    }

}
