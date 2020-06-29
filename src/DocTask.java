
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author migo
 */
public class DocTask {

    public static void main(String[] args) {
        System.out.println("DocTask.main() - RUN");
        if (args.length > 0) {
            System.out.println("Parsing " + args.length);
            for (String arg : args) {
                System.out.println("ARG - " + arg);
                if (arg.toLowerCase().endsWith(".jar")) {
                    readJarFile(arg);
                }
            }
        }
    }

    private static void analyse(JarEntry entry) {
        String name = entry.getRealName();
        if (name.toLowerCase().endsWith(".class")) {
            System.out.println(entry.getRealName());
        }
    }

    private static void readJarFile(String file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            JarInputStream jarInputStream = new JarInputStream(fis);
            JarEntry entry = jarInputStream.getNextJarEntry();
            while (entry != null) {
                if (entry != null) {
                    analyse(entry);
                }
                entry = jarInputStream.getNextJarEntry();
            }

        } catch (Exception ex) {
            Logger.getLogger(DocTask.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void addLibrary(File file) throws Exception {
        Method method = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
        method.setAccessible(true);
        method.invoke(ClassLoader.getSystemClassLoader(), new Object[]{file.toURI().toURL()});
    }

}
