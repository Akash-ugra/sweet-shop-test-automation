# Sweet Shop Test Automation

This project is a Java Selenium application built using the Maven build system. It is designed to automate testing for the web application available at [https://sweetshop.netlify.app/](https://sweetshop.netlify.app/).

## Project Structure

```
sweet-shop-test-automation
├── src
│   ├── main
│   │   └── java
│   │       └── App.java
│   ├── test
│   │   └── java
│   │       └── AppTest.java
├── pom.xml
└── README.md
```

## Setup Instructions

1. **Clone the repository:**
   ```
   git clone <repository-url>
   cd sweet-shop-test-automation
   ```

2. **Install Maven:**
   Ensure that you have Maven installed on your machine. You can download it from [Maven's official website](https://maven.apache.org/download.cgi).

3. **Build the project:**
   Run the following command to build the project and download the necessary dependencies:
   ```
   mvn clean install
   ```

## Usage

To run the tests, use:
```
mvn test
```

## Dependencies

This project uses the following dependencies:
- Selenium WebDriver
- JUnit

Make sure to check the `pom.xml` file for the complete list of dependencies and their versions.
