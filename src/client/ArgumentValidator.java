package client;

/**
 * Created by hugo on 11/17/14.
 */
public class ArgumentValidator {

    public ArgumentValidator() {

    }

    public void validateGetArguments(String[] arguments) {
        if(arguments.length != 1) {
            printUsageInstr();
        }
    }

    public void validateSearchArguments(String[] arguments) {
        if(arguments.length != 1) {
            printUsageInstr();
        } else if (arguments[1].length() < 1 || arguments[1].length() > 32) {
            printUsageInstr();
        }
    }

    public void validatePostArguments(String[] arguments) {
        if(arguments.length != 5) {
           printUsageInstr();
        }
    }

    public void printUsageInstr() {
        System.err.println("Usage: client CMD [args]");
        System.err.println("where CMD is {POST} or {GET}");
        System.err.println("{POST} sender topic content recipient ");
        System.err.println("{GET} {message-id} or null");
        System.err.println("{SEARCH} {keyword}");
    }
}
