javaee7-developer-handbook
==========================

This is the official repository for my book, Java EE 7 Developer Handbook published by Packt Publishing (1st Published on Friday 20 September 2013 ;-) 


    ================================================================================
        J A V A  E E  7     D E V E L O P E R     H A N D B O O K
    ================================================================================



Author:             Peter A. Pilgrim
Original Date:      01/October/2012
Impression Date:    31/July/2013
Email:              peter.pilgrim@gmail.com



This is software to go with the book with ``The Java EE 7 Developer 
Handbook'' written by Peter A. Pilgrim, published by Packt Publishing. 
(September 2013) 



You can purchase a copy of the book from Packt Pub at this URL:

http://www.packtpub.com/java-ee-7-developer-handbook/book


This source code distribution can be found on GitHub after the 
book's publication date:

http://github.com/peterpilgrim/javaee7-developer-handbook

(The code will be uploaded simultaneously with the book's 
publication date ;-)


LICENSE
------------
The entire source code and software for the book falls under the GNU 
GENERAL PUBLIC LICENSE (Version 3, 29 June 2007) 
http://www.gnu.org/licenses/gpl-3.0.txt 

See the ``LICENSE.txt'' for the full legal text.



COMPILATION
------------

Each of the chapters in the book has its own folder, and within those 
chapter folder there is at least one project folder with a Gradle 
project. You will recognize them, because they have a ``build.gradle' 
file inside them. This is the project build file. 


Go http://gradle.org/ and download Gradle version 1.6 or better

To compile the program

	% gradle build


Explicitly to just compile the sources, run this command line:

	% gradle compileJava


To clean the project and reset to the start, run this command line:

	% gradle clean




TEST
-----

Ask Gradle to run the Unit Tests with the following comand:

    % gradle test --info


Look for the results in the folder `build/reports' and view the HTML 
file inside a web browser or examine the XML test file. By the way all 
of the tests from the source code were written with JUnit. 




ECLIPSE
------------

Ask Gradle to (re-)create Eclipse project artifacts ``.classpath'' 
and ``.project''

    % gradle eclipse



IDEA
---------

Ask Gradle to (re-)create IDEA project configuration files ``*.ipr''
and ``*.iml'' etc

    % gradle idea



DEPENDENCIES
-----------------

You can ask a gradle project about its dependencies with the following:

    % gradle dependencies


And you can find out about the tasks that you can invoke with:

    % gradle tasks



OTHER ARTIFACTS
-----------------

Some project create WAR files. Gradle plugin for WAR files bind 
automatically `assemble'. This also applies to EAR and RAR files. So 
this implies that:


    % gradle assemble

Will do the right thing for certain projects. Here are some other 
useful Gradle tasks.

    % gradle jar        // for project that builds a JAR
    % gradle war        // ditto 
    % gradle ear        // ditto



ROOT GRADLE PROJECT 
----------------------

In January 2016, I refactored the Gradle Build system for this book's distribution 
so it works as proper MULTI-MODULE build.

To examine the structure of the multi-modules of the entire distribution execute the following 
command from the root project


    % gradle projects
    

Gradle will show you the project structure.

From the root directory, you can build the XenTracker AngularJS with the following syntax


    % gradle :ch01:xentracker-angularjs:build
    

This also works for the sub chapter folders


    % gradle :appendix01:clean    
    % gradle :ch07:build



More information Gradle can be found at the documentation site http://gradle.org/


CHAPTER 9 - JMS ASYNC
----------------------

There is an additional project *jms-async' in Chapter 9 that deals with asynchronous JMS examples. 
It requires a standalone Java SE JMS service in order to fully this code. In other words, this code example
is designed to run outside of the Java EE environment and at the time of writing this 
code was untested though it may compile successfully.

You must explicitly include this project into the multi-module build using the Gradle Settings XML file.

In the [Gradle build properties file](gradle.properties) set the custom property to true.
 
    buildChapter9JmsAsync = true
    

NB: You may need to refresh or rerun your Gradle Plugin for your particular type of IDE in order to recognise the new projects.


CHAPTER 11 - Advanced JPA
----------------------------

The code projects in Chapter 11 (*jpa-criteria*, *jpa-entity-graph* etc) requires a managed GlassFish or Payara 
application server is order to execute correctly. See the instructions in this folder [ch11](ch11)

Please examine [Gradle settings configuration file](settings.xml) in order to understand how the conditionally module
inclusion works.

In the [Gradle build properties file](gradle.properties) set the custom property to true.
 
    buildChapter11JpaAll = true
    
NB: You may need to refresh or rerun your Gradle Plugin for your particular type of IDE in order to recognise the new projects.



Define an environment variable called *GLASSFISH_HOME*. 

On a Windows environment, you may define it with a value like so:

    
    GLASSFISH_HOME=C:\opt\payara-4.1.1.154
    

On a UNIX, Linux or Mac OS X environment, you might define this as:*

    export GLASSFISH_HOME=/Library/opt/payara-4.1.1.154/
    

*In the latter stanza, I assume that you are using the Bourne Again Shell (BASH)


  

All the best.
Peter Pilgrim, 2013-2016


To send comments, email me : peter.pilgrim@gmail.com
Web Blog http://www.xenonique.co.uk/blog/
Follow me on Twitter http://twitter.com/peter_pilgrim ( @peter_pilgrim )
