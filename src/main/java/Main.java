package main.java;

import model.ExchangeRateData;
import service.CurrencyService;

import java.util.*;

public class Main {
    private static final List<String> conversionHistory = new ArrayList<>();
    private static final Map<String, List<String>> continentCurrencies = new HashMap<>();

    public static void main(String[] args) {
        CurrencyService currencyService = new CurrencyService();
        ExchangeRateData exchangeRateData = currencyService.fetchExchangeRates();

        if (exchangeRateData == null) {
            System.out.println("No se pudieron obtener las tasas de cambio.");
            return;
        }

        Map<String, Double> rates = exchangeRateData.getConversionRates();
        Scanner scanner = new Scanner(System.in);

        // Inicializar las monedas por continente
        initializeCurrencyList();

        while (true) {
            showMenu();
            System.out.print("Seleccione una opción: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea que queda en el buffer

            switch (option) {
                case 1 -> convertCurrency(rates, scanner);
                case 2 -> showCurrencies(scanner);  // Mostrar monedas organizadas por continente
                case 3 -> showHistory(scanner);
                case 4 -> showInstructions(scanner);
                case 5 -> {
                    System.out.println("\n¡Gracias por usar el conversor de monedas!");
                    return;
                }
                default -> System.out.println("Opción inválida. Por favor, intente nuevamente.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n+----------------------------+");
        System.out.println("|     CONVERSOR DE MONEDAS   |");
        System.out.println("+----------------------------+");
        System.out.println("| 1. Conversión de monedas   |");
        System.out.println("| 2. Ver listado de monedas  |");
        System.out.println("| 3. Historial de conversiones|");
        System.out.println("| 4. Instrucciones           |");
        System.out.println("| 5. Salir                   |");
        System.out.println("+----------------------------+");
    }

    private static void convertCurrency(Map<String, Double> rates, Scanner scanner) {
        while (true) {
            System.out.println("\n+-------------------------------+");
            System.out.println("|   === Conversión de Monedas === |");
            System.out.println("+-------------------------------+");
            System.out.println("| Presione 'M' para regresar al menú principal.");
            System.out.print("| Ingrese la moneda de origen (por ejemplo, USD): ");
            String fromCurrency = scanner.nextLine().toUpperCase();
            if (fromCurrency.equals("M")) return;

            System.out.print("| Ingrese la moneda de destino (por ejemplo, EUR): ");
            String toCurrency = scanner.nextLine().toUpperCase();
            if (toCurrency.equals("M")) return;

            System.out.print("| Ingrese la cantidad a convertir: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("M")) return;

            double amount;
            try {
                amount = Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("| Cantidad inválida. Intente nuevamente.");
                continue;
            }

            if (!rates.containsKey(fromCurrency) || !rates.containsKey(toCurrency)) {
                System.out.println("| Una o ambas monedas no son válidas. Intente nuevamente.");
                continue;
            }

            double fromRate = rates.get(fromCurrency);
            double toRate = rates.get(toCurrency);

            double convertedAmount = (amount / fromRate) * toRate;

            System.out.printf("| %.2f %s equivalen a %.2f %s%n", amount, fromCurrency, convertedAmount, toCurrency);

            String record = String.format("%.2f %s -> %.2f %s", amount, fromCurrency, convertedAmount, toCurrency);
            conversionHistory.add(record);

            System.out.println("\n| Desea realizar una nueva conversión? (S/N)");
            String userChoice = scanner.nextLine().toUpperCase();
            if (userChoice.equals("N")) {
                return; // Regresar al menú principal
            }
        }
    }

    private static void showHistory(Scanner scanner) {
        System.out.println("\n+------------------------------------+");
        System.out.println("|   === Historial de Conversiones === |");
        System.out.println("+------------------------------------+");
        if (conversionHistory.isEmpty()) {
            System.out.println("| No hay conversiones anteriores.");
        } else {
            for (String record : conversionHistory) {
                System.out.println("| " + record);
            }
        }
        System.out.println("+------------------------------------+");
        System.out.println("\n| Presione 'M' para regresar al menú.");
        scanner.nextLine(); // Esperar que el usuario presione enter
    }

    private static void showInstructions(Scanner scanner) {
        System.out.println("\n+-------------------------------+");
        System.out.println("|        === Instrucciones ===    |");
        System.out.println("+-------------------------------+");
        System.out.println("| 1. Selecciona 'Conversión de monedas' para realizar una conversión.");
        System.out.println("| 2. Ingrese las monedas de origen y destino, y la cantidad que desea convertir.");
        System.out.println("| 3. Para ver el historial de conversiones, elija 'Historial de conversiones'.");
        System.out.println("| 4. Para ver las monedas disponibles, elija 'Ver listado de monedas disponibles'.");
        System.out.println("| 5. Puede salir en cualquier momento seleccionando la opción 'Salir'.");
        System.out.println("+-------------------------------+");
        System.out.println("\n| Presione 'M' para regresar al menú.");
        scanner.nextLine(); // Esperar que el usuario presione enter
    }

    private static void initializeCurrencyList() {
        // Inicializando las monedas por continente
        continentCurrencies.put("América", Arrays.asList(
                "USD - Estados Unidos, El Salvador, Panamá, Ecuador, Islas Turcas y Caicos, Islas Vírgenes de EE.UU., Puerto Rico, Otros países del Caribe y América Central",
                "ARS - Argentina",
                "BBD - Barbados",
                "BOB - Bolivia",
                "BRL - Brasil",
                "BSD - Bahamas",
                "BZD - Belice",
                "CAD - Canadá",
                "COP - Colombia",
                "CRC - Costa Rica",
                "CUP - Cuba",
                "CVE - Cabo Verde",
                "DOP - República Dominicana",
                "EGP - Egipto",
                "GTQ - Guatemala",
                "GYD - Guyana",
                "HNL - Honduras",
                "MXN - México",
                "NIO - Nicaragua",
                "PAB - Panamá",
                "PEN - Perú",
                "PHP - Filipinas",
                "PYG - Paraguay",
                "SRD - Surinam",
                "UYU - Uruguay"
        ));

        continentCurrencies.put("Europa", Arrays.asList(
                "ALL - Albania",
                "AMD - Armenia",
                "BAM - Bosnia y Herzegovina",
                "BGN - Bulgaria",
                "BHD - Baréin",
                "CHF - Suiza",
                "CLP - Chile",
                "CNY - China",
                "DKK - Dinamarca",
                "EUR - Francia, Alemania, Italia, España, Portugal, Irlanda, Grecia, Países Bajos, Bélgica, Luxemburgo, Austria, Finlandia, Eslovaquia, Estonia, Letonia, Lituania, Malta, Chipre, Eslovenia",
                "GBP - Reino Unido, Gibraltar",
                "GEL - Georgia",
                "HRK - Croacia",
                "HUF - Hungría",
                "ISK - Islandia",
                "RON - Rumanía",
                "RSD - Serbia",
                "RUB - Rusia",
                "SEK - Suecia",
                "TRY - Turquía",
                "UAH - Ucrania"
        ));

        continentCurrencies.put("África", Arrays.asList(
                "AOA - Angola",
                "BIF - Burundi",
                "CDF - República Democrática del Congo",
                "DJF - Yibuti",
                "DZD - Argelia",
                "ERN - Eritrea",
                "ETB - Etiopía",
                "GHS - Ghana",
                "GNF - Guinea",
                "KMF - Comoras",
                "KES - Kenia",
                "KGS - Kirguistán",
                "KWD - Kuwait",
                "LSL - Lesoto",
                "LYD - Libia",
                "MGA - Madagascar",
                "MWK - Malaui",
                "MZN - Mozambique",
                "NAD - Namibia",
                "NGN - Nigeria",
                "SLL - Sierra Leona",
                "SOS - Somalia",
                "TND - Túnez",
                "UGX - Uganda",
                "ZAR - Sudáfrica",
                "ZMW - Zambia",
                "ZWL - Zimbabue"
        ));

        continentCurrencies.put("Asia", Arrays.asList(
                "AFN - Afganistán",
                "BDT - Bangladés",
                "INR - India",
                "IQD - Irak",
                "IRR - Irán",
                "JOD - Jordania",
                "JPY - Japón",
                "KWD - Kuwait",
                "LKR - Sri Lanka",
                "MYR - Malasia",
                "NPR - Nepal",
                "PKR - Pakistán",
                "PHP - Filipinas",
                "QAR - Catar",
                "RUB - Rusia",
                "SAR - Arabia Saudita",
                "SGD - Singapur",
                "THB - Tailandia",
                "TJS - Tayikistán",
                "TRY - Turquía",
                "TWD - Taiwán",
                "UZS - Uzbekistán",
                "VND - Vietnam",
                "YER - Yemen"
        ));
    }

    private static void showCurrencies(Scanner scanner) {
        System.out.println("\n+-----------------------------+");
        System.out.println("| === Monedas por Continente === |");
        System.out.println("+-----------------------------+");
        for (String continent : continentCurrencies.keySet()) {
            System.out.println("\n" + continent + ":");
            for (String currency : continentCurrencies.get(continent)) {
                System.out.println("| " + currency);
            }
        }
        System.out.println("\n+-----------------------------+");
        System.out.println("\nPresione 'M' para regresar al menú.");
        scanner.nextLine(); // Esperar que el usuario presione enter
    }
}
