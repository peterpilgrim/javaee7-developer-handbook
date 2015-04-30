README
-------


In order to build and test this project, you really need an instance of GlassFish 4.0.1 application server.
This is because the Arquillian tests really on a Remote GlassFish instance.

Configure the GlassFish managed location, see the file [`glassfish-resources.xml`](src/test/resources-glassfish-managed/glassfish-resources.xml).

Change the location to the installation root of GlassFish 4.0 on your workstation.

```xml
   <container qualifier="glassfish-managed" default="true">
        <configuration>
            <property name="glassFishHome">/YOUR/GLASSFISH/LOCATION/HERE</property>
            <property name="adminHost">localhost</property>
            <property name="adminPort">4848</property>
        </configuration>
    </container>
```


## Creating JMS Resources on GlassFish 

Open a terminal window.

Navigate to GLASSFISH_HOME and then to the `bin` directory.

```
cd /Library/opt/glassfish-4.1/bin
```

Run the GlassFish administrator program `asadmin`. 

At the interactive prompt for this program, run the following commands.

Start the application server on domain1.
```
    start-domain
```

Show the running domains
```
    list-domains
```

Create the JMS connection factory

```
    create-jms-resource  --restype javax.jms.ConnectionFactory jms/demoConnectionFactory
```

Create the JMS queue
```
    create-jms-resource  --restype javax.jms.Queue jms/demoQueue
```

Reveal the JMS resources for this domain1
```
    list-jms-resources
```

Stop the application server on domain1

```
    stop-domain
```

Terminate the interactive GlassFish Administrator shell and return to the parent command shell prompt. 

```
    exit
```


+PP+ *2014*   


http://twitter.com/peter_pilgrim  *Follow me on Twitter*
