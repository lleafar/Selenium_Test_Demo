import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GerenciadorArquivoData {

    protected JSONObject getJsonDataObject(String jsonFileName, String dataGroup) {
        JSONParser parser = new JSONParser();
        JSONObject jsonDataObject = null;
        try {
            Object jsonFileObject = parser.parse(new FileReader(System.getProperty("user.dir") + File.separator + "src" +
                    File.separator + "test" + File.separator + "java" + File.separator + "data" + File.separator + jsonFileName));

            jsonDataObject = (JSONObject) jsonFileObject;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        assert jsonDataObject != null;
        return (JSONObject) jsonDataObject.get(dataGroup);
    }
}
