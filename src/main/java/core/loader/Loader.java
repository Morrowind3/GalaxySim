package core.loader;

import core.exceptions.InvalidDataException;

import java.util.List;
import java.util.Map;

public interface Loader {

    List<Map<String, ?>> loadSimData(String dataUrl) throws InvalidDataException;

}
