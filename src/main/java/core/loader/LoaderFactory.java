package core.loader;

import core.Factory;

public class LoaderFactory implements Factory<Loader> {

    @Override
    public Loader create(String key) {
        return switch (key) {
            case "local" -> new LocalLoader();
            case "api" -> new ApiLoader();
            default -> throw new IllegalArgumentException();
        };
    }
}
