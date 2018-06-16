package com.fireslug.gameutils;

import java.util.Objects;

public class Coord2 {

    public int x;
    public int y;

    public Coord2(int i, int j) {

        this.x = i;
        this.y = j;

    }

    @Override
    public boolean equals(Object obj) {

        if(obj instanceof Coord2) {
            Coord2 other = (Coord2)obj;
            return (this.x == other.x) && (this.y == other.y);
        }
        else
            return false;

    }

    @Override
    public int hashCode() {

        return Objects.hash(this.x, this.y);

    }
}
