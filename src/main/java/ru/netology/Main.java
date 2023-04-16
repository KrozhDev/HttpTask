package ru.netology;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jdk.jfr.ContentType;
import netscape.javascript.JSObject;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class Main {

    public static String REMOTE_SERVICE_URL = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
    public static String APPLICATION_JSON = "application/json";
    public static void main(String[] args) throws IOException, RuntimeException {

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();

        //создание объекта запроса с производьными заголвками
        HttpGet request = new HttpGet(REMOTE_SERVICE_URL);
//
//        request.setHeader(HttpHeaders.ACCEPT, APPLICATION_JSON);
        //отправка запроса
        CloseableHttpResponse response = httpClient.execute(request);
        //вывод полученных заголовков
        Arrays.stream(response.getAllHeaders()).forEach(System.out::println);
        //чтение тела ответа
        System.out.println("________________________________");
//        String body = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
//        System.out.println(body);

        ObjectMapper mapper = new ObjectMapper();
        List<CatsFacts> facts = mapper.readValue(response.getEntity().getContent(),
                new TypeReference<List<CatsFacts>>(){});

        facts.stream().filter(x -> x.getUpvotes() > 0).forEach(System.out::println);

    }


}