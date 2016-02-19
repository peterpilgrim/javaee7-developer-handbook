package je7hb.standalone.app;

/**
 * Created by ppilgrim on 19/02/2016.
 */
public class Dealer {
    private String dealerName;

    public Dealer() {
        this(null);
    }

    public Dealer(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dealer dealer = (Dealer) o;

        return dealerName != null ? dealerName.equals(dealer.dealerName) : dealer.dealerName == null;

    }

    @Override
    public int hashCode() {
        return dealerName != null ? dealerName.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Dealer{" +
                "dealerName='" + dealerName + '\'' +
                '}';
    }
}
