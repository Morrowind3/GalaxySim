package core.loader;

import core.FactoryInterface;
import core.exceptions.InvalidDataException;
import core.parser.Parser;
import core.parser.ParserFactory;

import java.io.File;
import java.util.List;
import java.util.Map;

public class LocalLoader implements Loader {

    private static final FactoryInterface<Parser> parserFactory = new ParserFactory();


    @Override
    public List<Map<String, ?>> loadSimData(String dataUrl) throws InvalidDataException {
        Parser parser;

        File file = new File(dataUrl);
        if (file.isFile()) {
            String extension = file.getName();
            extension = extension.substring(extension.indexOf('.')+1);
            parser = parserFactory.create(extension);
            return parser.parseData(file);
        }
        throw new InvalidDataException();
    }
}
