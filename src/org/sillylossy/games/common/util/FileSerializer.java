package org.sillylossy.games.common.util;

import org.sillylossy.games.common.Main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Utility class that allows to (de)serialize an object to/from file.
 */
public final class FileSerializer {

    /**
     * Private constructor of utility class.
     *
     * @throws Exception when trying to call this method
     */
    private FileSerializer() throws Exception {
        throw new UnsupportedOperationException();
    }

    /**
     * Deserializes an object from file.
     *
     * @return deserialized object
     * @throws Exception when error happens while deserializing
     */
    @SuppressWarnings("unchecked")
    public static <T> T deserialize() throws Exception {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Main.FILE_DATA));
        Object data = ois.readObject();
        ois.close();
        return (T) data;
    }

    /**
     * Serializes an object to file.
     *
     * @param data object that needs to be saved
     * @throws Exception when error happens while serializing
     */
    public static <T> void serialize(T data) throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Main.FILE_DATA));
        oos.writeObject(data);
        oos.close();
    }
}
