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
        musicDAO.connect(); // Conectar no início
    }
    
    public Object add(Request request, Response response) {
        String name = request.queryParams("name");
        String artist = request.queryParams("artist");
        String gender = request.queryParams("gender");

        Music music = new Music(name, artist, gender); // Criação da música com os dados

        if (musicDAO.insertMusic(music)) {
            response.status(201);
            return "<html><body><div class='message'>Música cadastrada com sucesso!</div></body></html>";
        } else {
            response.status(500);
            return "<html><body><div class='error'>Erro ao cadastrar música.</div></body></html>";
        }
    }


    public Object get(Request request, Response response) {
        int code = Integer.parseInt(request.queryParams("code"));
        Music music = musicDAO.getMusicByCode(code);
        if (music != null) {
            return music.toString();
        } else {
            response.status(404);
            return "<html><body><div class='error'>Música não encontrada.</div></body></html>";
        }
    }

    public Object update(Request request, Response response) {
        int code = Integer.parseInt(request.queryParams("code"));
        String name = request.queryParams("name");
        String artist = request.queryParams("artist");
        String gender = request.queryParams("gender");

        Music music = new Music(code, name, artist, gender);
        if (musicDAO.updateMusic(music)) {
            return "<html><body><div class='message'>Música atualizada com sucesso!</div></body></html>";
        } else {
            response.status(500);
            return "<html><body><div class='error'>Erro ao atualizar música.</div></body></html>";
        }
    }

    public Object remove(Request request, Response response) {
        int code = Integer.parseInt(request.queryParams("code"));
        if (musicDAO.deleteMusic(code)) {
            return "<html><body><div class='message'>Música deletada com sucesso!</div></body></html>";
        } else {
            response.status(500);
            return "<html><body><div class='error'>Erro ao deletar música.</div></body></html>";
        }
    }

    public Object getAll(Request request, Response response) {
        List<Music> musicList = musicDAO.getMusic();
        if (!musicList.isEmpty()) {
            StringBuilder responseHTML = new StringBuilder("<html><body><h2>Músicas Cadastradas</h2><ul>");
            for (Music music : musicList) {
                responseHTML.append("<li>").append(music.toString()).append("</li>");
            }
            responseHTML.append("</ul></body></html>");
            return responseHTML.toString();
        } else {
            return "<html><body><div class='error'>Nenhuma música cadastrada.</div></body></html>";
        }
    }
}
