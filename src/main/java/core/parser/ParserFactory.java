package core.parser;

import core.FactoryInterface;
import core.SupportedFormats;
import core.loader.ApiLoader;
import core.loader.LocalLoader;

public class ParserFactory implements FactoryInterface<Parser> {
    @Override
    public Parser create(String key) {
        return switch (key) {
            case "csv" -> new CsvParser();
            case "xml" -> new XmlParser();
            default -> throw new IllegalArgumentException();
        };
    }
}
