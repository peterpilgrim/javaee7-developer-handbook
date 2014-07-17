#!/bin/bash
# Shell script to create the GlassFish 4 JDBC Connection pool for Mysql

# http://stackoverflow.com/questions/6572895/how-can-i-create-a-mysql-connection-pool-using-asadmin-tool-in-glassfish-server
echo "Create JDBC connection pool"
asadmin create-jdbc-connection-pool	\
  --datasourceclassname \
  com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource \
  --restype javax.sql.DataSource \
  --property User=arquillian:Password=arquillian:URL=\"jdbc:mysql://localhost:3306/arquillian\" \
  ArquillianPool

# An alternative command line
# asadmin create-jdbc-connection-pool --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource --restype javax.sql.DataSource
#       --property user=root:password=test:DatabaseName=test:ServerName=localhost:port=3306 test-pool

echo "Create JDBC resource"
asadmin create-jdbc-resource  --connectionpoolid ArquillianPool  jdbc/arquillian
