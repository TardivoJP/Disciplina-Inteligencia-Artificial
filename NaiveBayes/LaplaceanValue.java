public class LaplaceanValue {
    double numerator;
    double denominator;

    public LaplaceanValue(double numerator, double denominator){
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public double calcValue(){
        return (double) this.numerator / this.denominator;
    }
}
