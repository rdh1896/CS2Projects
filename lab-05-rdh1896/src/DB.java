import java.util.Collection;

/**
 * Interface used by Backend, CourseDB, and UserDB.
 *
 * @author Russell Harvey (rdh1896@rit.edu)
 * @param <K>
 * @param <V>
 */

public interface DB<K,V> {

    /**
     * Add a value to the database.
     * @param value
     * @return <V> Generic </V>
     */
    public V addValue(V value);

    /**
     * Gets all values in the database.
     * @return Collection<V> Generic </V>
     */
    public Collection<V> getAllValues();

    /**
     * Get the value for a corresponding key.
     * @param key
     * @return <V> Generic </V>
     */
    public V getValue(K key);

    /**
     * Indicates if the key is in the database.
     * @param key
     * @return boolean
     */
    public boolean hasKey(K key);

}
