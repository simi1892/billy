package ch.simonegli.billy;

import ch.simonegli.billy.customer.CustomerCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.HelpCommand;

@Command(name = "billy", mixinStandardHelpOptions = true, version = "Billy 1.0",
        description = "A simple CLI to generate Swiss QR Bills.",
        subcommands = {HelpCommand.class, CustomerCommand.class})
public class BillyCli implements Runnable {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new BillyCli()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        System.out.println("Welcome to Billy CLI. Use 'billy help' for more information.");
    }
}
