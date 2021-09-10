package core.loader;

import core.AbstractFactoryInterface;
import core.parser.Parser;
import core.parser.ParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

public class LocalLoader implements Loader {

    private static final AbstractFactoryInterface<Parser> parserFactory = new ParserFactory();


    @Override
    public List<Map<String, ?>> loadSimData() {
        Parser parser;

        File file = new File("./././res/planetsExtended.csv"); //TODO: Selectable file;
        if (file.isFile()) {
            String extension = file.getName();
            extension = extension.substring(extension.indexOf('.')+1);
            parser = parserFactory.create(extension);

            return parser.parseData(file);
        }


        return null;
    }
}
