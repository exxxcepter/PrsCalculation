package distcalc;

public class LineType {
    private String name;
    private int uPMob;
    private int uPStat;
    private int uMinMob;
    private int uMinStat;
    private int aPer;
    private int aPerTunnel;
    private double aN;
    private double aNTunnel;
    private double aV;
    private int Ku;
    private int aon;
    private double atr;

    public LineType(String name, int uPMob, int uPStat, int uMinMob, int uMinStat, int aPer, int aPerTunnel, double aN, double aNTunnel, double aV, int Ku, int aon, double atr) {
        this.name = name;
        this.uPMob = uPMob;
        this.uPStat = uPStat;
        this.uMinMob = uMinMob;
        this.uMinStat = uMinStat;
        this.aPer = aPer;
        this.aPerTunnel = aPerTunnel;
        this.aN = aN;
        this.aNTunnel = aNTunnel;
        this.aV = aV;
        this.Ku = Ku;
        this.aon = aon;
        this.atr = atr;
    }

    public String getName() {
        return name;
    }

    public int getuPMob() {
        return uPMob;
    }

    public int getuMinMob() {
        return uMinMob;
    }

    public int getuPStat() {
        return uPStat;
    }

    public int getuMinStat() {
        return uMinStat;
    }

    public int getaPer() {
        return aPer;
    }

    public int getaPerTunnel() {
        return aPerTunnel;
    }

    public double getaN() {
        return aN;
    }

    public double getaNTunnel() {
        return aNTunnel;
    }

    public double getaV() {
        return aV;
    }

    public double getAtr() { return atr;
    }

    public int getAon() {
        return aon;
    }

    public int getKu() {
        return Ku;
    }

    @Override
    public String toString(){
        return this.getName();
    }
}
