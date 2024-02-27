package dataAccess;

import model.AuthData;

public interface AuthDao {
    public void createAuth(AuthData data);
    public AuthData getAuth(AuthData data);
    public void deleteAuth(AuthData data);
    public void clear();
}
