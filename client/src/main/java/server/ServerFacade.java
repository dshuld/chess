package server;

import result.*;
import request.*;

public class ServerFacade {
    private String authToken;
    private HTTPCommunicator http;
    private String urlString;

    public ServerFacade(int port) {
        this.http = HTTPCommunicator.getInstance();
        this.urlString = "http://localhost:" + port;
    }

    public void setAuthToken(String auth) {
        authToken = auth;
    }

    public String getAuthToken() {
        return authToken;
    }

    public Result clear() {
        try {
            return http.makeRequest("DELETE", urlString + "/db", Result.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GameResult createGame(CreateGameRequest request) {
        try {
            return http.makeRequest("POST", urlString + "/game", GameResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Result joinGame(JoinGameRequest request) {
        try {
            return http.makeRequest("PUT", urlString + "/game", Result.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GameListResult listGames(ListGamesRequest request) {
        try {
            return http.makeRequest("GET", urlString + "/game", GameListResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public AuthResult login(LoginRequest request) {
        try {
            return http.makeRequest("POST", urlString + "/session", AuthResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Result logout(LogoutRequest request) {
        try {
            return http.makeRequest("DELETE", urlString + "/session", Result.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public AuthResult register(RegisterRequest request) {
        try {
            return http.makeRequest("POST", urlString + "/user", AuthResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
