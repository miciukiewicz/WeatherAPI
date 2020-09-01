import com.google.gson.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class Service {
    private String countryName;
    private final Locale[] locales = Locale.getAvailableLocales();

    public Service(String countryName) {
        this.countryName = countryName;
    }

    public String getWeather(String cityName) throws Exception {
        Map<String, String> mapOfCountriesAndCodes = new HashMap();
        mapOfCountriesAndCodes = setMapOfCountriesAndCodes(mapOfCountriesAndCodes);
        URL urlToApi = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "," + mapOfCountriesAndCodes.get(countryName) + "&appid=e2cedb685ad68fb0ca0eba314fba795d");
        String jsonFromUrl = getJson(urlToApi);
        JsonObject jsonObject = JsonParser.parseString(jsonFromUrl).getAsJsonObject();
        JsonElement jsonMainWeather = jsonObject.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("main");
        JsonElement jsonDescriptionOfWeather = jsonObject.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description");
        JsonElement jsonTemperature = jsonObject.get("main").getAsJsonObject().get("temp");

        return "Actual weather in " + cityName + ": " + jsonMainWeather + "\nDescription of weather: " + jsonDescriptionOfWeather + "\nTemperature: " + jsonTemperature + "°F";
    }

    public String getRateFor(String currencyName) throws Exception {
        Map<String, Locale> countries = new HashMap<>();
        for (int i = 0; i < locales.length; i++) {
            countries.put(locales[i].getDisplayCountry(Locale.US), locales[i]);
        }
        URL urlToApi = new URL("https://api.exchangeratesapi.io/latest?base=" + Currency.getInstance(countries.get(countryName)).getCurrencyCode() + "&symbols=" + currencyName);
        String jsonFromUrl = getJson(urlToApi);
        JsonObject jsonObject = JsonParser.parseString(jsonFromUrl).getAsJsonObject();
        JsonElement jsonRates = jsonObject.get("rates").getAsJsonObject().get(currencyName);
        return "Rates from " + Currency.getInstance(countries.get(countryName)).getCurrencyCode() + " to " + currencyName + " is " + jsonRates;
    }

    public String getJson(URL urlToGetJson) throws Exception {
        String json = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(urlToGetJson.openStream()));
        String line;
        while ((line = in.readLine()) != null) {
            json += line;
        }
        return json;
    }

    public Map setMapOfCountriesAndCodes(Map mapOfCountriesAndCodes) {
        for (Locale l : locales) {
            mapOfCountriesAndCodes.put(l.getDisplayCountry(Locale.US), l.getCountry());
        }
        return mapOfCountriesAndCodes;
    }

}
