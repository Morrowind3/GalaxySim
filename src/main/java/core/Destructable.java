package core;

public interface Destructable {
    void prepareForDestruction(boolean destructive);
    boolean shouldDestroy();
}
