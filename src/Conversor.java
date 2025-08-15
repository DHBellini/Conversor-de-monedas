import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Conversor {
    public static Moneda obtenerDaros(String api){
        Moneda moneda = null;
                try{
                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(api))
                            .GET()
                            .build();
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                    Gson gson = new Gson();

                    moneda = gson.fromJson(response.body(), Moneda.class);
                } catch (Exception e) {
                    throw new RuntimeException("Moneda no encontrada");
                }
                return moneda;
    }

    public static String convertir(String tipoDeMoneda, String monedaDeCambio, double valor){
        double resultado = 0;

        String api = "https://v6.exchangerate-api.com/v6/316039ec0c2c88351eb1bd7b/latest/" +tipoDeMoneda;
        Moneda moneda = obtenerDaros(api);
        double cambio = moneda.getConversion_rates().get(monedaDeCambio);
        resultado = valor*cambio;

        return "\nEl valor de " + valor + " [" + tipoDeMoneda + "] corresponde a un valor final de " + resultado + " [" + monedaDeCambio + "]";
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        String resultado = "";
        double valor = 0;

        while(opcion != 7){

        String Menu =
                """
                        ******************************************
                        Bienvenido al conversor de monedas
                        ******************************************
                        1) Dólar a Peso Argentino
                        2) Peso Argentino a Dólar
                        3) Dólar a Euro
                        4) Euro a Dólar
                        5) Dólar a Real Brasileño
                        6) Real Brasileño a Dólar
                        7) Salir del sistema
                        ******************************************
                        Seleccione una opción válida:
                """;

        System.out.println(Menu);
        opcion = scanner.nextInt();

        if (opcion != 7 && opcion <= 7 ){
            System.out.println("Ingrese el valor a convertir: ");
            valor = scanner.nextDouble();
            }

        switch (opcion){
            case 1:
                resultado = Conversor.convertir(TipoDeMoneda.USD.toString(), TipoDeMoneda.ARS.toString(), valor);
                break;
            case 2:
                resultado = Conversor.convertir(TipoDeMoneda.ARS.toString(), TipoDeMoneda.USD.toString(), valor);
                break;
            case 3:
                resultado = Conversor.convertir(TipoDeMoneda.USD.toString(), TipoDeMoneda.EUR.toString(), valor);
                break;
            case 4:
                resultado = Conversor.convertir(TipoDeMoneda.EUR.toString(), TipoDeMoneda.USD.toString(), valor);
                break;
            case 5:
                resultado = Conversor.convertir(TipoDeMoneda.USD.toString(), TipoDeMoneda.BRL.toString(), valor);
                break;
            case 6:
                resultado = Conversor.convertir(TipoDeMoneda.BRL.toString(), TipoDeMoneda.USD.toString(), valor);
                break;
            case 7:
                System.out.println("Salio del sistema");
                break;
            default:
                System.out.println("Opcion invalida");
                break;
        }
            if (opcion != 7 && opcion <= 7 && !resultado.isEmpty()) {
                System.out.println(resultado);
            }
        }
    }

}