package edu.csusm.Adapter;

import org.json.JSONObject;
import org.json.JSONException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import io.github.cdimascio.dotenv.Dotenv;

public class CurrencyExchangeAdapter implements CurrencyExchangeService {

   
    private static final String CONVERT_URL = "https://v6.exchangerate-api.com/v6/{apiKey}/pair";

    
    private String apiKey;

    public CurrencyExchangeAdapter() {
        Dotenv dotenv = Dotenv.load(); 
        this.apiKey = dotenv.get("EXCHANGE_RATE_API_KEY"); 
        if (this.apiKey == null || this.apiKey.isEmpty()) {
            throw new IllegalArgumentException("API key is missing or invalid in the .env file");
        }
    }

    @Override
    public JSONObject convertCurrency(String fromCurrency, String toCurrency, double amount) throws IOException, JSONException {
        // Construct the URL dynamically using the API key
        String urlString = "https://v6.exchangerate-api.com/v6/" + this.apiKey + "/pair/" + fromCurrency + "/" + toCurrency + "/" + amount;

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                return new JSONObject(response.toString());
            } else {
                throw new IOException("API request failed with status: " + connection.getResponseCode());
            }
        } finally {
            if (reader != null) reader.close();
            if (connection != null) connection.disconnect();
        }
    }
}
