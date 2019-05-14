import java.util.*;

/**
 * User database for SIS.
 *
 * @author Russell Harvey (rdh1896@rit.edu)
 */

public class UserDB implements DB<String, User> {

    /** Database for users in the SIS. */
    private HashMap<String, User> users;

    /**
     * Creates a new user database.
     */
    public UserDB() {
        this.users = new HashMap<>();
    }

    /**
     * Add a value to the database.
     * @param value
     * @return User
     */
    @Override
    public User addValue(User value) {
        if (users.containsKey(value.getUsername())) {
            User temp = users.get(value.getUsername());
            users.replace(value.getUsername(), value);
            return temp;
        } else {
            users.put(value.getUsername(), value);
            return null;
        }
    }

    /**
     * Gets all values in the database.
     * @return Collection<User>
     */
    @Override
    public Collection<User> getAllValues() {
        ArrayList<User> usrs = new ArrayList<>();
        Iterator<User> iter = users.values().iterator();
        while (iter.hasNext()) {
            usrs.add(iter.next());
        }
        Collections.sort(usrs);
        return usrs;
    }

    /**
     * Get the value for a corresponding key.
     * @param key
     * @return User
     */
    @Override
    public User getValue(String key) {
        return users.get(key);
    }

    /**
     * Indicates if the key is in the database.
     * @param key
     * @return boolean
     */
    @Override
    public boolean hasKey(String key) {
        return users.containsKey(key);
    }
}
