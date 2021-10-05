package core;

public interface Destructable {
    void prepareForDestruction();
    boolean shouldDestroy();
}
