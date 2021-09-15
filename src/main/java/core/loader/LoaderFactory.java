package core.loader;

import core.FactoryInterface;

public class LoaderFactory implements FactoryInterface<Loader> {

    @Override
    public Loader create(String key) {
        return switch (key) {
            case "local" -> new LocalLoader();
            case "api" -> new ApiLoader();
            default -> throw new IllegalArgumentException();
        };
    }
}
