package core.loader;

import core.Factory;

public class LoaderFactory implements Factory<Loader> {

    @Override
    public Loader create(String url) {
        if(url.startsWith("www.") || url.startsWith("http")){
            return new ApiLoader();
        } else {
            return new LocalLoader();
        }
    }
}
