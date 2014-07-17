README
-------


In order to build and test this project, you really need an instance of GlassFish 4.0.1 application server running already.
This is because the Arquillian tests rely on a **REMOTE**  GlassFish instance.

You also need to connect a separate relational database and then add a JDBC driver to the GlassFish server.
The book examples were written against [**MySQL Community Server 5.6.11**](http://dev.mysql.com/downloads/mysql/).
Instructions for installing MySQL were out of scope of the book, however I recommend you download the latest 
version 5.6 series of the Community Server and also install the popular **MySQL Workbench**. The Workbench allows
the digital developer to easily configure the database from a user interface.

GlassFish requires a separate **JDBC driver for Mysql**. Download and the [**MySql ConnectorJ**](http://dev.mysql.com/downloads/connector/j/5.1.html)
 
Copy the Mysql JConnector driver JAR to the GlassFish 4.0.1 library folder folder.
For example, this is the Mac OS X / Unix command line that I used recently:

<pre>
cp mysql-connector-java-5.1.25-bin.jar  /Library/opt/glassfish-4.0.1-b05-20140427/glassfish/lib
</pre>

Login to Mysql as the administration root user.

Create a brand new database called arquillian:
<pre>
CREATE SCHEMA arquillian;
</pre>


With MySql create a user call arquillian
<pre>
CREATE USER 'arquillian'@'arquillian' IDENTIFIED BY 'arquillian';
</pre>

Give that user permissions:
<pre>
GRANT ALL PRIVILEGES ON *.* TO 'arquillian'@'localhost'; 
</pre>

Remember to force MySql to save internal settings from memory to disk.
<pre>
FLUSH ALL PRIVILEGES;
</pre>

You should now be able to login your local MySql database as the user arquillian with the password arquillian. 
Verify that this, indeed, works from the command line or the Workbench GUI.


Assuming you have installed already GlassFish, you will need to create a JDBC connector pool for Mysql database.
Stop the local instance of GlassFish if it already running and then restart it.  
In another terminal, start GlassFish on your local workstation, execute the following

<pre>
asadmin stop-domain
asadmin start-domain
</pre>

Using the command line, execute either the shell script 'create-glassfish-resource.sh' or copy and paste the lines f
rom this file or the shell script to the command line.

If you are using Mac OS X or Liunx or UNIX environment, or Git-Bash on Windows, run the script from the terminal command line like so:
<pre>
 ./create-glassfish-jdbc-resource.sh 
</pre>

Alternatively, create the JDBC connection pool for GlassFish:

<pre>
asadmin create-jdbc-connection-pool --datasourceclassname  com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource  --restype javax.sql.DataSource  --property User=arquillian:Password=arquillian:URL="jdbc:mysql://localhost:3306/arquillian"  ArquillianPool
</pre>

  
Create the JDBC resource:
<pre>
asadmin create-jdbc-resource --connectionpoolid ArquillianPool  jdbc/arquillian
</pre>


At this point, you will need to restart GlassFish to take advantage of the changes.
<pre>
asadmin stop-domain
asadmin start-domain --verbose
</pre>


In this current terminal, run the Gradle tests
<pre>
gradle test
</pre>


These are the instructions to create a JDBC Data source for Glassfish 4.0 and for MySQL 5.6.

It should be possible to use an alternative database like Postgres or Oracle, but you will probably have to hack the 
code to change functionality, you will certainly need a different JDBC driver and, of course, 
you will need set up JDBC driver for GlassFish differently. 


+PP+ *2014*   


http://twitter.com/peter_pilgrim  *Follow me on Twitter*
