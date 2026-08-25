package com.example.rectangle.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Input DTO representing Rectangle object with length and breadth.
 */
public class RectangleRequestDto {

    @NotNull(message = "Length is required")
    @Min(value = 0, message = "Length cannot be negative")
    private Double length;

    @NotNull(message = "Breadth is required")
    @Min(value = 0, message = "Breadth cannot be negative")
    private Double breadth;

    public RectangleRequestDto() {
    }

    public RectangleRequestDto(Double length, Double breadth) {
        this.length = length;
        this.breadth = breadth;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getBreadth() {
        return breadth;
    }

    public void setBreadth(Double breadth) {
        this.breadth = breadth;
    }
}
