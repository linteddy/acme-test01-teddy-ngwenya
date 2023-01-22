# Guide on how to run JUnit tests using Maven:

1. Open a terminal/command prompt and navigate to the root directory of your Maven project.

2. Run the following command to execute all tests in your project:

```bash
./mvnw test
```
3. To execute a specific test class, use the following command:
```bash
./mvnw -Dtest=TestClassName test
```
4. To execute a specific test method within a class, use the following command:
```bash
./mvnw -Dtest=TestClassName#testMethodName test
```

5. To see more detailed output when running the tests, you can use the following command:
```bash
./mvnw -Dsurefire.useFile=false test
```
6. Convert .xml reports into .html report, but without the CSS or images
```bash
./mvnw surefire-report:report-only
```
go to target/site/surefire-report.html for the report.
