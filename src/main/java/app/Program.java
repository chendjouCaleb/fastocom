package app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Program {
    private static Logger logger = LoggerFactory.getLogger(Program.class);

    public static void main(String[] args) {
        IServer server = new TomcatServer();
        logger.info("Starting of Fastocom application");
        server.create();
        server.configure();
        server.start();

        logger.info("Fastocom application closed");
    }
}
