package dataAccess;

import model.AuthData;

import java.util.HashSet;
import java.util.Set;

public class MemoryAuthDao implements AuthDao {
    private static MemoryAuthDao instance;
    private Set<AuthData> auth = new HashSet<>();

    private MemoryAuthDao() {}

    public static MemoryAuthDao getInstance() {
        if (instance == null) {
            instance = new MemoryAuthDao();
        }
        return instance;
    }

    @Override
    public void createAuth(AuthData data) {
        auth.add(data);
    }

    @Override
    public AuthData getAuth(AuthData data) {
        for (AuthData a : auth) {
            if(a.username().equals(data.username())) {
                return a;
            }
        }
        return null;
    }

    @Override
    public void deleteAuth(AuthData data) {
        for (AuthData a : auth) {
            if (a.username().equals(data.username())) {
                auth.remove(a);
            }
        }
    }

    @Override
    public void clear() {
        auth = new HashSet<>();
    }
}