package ouniverse.kt;

public class Battery {

    private int mah;

    public Battery(int mah) {

        System.out.println("Battery object created");
        this.mah = mah;
    }

    public int getMah() {
        return mah;
    }

    public void setMah(int mah) {
        this.mah = mah;
    }
}
