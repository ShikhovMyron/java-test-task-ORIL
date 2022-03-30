package test.task.utils;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import test.task.entity.CryptocurrencyInfoEntity;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class JsonInfoUtils {
    static final Logger logger = Logger.getLogger(JsonInfoUtils.class);

    public static CryptocurrencyInfoEntity getCryptoInfoFromJsonCEX(JSONObject json) {
        String price = json.getString("lprice");
        String currencyName = json.getString("curr1");
        String valueName = json.getString("curr2");
        return new CryptocurrencyInfoEntity(price, currencyName, valueName);
    }

    public static JSONObject getJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);

            logger.info("Got Json:\t" + jsonText);

            return new JSONObject(jsonText);
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
