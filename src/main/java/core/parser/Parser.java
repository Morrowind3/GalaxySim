package core.parser;

import java.io.File;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;

public interface Parser {
    List<Map<String, ?>> parseData(File file);
}
