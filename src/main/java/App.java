import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class App {
    public static void main(String[] args) {
        final Logger logger = LogManager.getLogger(App.class);
        logger.info("Application started");
        BankInterface.menuLoop(24);
        logger.info("Application terminated");
    }
}
