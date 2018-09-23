import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Util {

    /**
     * Gets the external path to the jar.
     * @return Returns the String of an external path to the current location of the jar being ran.
     */
    public static String getJarLocation()
    {
        String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        try {
            String decodedPath = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String[] s = path.split("/");
        String r = "";
       for(String x : s)
       {
            if (!x.contains("jar"))
            {
                r = r + x + "/";
            }
        }

        return r;
    }

    /**
     * Creates a basic local file at the path if one doesn't exist.
     * @param path Creates a local file at the specified path.
     */
    public static void createLocalFile(String path)
    {
        createFile(getJarLocation() + path);
    }

    /**
     * Creates a basic file at the specified path.
     * @param path The path on where to create the file.
     */
    public static void createFile(String path)
    {
        File targetFile = new File(path);
        File parent = targetFile.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Couldn't create dir: " + parent);
        }
        try {
            targetFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a local directory at the specified path and all the parent directories.
     * @param path Path to create including parent directories.
     */
    public static void createLocalDirectory(String path)
    {
        createFile(getJarLocation() + path + "/.");
    }

    /**
     * Checks if a file already exists.
     * @param file The file in question.
     * @return Returns true if the files exists, false if it does not.
     */
    public static boolean fileExists(String file)
    {
        //System.out.println("Checking if file " + file + " exists.");
        File f = new File(file);
        return f.exists();
    }


}
