package core.loader;

import core.Factory;
import core.exceptions.InvalidDataException;
import core.parser.Parser;
import core.parser.ParserFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

public class ApiLoader implements Loader {
    private static final Factory<Parser> parserFactory = new ParserFactory();


    @Override
    public List<Map<String, ?>> loadSimData(String dataUrl) throws InvalidDataException {

        URI u = URI.create(dataUrl);
        String extension = dataUrl.substring(dataUrl.lastIndexOf(".")+1).split("\\?")[0];
        try (InputStream inputStream = u.toURL().openStream()) {

            File file = new File(System.getProperty("java.io.tmpdir") + "/galaxysimulatordata." + extension );
            Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            final Parser parser = parserFactory.create(extension);
            return parser.parseData(file);

        } catch ( IOException e) {
            e.printStackTrace();
        }
        throw new InvalidDataException();
    }



}
