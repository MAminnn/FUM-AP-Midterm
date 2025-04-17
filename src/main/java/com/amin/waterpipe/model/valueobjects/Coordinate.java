package com.amin.waterpipe.model.valueobjects;

public record Coordinate(int x, int y) {
    public Coordinate Right() {
        return new Coordinate(x + 1, y);
    }

    public Coordinate Left() {
        return new Coordinate(x - 1, y);
    }

    public Coordinate Up() {
        return new Coordinate(x, y + 1);
    }

    public Coordinate Down() {
        return new Coordinate(x, y - 1);
    }

    public boolean equals(Coordinate coordinate) {
        return (coordinate.x == this.x && coordinate.y == this.y);
    }

    public static double calculateDistance(Coordinate c1, Coordinate c2) {
        return Math.sqrt(Math.pow(c1.x - c2.x, 2) + Math.pow(c1.y - c2.y, 2));
    }
}
