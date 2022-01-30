package model;

public enum Direction {
    UP,DOWN,IDLE;

    public static char getChar(Direction direction) {
        return switch (direction) {
            case UP -> 0x2191;
            case DOWN -> 0x2193;
            case IDLE -> 0x00B7;
        };
    }
}
