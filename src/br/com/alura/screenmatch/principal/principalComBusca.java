package br.com.alura.screenmatch.principal;
import br.com.alura.screenmatch.exception.erroDeConvercaoDeAno;
import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class principalComBusca {

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        var tituloBusca = "";
        List<Titulo> titulos = new ArrayList<>();

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();

        while (!tituloBusca.equalsIgnoreCase("sair")) {

            System.out.println("Digite o nome do titulo desejado:");
            tituloBusca = sc.nextLine();
            if(tituloBusca.equalsIgnoreCase("sair")){
                break;
            }else {


                String endereco = "https://www.omdbapi.com/?t=" + tituloBusca.replace(" ", "+") + "&apikey=a957fb76";

                try {

                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(endereco))
                            .build();
                    HttpResponse<String> response = client
                            .send(request, HttpResponse.BodyHandlers.ofString());
                    String json = response.body();

                    TituloOmdb meuTituloOmdb = gson.fromJson(json, TituloOmdb.class);

                    Titulo meuTitulo = new Titulo(meuTituloOmdb);
                    titulos.add(meuTitulo);

                } catch (NumberFormatException e) {
                    System.out.print("ocorreu um erro:");
                    System.out.println(e.getMessage());
                } catch (IllegalAccessError e) {
                    System.out.println("Algum erro de argumento na busca, verifique o endereço!");
                    System.out.println(e.getMessage());
                } catch (erroDeConvercaoDeAno e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        FileWriter escrita = new FileWriter("titulos.json");
        escrita.write(gson.toJson(titulos));
        escrita.close();
        System.out.println(titulos);

    }
}
