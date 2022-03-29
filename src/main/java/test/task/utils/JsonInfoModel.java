package test.task.utils;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import test.task.model.CryptocurrencyInfoModel;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class JsonInfoModel {
    static final Logger logger = Logger.getLogger(JsonInfoModel.class);

    public static CryptocurrencyInfoModel getCryptoInfoFromJsonCEX(JSONObject json) {
        String price = json.getString("lprice");
        String currency_name = json.getString("curr1");
        String value_name = json.getString("curr2");
        return new CryptocurrencyInfoModel(price, currency_name, value_name);
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
