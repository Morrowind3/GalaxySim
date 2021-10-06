package core;

public interface Factory<T> {
    T create(String key);
}
