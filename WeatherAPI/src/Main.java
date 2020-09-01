import java.net.MalformedURLException;

public class Main {
    public static void main(String[] args) throws Exception {
        Service s = new Service("Poland");
        String weatherJson = s.getWeather("Warsaw");
        String rate1 = s.getRateFor("USD");

        System.out.println(weatherJson);
        System.out.println(rate1);

    }
}
