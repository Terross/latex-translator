package ru.leti.textranslate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.leti.textranslate.domain.Language;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

import static java.nio.charset.StandardCharsets.UTF_8;
import static ru.leti.textranslate.domain.UnicodeSymbol.*;

@Slf4j
@Service
public class TranslationService {

    @Value("${translate.google.url}")
    private String googleUrl;

    private static final int GOOGLE_MAX_REQUEST_SIZE = 1000;

    public String translateTextBatch(String text, Language source, Language target) {

        StringBuilder response = new StringBuilder();
        String urlStr = googleUrl +
                "?q=" + URLEncoder.encode(text, UTF_8) +
                "&target=" + target.getValue() +
                "&source=" + source.getValue();

        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return response.toString();
    }

    public String translateText(String text, Language source, Language target) {
        String[] batches = text.split("\\.");
        StringBuilder result = new StringBuilder();
        AtomicInteger i = new AtomicInteger(1);

        var preparedBatches = prepareBatches(batches);
        preparedBatches.stream()
                .map(batch ->  batch.replace("\n", NEW_LINE.getValue()))
                .map(batch -> {
                    log.info("%f percent".formatted((double) i.getAndIncrement() /preparedBatches.size() * 100));
                    return translateTextBatch(batch, source, target);
                })
                .map(this::replaceUnicode)
                .forEach(result::append);

        return result.toString();
    }

    private String replaceUnicode(String string) {
        return string
                .replace(LESS.getValue(), "<")
                .replace(MORE.getValue(), ">")
                .replace(NEW_LINE.getValue(), "\n")
                .replace(NEW_LINE_GOOGLE.getValue(), "\n")
                .replace(APOSTROPHE.getValue(), "'");
    }

    private List<String> prepareBatches(String[] batches) {
        List<String> result = new ArrayList<>();
        StringBuilder buf = new StringBuilder();
        for (String batch:
             batches) {
            if ((buf.length() + batch.length()) > GOOGLE_MAX_REQUEST_SIZE) {
                result.add(buf.toString());
                buf = new StringBuilder(batch);
            } else {
                buf.append(batch).append(".");
            }
        }
        result.add(buf.toString());
        return result;
    }
}
