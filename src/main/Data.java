package main;

import java.io.Serializable;

public class Data implements Serializable {
    private final double x;
    private final double y;
    private final int type;

    public Data(double x, double y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getType() {
        return type;
    }
}
