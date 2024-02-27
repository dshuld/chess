package dataAccess;

import model.GameData;

import java.util.HashSet;
import java.util.Set;

public class MemoryGameDao implements GameDao {
    private static MemoryGameDao instance;
    private Set<GameData> game = new HashSet<>();

    private MemoryGameDao() {}

    public static MemoryGameDao getInstance() {
        if (instance == null) {
            instance = new MemoryGameDao();
        }
        return instance;
    }

    @Override
    public void createGame(GameData data) {
        game.add(data);
    }

    @Override
    public GameData getGame(GameData data) {
        for (GameData g : game) {
            if (g.gameID() == data.gameID()) {
                return g;
            }
        }
        return null;
    }

    @Override
    public Set<GameData> listGames() {
        return game;
    }

    @Override
    public void updateGame(GameData data) {
        for (GameData g : game) {
            if (g.gameID() == data.gameID()) {
                game.remove(g);
                game.add(data);
            }
        }
    }

    @Override
    public void clear() {
        game = new HashSet<>();
    }
}
