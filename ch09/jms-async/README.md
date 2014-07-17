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


+PP+ *2014*   


http://twitter.com/peter_pilgrim  *Follow me on Twitter*
