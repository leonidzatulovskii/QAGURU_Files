package guru.qa.jsonUtils;

import java.io.*;

public class JsonToString {
    public static String readJsonData(String pactFile) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileInputStream fileInputStream = new FileInputStream(pactFile);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String string;
            while ((string = bufferedReader.readLine()) != null) {
                stringBuilder.append(string);
            }
            bufferedReader.close();
        }
        catch (IOException e) {
            e.getStackTrace();
        }
        return stringBuilder.toString();
    }
}
