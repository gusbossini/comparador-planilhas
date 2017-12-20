package br.com.maps.comparadorplanilhas.controller;

import br.com.maps.comparadorplanilhas.service.Comparador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private Comparador comparador;

    @GetMapping
    public String index() {
        return "index";
    }

    @PostMapping
    public String upload(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2, RedirectAttributes redirectAttributes) {
        List<String> msg = new ArrayList<>();

        if (file1.isEmpty() || file2.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Arquivo para importar não selecionado.");
            return "redirect:uploadStatus";
        }

        try {
            byte[] bytes1 = file1.getBytes();
            String file1FullName = uploadPath + file1.getOriginalFilename();
            Path path1 = Paths.get(file1FullName);
            Files.write(path1, bytes1);

            byte[] bytes2 = file2.getBytes();
            String file2FullName = uploadPath + file2.getOriginalFilename();
            Path path2 = Paths.get(file2FullName);
            Files.write(path2, bytes2);

            msg.add("Arquivos importados com sucesso.");

            // executa comparação e retorna msg de sucesso ou erro.
            msg.addAll(comparador.compare(file1FullName, file2FullName));

            redirectAttributes.addFlashAttribute("message", msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }
}