import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class App {
    private final static String URI = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";

    public static void main(String[] args) throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();

        HttpGet request = new HttpGet(URI);
        CloseableHttpResponse response = httpClient.execute(request);
//        Arrays.stream(response.getAllHeaders()).forEach(System.out::println);
//
//        byte[] body = response.getEntity().getContent().readAllBytes();
//
//        for (byte b : body) {
//            System.out.print((char) b);
//        }
        ObjectMapper mapper = new ObjectMapper();
        List<Cats> cats = mapper.readValue(response.getEntity().getContent(),
                new TypeReference<>() {});

        cats.forEach(p -> System.out.println(p.getId()));

    }
}
