This is a Java application that contains several SOAP examples.
Each example has its own main method.


Instructions using Maven:
------------------------

To compile and copy the properties file to the output directory:
    mvn compile

To run the default example using exec plugin:
    mvn exec:java

To list available profiles (one for each example):
    mvn help:all-profiles

To run a specific example, select the profile with -P:
    mvn exec:java -P soap-connection


To configure Maven project in Eclipse:
-------------------------------------

If Maven pom.xml exist:
  'File', 'Import...', 'Maven'-'Existing Maven Projects'
  'Select root directory' and 'Browse' to the project base folder.
    Check that the desired POM is selected and 'Finish'.

If Maven pom.xml do not exist:
  'File', 'New...', 'Project...', 'Maven Projects'.
    Check 'Create a simple project (skip architype selection)'.
    Uncheck  'Use default Workspace location' and 'Browse' to the project base folder.
    Fill the fields in 'New Maven Project'.
    the Check if everything is OK and 'Finish'.

To run:
  Select the main class and click 'Run' (the green play button).
  Specify arguments using 'Run Configurations'


--
Revision date: 2015-04-21
leic-sod@disciplinas.tecnico.ulisboa.pt
