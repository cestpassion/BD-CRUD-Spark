package app;

import static spark.Spark.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import service.MusicService;

public class Principal {
    private static MusicService musicService = new MusicService();

    public static void main(String[] args) {
        port(4567); 

        get("/", (request, response) -> {
            response.type("text/html"); 
            return renderHTMLForm();
        });

        post("/music", (request, response) -> musicService.addMusic(request, response));
        post("/music/get", (request, response) -> musicService.getMusic(request, response));
        post("/music/update", (request, response) -> musicService.updateMusic(request, response));
        post("/music/delete", (request, response) -> musicService.removeMusic(request, response));
        post("/music/all", (request, response) -> musicService.getAllMusic(request, response));
    }

    private static String renderHTMLForm() {
        try {
            return new String(Files.readAllBytes(Paths.get("src/main/resources/index.html")));
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao carregar o index."; 
        }
    }
}
