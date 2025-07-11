import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultaApi {
    TipoDeMoneda buscarValor (String PrimerValor) {
        URI direccion = URI.create(" https://v6.exchangerate-api.com/v6/316039ec0c2c88351eb1bd7b/latest/" + PrimerValor +"/");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direccion)
                .build();

        try {
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return new Gson().fromJson(response.body(), TipoDeMoneda.class);
        } catch (Exception e) {
            throw new RuntimeException("Moneda no encontrada");
        }

    }
}
