mvn -DBUNDLE_TYPE=pp clean test
mvn clean test

mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="show-trace trace.zip"

mvn allure:serve