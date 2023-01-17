package WarehouseShared;

/**
 * Position class to store the position of anything in the system.
 *
 * @author Maxim
 * @author Thimoty
 * @since 10/12/2022
 */
public class Position {
    public float x;
    public float y;

    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float distanceTo(Position other) {
        return (float) Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public String toJsonArray() {
        return "[" + x + "," + y + "]";
    }
}
