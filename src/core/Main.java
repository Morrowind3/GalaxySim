package core;

import core.loader.Loader;
import core.loader.LoaderFactory;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class Main {

    private static final AbstractFactoryInterface<Loader> loaderFactory = new LoaderFactory();


    public static void main(String args[]){

        Loader loader = loaderFactory.create("local"); //todo: Choose between local and internet
        //todo: selectable file

        List<Map<String, ?>> simData = loader.loadSimData();



        Simulation sim = new Simulation();


    }


}
