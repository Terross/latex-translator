package ru.leti.textranslate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.leti.textranslate.domain.Response;
import ru.leti.textranslate.service.TranslationService;

import java.io.IOException;

import static ru.leti.textranslate.domain.Language.ENGLISH;
import static ru.leti.textranslate.domain.Language.RUSSIAN;


@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class TranslateController {

    private final TranslationService translationService;


    @PostMapping("/tex-translate")
    public ResponseEntity<?> translateTexFile(@RequestParam("tex-file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(translationService.translateText(new String(file.getBytes()), RUSSIAN, ENGLISH));
    }

}
