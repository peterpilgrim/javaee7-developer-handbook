README  README  README  README  README  README  README  README  README  README  
================================================================================

Author:             Peter A. Pilgrim
Original Date:      01/October/2012
Impression Date:    31/July/2013
Email:              peter.pilgrim@gmail.com


================================================================================
    J A V A  E E  7     D E V E L O P E R     H A N D B O O K
================================================================================

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




++++++++++++++++++++++++++++++++
++++++++++++++++++++++++++++++++
++++   LATE BREAKING NEWS   ++++
++++++++++++++++++++++++++++++++
++++++++++++++++++++++++++++++++

At the last moment, I decided to split the XenTracker demonstration is 
two modes: modern and traditional. The first mode that I developed uses 
AngularJS (http://angularjs.org), a popular JavaScript framework and 
this is very modern. However I realized that this would be impedement to 
very new Java EE developer, who have just started to learn JavaServer 
Pages, JavaServer Faces and therefore I felt their learning would 
benefit a simplified page flow without AJAX. So please bear with me as I 
sort out the split. PS: I am doing post JavaOne 2013, conference and 
travelling around California. 



HINT:

Navigate to the `Embedded Container' project and build that one first. 
Execute the following commands from the project root directory

    % cd appendix01/glassfish-embedded
    % gradle clean
    % gradle install


The embedded container is used by many of the examples. I am sorry about 
this mess, because Gradle multi-module files do not behave exactly like
Maven modules. I counted on it when I was writing the book and then 
discovered this was the wrong approach for IDE's such as JetBrains IDEA.


	
++++++++++++++++++++++++++++++++
++++++++++++++++++++++++++++++++

All the best.
Peter Pilgrim, 2013


To send comments, email me : peter.pilgrim@gmail.com
Web Blog http://www.xenonique.co.uk/blog/
