# Billy - The QR Bill Generator 🚌💨

Welcome to Billy, a fun little project for generating Swiss QR bills, especially handy for my bus rental side-hustle! 🚌

This is a simple web application to manage customers and generate QR bills for them.

This project was written solely with vibe coding (gemini-cli). ✨

## Getting Started 🚀

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

*   Java 21
*   Maven

### Building

To build the project, run the following command:

```bash
mvn clean install
```

This will create a `jar` file in the `target` directory.

## Usage 👨‍💻

You can run the application using the following command:

```bash
java -jar target/billy-1.0.0-SNAPSHOT.jar
```

Once the application is running, open your web browser and navigate to `http://localhost:8080/customers`.

## Development 💻

This project is built with:

*   [Spring Boot](https://spring.io/projects/spring-boot) for the web application framework.
*   [Thymeleaf](https://www.thymeleaf.org/) as the templating engine.
*   [Bootstrap](https://getbootstrap.com/) for styling.
*   [Gson](https://github.com/google/gson) for JSON handling.
*   [OpenHTMLToPDF](https://github.com/openhtmltopdf/openhtmltopdf) for PDF generation.
*   [JUnit 5](https://junit.org/junit5/) for testing.

## Contributing 🙏

Feel free to contribute to this project. Please open an issue or a pull request.

## License 📄

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.