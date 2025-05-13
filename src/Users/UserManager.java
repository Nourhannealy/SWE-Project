package Users;

public class UserManager {

    protected boolean isValidUsername(String username)
    {
        if(username.length() < 4 || username == "")
        {
            return false;
        }
        return true;
    }

    protected boolean isValidPassword(String password)
    {
        if (password.length() < 8 || password == "")
        {
            return false;
        }
        return true;
    }
    
}
