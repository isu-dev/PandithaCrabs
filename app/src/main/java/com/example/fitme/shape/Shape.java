package com.example.fitme.shape;

public class Shape {
    private Double bust;
    private Double waist;
    private Double hip;
    private int shape;

    Shape(Double bust, Double waist, Double hip, int shape) {
        this.bust = bust;
        this.waist = waist;
        this.hip = hip;
        this.shape = shape;
    }

    public Double getBustSize() {
        return this.bust;
    }

    public Double getWaistSize() {
        return this.waist;
    }

    public Double getHipSize() {
        return this.hip;
    }

    public int getShape() {
        return this.shape;
    }
}
