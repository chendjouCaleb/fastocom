package app;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.File;

public class TomcatServer implements IServer{

    private static final int PORT = 9500;
    private static final String DIR = "src/webapp/";
    private Tomcat tomcat;
    private Logger logger = LoggerFactory.getLogger(TomcatServer.class);
    private long begin;
    private long duration;

    @Override
    public void create() {
        begin = System.currentTimeMillis();
        logger.info("starting server");
        System.setProperty("tomcat.util.scan.StandardJarScanFilter.jarsToSkip", "*.jar");
        tomcat = new Tomcat();
    }

    @Override
    public void configure(){
        tomcat.setPort(PORT);
        Context context = null;
        try {
            context = tomcat.addWebapp("", new File(DIR).getAbsolutePath());
        } catch (ServletException e) {
            e.printStackTrace();
        }

        File classDir = new File("out/production/classes");
        WebResourceRoot resourceRoot = new StandardRoot(context);
        resourceRoot.addPreResources(new DirResourceSet(resourceRoot, "/WEB-INF/classes", classDir.getAbsolutePath(), "/" ));

        context.setResources(resourceRoot);
    }

    @Override
    public void start(){
        tomcat.getConnector();
        try {
            tomcat.start();
            duration = System.currentTimeMillis()-begin;

        } catch (LifecycleException e) {
            e.printStackTrace();
        }
        logger.info("Server: [Port={}], starting time=[{} s]", PORT, (double)duration/1000);
        tomcat.getServer().await();
    }

    @Override
    public void stop(){
        try {
            tomcat.stop();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }
}
