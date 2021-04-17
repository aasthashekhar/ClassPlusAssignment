# ClassPlusAssignment
To compare phone price values between Amazon and Flipkart
---

## System Requirement:
JDK 1.6 or above
Maven 3.1 or above
Eclipse IDE or any other of choice in case there is need to update the script. (optional)

## Chrome Driver Installation
For execution of scripts on Chrome you need to have executable files of chrome driver and paste them at location "\src\test\resources\Drivers" in project folder.

You can download latest executable files from below link
Chrome: https://chromedriver.chromium.org/downloads

## Execution Steps:
Please follow the instructions to execute the tests on local:
According to the Test Scope use following commands


To execute Pubs KPI Dashboard Comparison.
```bash
mvn clean verify -DphoneName='iPhone XR (64GB) - Yellow'
```

## Note:
For executing tests on local machine checkout code from github and then use the above command through command prompt from folder path where pom.xml file is present

## Result Files:
The Test Execution Results will be stored in the following directory once the test has completed:
./target/surefire-reports/emailable-report.html
