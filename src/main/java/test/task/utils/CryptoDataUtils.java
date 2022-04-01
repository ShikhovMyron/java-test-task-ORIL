package test.task.utils;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;

import static java.nio.charset.StandardCharsets.UTF_8;

public class CryptoDataUtils {
    private static final Logger logger = Logger.getLogger(CryptoDataUtils.class);

    public static JSONObject getJsonFromUrl(String url) throws IOException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, UTF_8));
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
