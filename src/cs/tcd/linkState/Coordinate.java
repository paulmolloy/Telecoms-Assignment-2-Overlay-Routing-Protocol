package cs.tcd.linkState;

/**
 * Created by root on 11/01/17.
 */
public class Coordinate {

    private double x, y;

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDistanceFrom(Coordinate coord) {
        return Math.sqrt(Math.pow(coord.getX() - x, 2) + Math.pow(coord.getY() - y, 2));
    }

}
