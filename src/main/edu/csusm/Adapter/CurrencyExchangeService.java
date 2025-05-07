package edu.csusm.Adapter;

import org.json.JSONObject;
import java.io.IOException;
import org.json.JSONException;

public interface CurrencyExchangeService {
    JSONObject convertCurrency(String fromCurrency, String toCurrency, double amount) throws IOException, JSONException;
}
