package core;

public interface FactoryInterface<T> {
    T create(String key);
}
