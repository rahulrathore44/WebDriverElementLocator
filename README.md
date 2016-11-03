###WebDriver Element Locator


Automated approach to extract object properties. The script extracts the web element locator,
By going to each page and validating the locator is unique or not.
If the locator is unique then it is saved in Csv/Excel/Json file.

It also generate the Page Object Model class file

### List of Urls must be specified in the config.properties file:

```java
    Website1=https://www.linkedin.com/
    Website2=http://automationpractice.com/index.php
    Website3=http://newtours.demoaut.com/
```

### To see this whole thing running simply checkout this project and run this command:

`mvn clean generate-sources install`

### The script create the Csv/Excel/Json file with name as key from config.properties 

`Page Object Model Class File`

![Pom]
(https://github.com/rahulrathore44/WebDriverElementLocator/blob/master/src/overview/pageobj.PNG) 

`Comma separated file`

![Csv]
(https://github.com/rahulrathore44/WebDriverElementLocator/blob/master/src/overview/Csv.PNG)

`Excel file`

![Excel]
(https://github.com/rahulrathore44/WebDriverElementLocator/blob/master/src/overview/Excel.PNG)

`Json file`

![Json]
(https://github.com/rahulrathore44/WebDriverElementLocator/blob/master/src/overview/Json.PNG) 


