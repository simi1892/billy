# Billy - The QR Bill Generator 🚌💨

Welcome to Billy, a fun little project for generating Swiss QR bills, especially handy for my bus rental side-hustle! 🚌

This is a simple command-line interface (CLI) to manage customers and generate QR bills for them.

This project was written solely with vibe coding (gemini-cli). ✨

## Getting Started 🚀

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

* Java 21
* Maven

### Building

To build the project, run the following command:

```bash
mvn clean package
```

This will create a `jar` file in the `target` directory.

## Usage 👨‍💻

You can run the application using the following command:

```bash
java -jar target/billy-1.0.0-SNAPSHOT-jar-with-dependencies.jar
```

### Commands

Here are the available commands:

* `customer`: Manage customers.
* `add`: Add a new customer.
* `list`: List all customers.
* `bill`: Create a new bill.
* `create`: Create a new bill for a customer.

For more information on a specific command, use the `--help` option. For example:

```bash
java -jar target/billy-1.0.0-SNAPSHOT-jar-with-dependencies.jar customer add --help
```

## Development 💻

This project is built with:

* [PicoCLI](https://picocli.info/) for the CLI.
* [Gson](https://github.com/google/gson) for JSON handling.
* [OpenHTMLToPDF](https://github.com/openhtmltopdf/openhtmltopdf) for PDF generation.
* [Thymeleaf](https://www.thymeleaf.org/) as a template engine.
* [JUnit 5](https://junit.org/junit5/) for testing.

## Contributing 🙏

Feel free to contribute to this project. Please open an issue or a pull request.

## License 📄

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
