package serviceTests;

import dataAccess.interfaces.AuthDao;
import dataAccess.interfaces.GameDao;
import dataAccess.memory.MemoryAuthDao;
import dataAccess.memory.MemoryGameDao;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.*;
import request.ListGamesRequest;
import service.ListGamesService;

import java.util.HashSet;
import java.util.Set;

public class ListGamesServiceTests {
    @BeforeAll
    public static void setup() {
        GameDao gameDao = MemoryGameDao.getInstance();
        gameDao.createGame(new GameData(null, null,null, null, null));
        AuthDao authDao = MemoryAuthDao.getInstance();
        authDao.createAuth(new AuthData("auth", "user"));
    }

    @Test
    public void testListGamesPositive() {
        ListGamesService listGamesService = ListGamesService.getInstance();
        ListGamesRequest listGamesRequest = new ListGamesRequest("auth");

        Set<GameData> set = new HashSet<>();
        set.add(new GameData(null, null,null, null, null));

        try {
            Assertions.assertEquals(set, listGamesService.listGames(listGamesRequest).games());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void testListGamesNegative() {
        ListGamesService listGamesService = ListGamesService.getInstance();
        ListGamesRequest listGamesRequest = new ListGamesRequest("notAuth");

        try {
            Assertions.assertNull(listGamesService.listGames(listGamesRequest).games());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
