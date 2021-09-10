package core.loader;

import core.AbstractFactoryInterface;

public class LoaderFactory implements AbstractFactoryInterface<Loader> {

    @Override
    public Loader create(String key) {
        return switch (key) {
            case "local" -> new LocalLoader();
            case "api" -> new ApiLoader();
            default -> throw new IllegalArgumentException();
        };
    }
}
