package com.example.rectangle.dto;

/**
 * Output PA (Perimeter & Area) object containing calculated values.
 */
public class PAResponseDto {

    private double perimeter;
    private double area;

    public PAResponseDto() {
    }

    public PAResponseDto(double perimeter, double area) {
        this.perimeter = perimeter;
        this.area = area;
    }

    public double getPerimeter() {
        return perimeter;
    }

    public void setPerimeter(double perimeter) {
        this.perimeter = perimeter;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }
}
