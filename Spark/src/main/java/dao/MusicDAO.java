package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Music;

public class MusicDAO {
    private Connection connect;

    public MusicDAO() {
        connect = null;
    }

    public boolean connect() {
        String url = "jdbc:postgresql://localhost:5432/exer02ti2";
        String username = "cestpassion";
        String password = "furt4d00";
        try {
            connect = DriverManager.getConnection(url, username, password);
            System.out.println("\nConexão efetuada com o PostgreSQL!");
            return true;
        } catch (SQLException e) {
            System.err.println("Conexão NÃO efetuada com o PostgreSQL -- " + e.getMessage());
            return false;
        }
    }

    public void close() {
        if (connect != null) {
            try {
                connect.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public boolean insertMusic(Music music) {
        String query = "INSERT INTO music (code, name, artist, gender) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = connect.prepareStatement(query)) {
            pst.setInt(1, music.getCode());
            pst.setString(2, music.getName());
            pst.setString(3, music.getArtist());
            pst.setString(4, music.getGender());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir música: " + e.getMessage());
            return false;
        }
    }

    public boolean updateMusic(Music music) {
        String query = "UPDATE music SET name = ?, artist = ?, gender = ? WHERE code = ?";
        try (PreparedStatement pst = connect.prepareStatement(query)) {
            pst.setString(1, music.getName());
            pst.setString(2, music.getArtist());
            pst.setString(3, music.getGender());
            pst.setInt(4, music.getCode());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar música: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteMusic(int code) {
        String query = "DELETE FROM music WHERE code = ?";
        try (PreparedStatement pst = connect.prepareStatement(query)) {
            pst.setInt(1, code);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar música: " + e.getMessage());
            return false;
        }
    }

    public Music getMusicByCode(int code) {
        String query = "SELECT * FROM music WHERE code = ?";
        try (PreparedStatement pst = connect.prepareStatement(query)) {
            pst.setInt(1, code);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Music(
                        rs.getInt("code"),
                        rs.getString("name"),
                        rs.getString("artist"),
                        rs.getString("gender")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar música: " + e.getMessage());
        }
        return null;
    }

    public List<Music> getMusic() {
        List<Music> musicList = new ArrayList<>();
        String query = "SELECT * FROM music";
        try (Statement st = connect.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                musicList.add(new Music(
                        rs.getInt("code"),
                        rs.getString("name"),
                        rs.getString("artist"),
                        rs.getString("gender")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todas as músicas: " + e.getMessage());
        }
        return musicList;
    }

    public int[] getCode() {
        List<Integer> codes = new ArrayList<>();
        String query = "SELECT code FROM music";
        try (Statement st = connect.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                codes.add(rs.getInt("code"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar códigos: " + e.getMessage());
        }
        return codes.stream().mapToInt(i -> i).toArray();
    }
}
