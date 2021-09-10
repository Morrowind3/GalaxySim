package core;

import java.util.HashMap;
import java.util.function.Function;

public interface AbstractFactoryInterface<T> {

    T create(String key);

}