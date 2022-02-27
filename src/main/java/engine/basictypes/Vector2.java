package engine.basictypes;

import java.util.Objects;

public class Vector2 implements Cloneable, Comparable<Vector2> {
    private int x;
    private int y;

    public Vector2() {}

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 pos) {
        this.x = pos.x;
        this.y = pos.y;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vector2 pos) {
        this.x = pos.getX();
        this.y = pos.getY();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void add(Vector2 position) {
        this.x += position.x;
        this.y += position.y;
    }

    public void subtract(Vector2 position) {
        this.x -= position.x;
        this.y -= position.y;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2 vector2 = (Vector2) o;
        return x == vector2.x && y == vector2.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public int compareTo(Vector2 o) {
        if (x == o.x) {
            return Integer.compare(y,o.y);
        }
        else {
            return Integer.compare(x,o.x);
        }
    }
}
