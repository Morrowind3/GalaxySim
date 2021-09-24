package core.parser;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvParser implements Parser {

    @Override
    public List<Map<String, ?>> parseData(File file) {
        final List<Map<String, ?>> items = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            boolean firstLine = true;
            final List<String> keys = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(";");
                Map<String, Object> lineData = new HashMap<>();
                int fieldNum = 0;

                for(String field : fields ){
                    if(firstLine){
                        keys.add(field);
                    } else {
                        lineData.put(keys.get(fieldNum), guessFieldType(field));
                        fieldNum++;
                    }
                }
                
                if(firstLine){
                    firstLine = false;
                } else {
                    items.add(lineData);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }

    private Object guessFieldType(String field){
        if(field.matches("-?\\d+.?,?\\d?\\d?")){
            return Float.parseFloat(field);
        }
        if(field.matches("true|false/i")){
            return Boolean.parseBoolean(field);
        }
        return field;
    }
}
