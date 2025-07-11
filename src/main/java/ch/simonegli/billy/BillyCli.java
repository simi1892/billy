package ch.simonegli.billy;

import ch.simonegli.billy.bill.BillCommand;
import ch.simonegli.billy.bill.BillService;
import ch.simonegli.billy.bill.QrBillService;
import ch.simonegli.billy.customer.CustomerCommand;
import ch.simonegli.billy.customer.CustomerService;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "billy", mixinStandardHelpOptions = true, version = "Billy 1.0",
        description = "A simple CLI to generate Swiss QR Bills.",
        subcommands = {CustomerCommand.class})
public class BillyCli implements Runnable {

    public static void main(String[] args) {
        BillyCli billyCli = new BillyCli();
        CustomerService customerService = new CustomerService();
        BillService billService = new BillService();
        QrBillService qrBillService = new QrBillService();

        CommandLine commandLine = new CommandLine(billyCli)
                .addSubcommand("bill", new BillCommand(customerService, billService, qrBillService));

        int exitCode = commandLine.execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        System.out.println("Welcome to Billy CLI. Use 'billy help' for more information.");
    }
}
