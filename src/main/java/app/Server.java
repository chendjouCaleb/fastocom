package app;

import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.scan.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class Server {
    private static final int PORT = 8000;
    private static Tomcat tomcat;
    private static Logger logger = LoggerFactory.getLogger(Server.class);
    static void createServer(){
        long begin = System.currentTimeMillis();
        System.setProperty(Constants.SKIP_JARS_PROPERTY, "*.jar");
        String appBase = "";
        tomcat = new Tomcat();
        tomcat.setBaseDir(createTempDir());
        tomcat.setPort(PORT);
        tomcat.getHost().setAppBase(appBase);
        try {
            tomcat.addWebapp("/src/web", appBase);
            tomcat.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

        long duration = System.currentTimeMillis() - begin;

        logger.info("Server starting during {} ms {} s", duration, (double)duration/1000);
    }
    static void run(){
        tomcat.getServer().await();
    }

    private static String createTempDir() {
        try {
            File tempDir = File.createTempFile("tomcat.", "." + PORT);
            tempDir.delete();
            tempDir.mkdir();
            tempDir.deleteOnExit();
            System.out.println(tempDir.getAbsolutePath());
            return tempDir.getAbsolutePath();
        } catch (IOException ex) {
            throw new RuntimeException(
                    "Unable to create tempDir. java.io.tmpdir is set to " + System.getProperty("java.io.tmpdir"),
                    ex
            );
        }
    }
}
