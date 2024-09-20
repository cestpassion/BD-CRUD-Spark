package service;

import dao.MusicDAO;
import model.Music;
import spark.Request;
import spark.Response;

import java.util.List;

public class MusicService {
    private MusicDAO musicDAO;

    public MusicService() {
        musicDAO = new MusicDAO();
        musicDAO.connect();
    }
    
    public Object addMusic(Request request, Response response) {
        String name = request.queryParams("name");
        String artist = request.queryParams("artist");
        String gender = request.queryParams("gender");

        Music music = new Music(name, artist, gender);

        if (musicDAO.insertMusic(music)) {
            response.status(201);
            return "<html><body><div class='message'>Música cadastrada com sucesso!</div></body></html>";
        } else {
            response.status(500);
            return "<html><body><div class='error'>Não foi possível cadastrar música.</div></body></html>";
        }
    }
    
    public Object removeMusic(Request request, Response response) {
        int code = Integer.parseInt(request.queryParams("code"));
        if (musicDAO.deleteMusic(code)) {
            return "<html><body><div class='message'>Música deletada com sucesso!</div></body></html>";
        } else {
            response.status(500);
            return "<html><body><div class='error'>Erro ao deletar música.</div></body></html>";
        }
    }

    public Object updateMusic(Request request, Response response) {
        int code = Integer.parseInt(request.queryParams("code"));
        String name = request.queryParams("name");
        String artist = request.queryParams("artist");
        String gender = request.queryParams("gender");

        Music music = new Music(code, name, artist, gender);
        if (musicDAO.updateMusic(music)) {
            return "<html><body><div class='message'>Música atualizada com sucesso!</div></body></html>";
        } else {
            response.status(500);
            return "<html><body><div class='error'>Não foi possível atualizar música.</div></body></html>";
        }
    }
    
    public Object getMusic(Request request, Response response) {
        int code = Integer.parseInt(request.queryParams("code"));
        Music music = musicDAO.getMusicByCode(code);

        if (music != null) {
            // HTML para exibir detalhes da música
            return "<html>" +
                    "<head>" +
                    "<title>Detalhes da Música</title>" +
                    "<style>" +
                    "body { font-family: Arial, sans-serif; margin: 20px; background-color: rgb(14, 14, 14); color: #fff; }" +
                    ".container { background-color: rgb(36, 31, 49); box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); padding: 20px; border-radius: 8px;}" +
                    "h1 { color: rgb(38, 162, 105); text-shadow: 0px 0px 5px rgba(38, 162, 105, 0.200); }" +
                    "p { font-size: 16px; color: #555; }" +
                    ".music-detail { margin-bottom: 10px; }" +
                    ".music-detail strong { color: rgb(38, 162, 105); text-shadow: 0px 0px 5px rgba(38, 162, 105, 0.678); }" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<div class='container'>" +
                    "<h1>Detalhes da Música</h1>" +
                    "<div class='music-detail'><strong>Código:</strong> " + music.getCode() + "</div>" +
                    "<div class='music-detail'><strong>Nome:</strong> " + music.getName() + "</div>" +
                    "<div class='music-detail'><strong>Artista:</strong> " + music.getArtist() + "</div>" +
                    "<div class='music-detail'><strong>Gênero:</strong> " + music.getGender() + "</div>" +
                    "</div>" +
                    "</body>" +
                    "</html>";
        } else {
            response.status(404);
            // HTML para exibir mensagem de erro
            return "<html>" +
                    "<head>" +
                    "<title>Erro 404 - Música Não Encontrada</title>" +
                    "<style>" +
                    "body { font-family: Arial, sans-serif; margin: 20px; background-color: rgb(14, 14, 14); color: #fff; }" +
                    ".error-container { background-color: rgb(36, 31, 49); box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); padding: 20px; border-radius: 8px; text-align: center; }" +
                    "h1 { color: rgb(38, 162, 105); text-shadow: 0px 0px 5px rgba(38, 162, 105, 0.200); }" +
                    "p { font-size: 18px; color: #fff; }" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<div class='error-container'>" +
                    "<h1>404 - Música Não Encontrada</h1>" +
                    "<p>A música com o código " + code + " não foi encontrada.</p>" +
                    "</div>" +
                    "</body>" +
                    "</html>";
        }
    }


    public Object getAllMusic(Request request, Response response) {
        List<Music> musicList = musicDAO.getMusic();
        StringBuilder responseHTML = new StringBuilder();

        responseHTML.append("<html>")
                .append("<head>")
                .append("<title>Músicas Cadastradas</title>")
                .append("<style>")
                .append("body { font-family: Arial, sans-serif; margin: 20px; background-color: rgb(14, 14, 14); color: #fff; }")
                .append(".container { background-color: rgb(36, 31, 49); box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); padding: 20px; border-radius: 8px; }")
                .append("h2 { color: rgb(38, 162, 105); text-shadow: 0px 0px 5px rgba(38, 162, 105, 0.678); }")
                .append("ul { list-style-type: none; padding: 0; }")
                .append("li { margin: 10px 0; padding: 10px; border: 3px solid rgb(14, 14, 14); border-radius: 5px; }")
                .append(".error { color: #fff; font-weight: bold; }")
                .append("strong { color: rgb(38, 162, 105); text-shadow: 0px 0px 5px rgba(38, 162, 105, 0.200); }")
                .append("</style>")
                .append("</head>")
                .append("<body>");

        if (!musicList.isEmpty()) {
            responseHTML.append("<div class='container'>")
                    .append("<h2>Músicas Cadastradas</h2>")
                    .append("<ul>");
            for (Music music : musicList) {
                responseHTML.append("<li>")
                        .append("<strong>Código:</strong> ").append(music.getCode()).append("<br>")
                        .append("<strong>Nome:</strong> ").append(music.getName()).append("<br>")
                        .append("<strong>Artista:</strong> ").append(music.getArtist()).append("<br>")
                        .append("<strong>Gênero:</strong> ").append(music.getGender())
                        .append("</li>");
            }
            responseHTML.append("</ul></div>");
        } else {
            responseHTML.append("<div class='container'>")
                    .append("<h2 class='error'>Não há músicas cadastradas.</h2>")
                    .append("</div>");
        }

        responseHTML.append("</body>")
                .append("</html>");

        return responseHTML.toString();
    }

}
