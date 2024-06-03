import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/6c67cbeb056f653af8cb0ea3/latest/COP";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingresa la cantidad de pesos colombianos a convertir: ");
        double amountInCOP = scanner.nextDouble();

        List<String> monedas = new ArrayList<>();
        monedas.add("USD - Dólar estadounidense");
        monedas.add("EUR - Euro");
        monedas.add("GBP - Libra esterlina");
        monedas.add("JPY - Yen japonés");
        monedas.add("CHF - Franco suizo");
        monedas.add("AUD - Dólar australiano");
        monedas.add("CAD - Dólar canadiense");
        monedas.add("CNY - Yuan chino");
        monedas.add("NZD - Dólar neozelandés");
        monedas.add("KRW - Won surcoreano");
        monedas.add("ARS - Peso argentino");
        monedas.add("MXN - Peso mexicano");
        monedas.add("BRL - Real brasileño");
        monedas.add("CLP - Peso chileno");
        monedas.add("PEN - Sol peruano");

        System.out.println("Elige la moneda a la que deseas convertir (Ingresa el número correspondiente): ");
        for (int i = 0; i < monedas.size(); i++) {
            System.out.println((i + 1) + ". " + monedas.get(i));
        }

        int option = scanner.nextInt();
        if (option >= 1 && option <= monedas.size()) {
            String currency = monedas.get(option - 1).split(" ")[0];
            try {
                double conversionRate = getConversionRate(currency);
                if (conversionRate != -1) {
                    double convertedAmount = amountInCOP * conversionRate;
                    System.out.printf("Resumen de conversión:\nPesos Colombianos: %.2f\n%s: %.2f\n", amountInCOP, currency, convertedAmount);
                } else {
                    System.out.println("No se pudo obtener la tasa de conversión. Por favor, intente de nuevo.");
                }
            } catch (IOException e) {
                System.out.println("Error al conectarse a la API de conversión de moneda.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Opción no válida. Por favor, elige una opción del menú.");
        }
    }

    private static double getConversionRate(String targetCurrency) throws IOException {
        URL url = new URL(API_URL);
        try (Scanner scanner = new Scanner(url.openStream())) {
            StringBuilder jsonContent = new StringBuilder();
            while (scanner.hasNextLine()) {
                jsonContent.append(scanner.nextLine());
            }
            JsonObject jsonObject = JsonParser.parseString(jsonContent.toString()).getAsJsonObject();
            JsonObject rates = jsonObject.getAsJsonObject("conversion_rates");
            return rates.get(targetCurrency).getAsDouble();
        }
    }
}
