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
}
