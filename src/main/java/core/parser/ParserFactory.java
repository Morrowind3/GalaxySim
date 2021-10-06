package core.parser;

import core.Factory;

public class ParserFactory implements Factory<Parser> {
    @Override
    public Parser create(String key) {
        return switch (key) {
            case "csv" -> new CsvParser();
            case "xml" -> new XmlParser();
            default -> throw new IllegalArgumentException();
        };
    }
}
