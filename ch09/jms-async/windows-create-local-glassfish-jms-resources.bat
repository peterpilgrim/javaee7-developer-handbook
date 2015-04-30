@Echo off
REM =====================================================================================================
REM Run this Windows command script in the GlassFish Home Bin folder
REM e.g. C:\opt\glassfish-4.1-b13-04_01_2015\bin
REM Peter Pilgrim 2015
REM =====================================================================================================

pushd C:\opt\glassfish-4.1-b13-04_01_2015\bin

asadmin     start-domain

asadmin     create-jms-resource  --restype javax.jms.ConnectionFactory jms/demoConnectionFactory

asadmin     create-jms-resource  --restype javax.jms.Queue jms/demoQueue

asadmin     list-jms-resources

asadmin     stop-domain

popd
echo Done.
