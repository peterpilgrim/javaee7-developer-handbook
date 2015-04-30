#!/usr/bin/env bash

SysError () {
    echo "$mynamne :: *ERROR* :  $*" 1>&2
    exit 1
}

myname=`basename $0`

: ${GLASSFISH_HOME:=/Library/opt/glassfish/glassfish-4.1/}

echo "GLASSFISH_HOME=${GLASSFISH_HOME}"

if [ ! -e ${GLASSFISH_HOME} ]; then
    SysError "GlassFish directory: $GLASSFISH_HOME does not exist."
fi
if [ ! -d ${GLASSFISH_HOME} ]; then
    SysError "GlassFish directory: $GLASSFISH_HOME is not a directory."
fi

BinDir=$GLASSFISH_HOME/bin

if [ ! -d ${BinDir} ]; then
    SysError "GlassFish directory: ${BinDir} is not a directory."
fi


${BinDir}/asadmin start-domain

${BinDir}/asadmin list-domain

${BinDir}/asadmin create-jms-resource  --restype javax.jms.ConnectionFactory jms/demoConnectionFactory

${BinDir}/asadmin create-jms-resource  --restype javax.jms.Queue jms/demoQueue

${BinDir}/asadmin list-jms-resources

${BinDir}/asadmin stop-domain

${BinDir}/asadmin list-domain


#MqProgramDir=$GLASSFISH_HOME/mq/bin
#
#if [ ! -d ${MqProgramDir} ]; then
#    SysError "GlassFish directory: ${MqProgramDir} is not a directory."
#fi
#
#${MqProgramDir}/imqobjmgr \
#   -l "cn=demoQueue" \
#   -j "java.naming.factory.initial=com.sun.jndi.ldap.LdapCtxFactory" \
#   -t qf \
#   -o "imqAddressList=mq://localhost:7272/jms"
#

#${MqProgramDir}/imqobjmgr \
#   -l "cn=demoQueue" \
#   -j "java.naming.factory.initial=com.sun.jndi.ldap.LdapCtxFactory" \
#   -j "java.naming.provider.url=ldap://localhost:389/o=imq" \
#   -j "java.naming.security.principal=uid=homerSimpson,ou=People,o=imq" \
#   -j "java.naming.security.credentials=doh" \
#   -j "java.naming.security.authentication=simple" \
#   -t qf \
#   -o "imqAddressList=mq://localhost:7272/jms"



# End

