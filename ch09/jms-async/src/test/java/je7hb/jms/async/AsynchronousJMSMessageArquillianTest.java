package je7hb.jms.async;

import static org.junit.Assert.*;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.*;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Verifies the operation of the AsynchronousJMSMessageArquillianTest
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
public class AsynchronousJMSMessageArquillianTest {

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "asyncjms.war")
                .addClasses(PayloadCheck.class)
                .addAsWebInfResource(
                        "test-persistence.xml",
                        "classes/META-INF/persistence.xml")
                        // .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
                .addAsWebInfResource(
                        new File("src/test/resources-glassfish-managed/glassfish-resources.xml"),
                        "glassfish-resources.xml")
                .addAsWebInfResource(
                        EmptyAsset.INSTANCE, "beans.xml");

        System.out.println(webArchive.toString(true));
        return webArchive;
    }

    private List<String> messages = new ArrayList<>();

    private CompletionListener completionListener = new CompletionListener() {
        @Override
        public void onCompletion(Message msg) {
            TextMessage textMsg = (TextMessage)msg;
            try {
                System.out.printf("%s.onCompletion(%s) Thread: %s\n",
                        getClass().getSimpleName(), textMsg.getText(), Thread.currentThread());
            } catch (JMSException e) {
                e.printStackTrace(System.err);
            }
        }

        @Override
        public void onException(Message msg, Exception ex) {
            ex.printStackTrace(System.err);
        }
    };

//    @Resource(mappedName = "jms/demoConnectionFactory")
//    private ConnectionFactory connectionFactory;
//
//    @Resource(mappedName = "jms/demoQueue")
//    private Queue queue;

    @Test
    @RunAsClient
    public void shouldFire() throws JMSException, InterruptedException, NamingException {
        final Properties properties = new Properties();
//        properties.put("com.sun.appserv.iiop.endpoints", "localhost:7676");
//        properties.put("org.omg.CORBA.ORBInitialHost", "localhost");
//        properties.put("org.omg.CORBA.ORBInitialPort", "3700");
//        properties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
//        properties.put(Context.PROVIDER_URL, "mq://localhost:7676/");
//        properties.put("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
//        properties.put("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");

        properties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
        properties.put(Context.URL_PKG_PREFIXES, "com.sun.enterprise.naming");
        properties.put(Context.STATE_FACTORIES, "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
//        properties.put("org.omg.CORBA.ORBInitialHost", "localhost");
//        properties.put("org.omg.CORBA.ORBInitialPort", "3700");
        properties.put(Context.PROVIDER_URL, "mq://localhost:7676"); // vm://localhost:
//        properties.put(Context.PROVIDER_URL, "iiop://localhost:7676"); // vm://localhost:

        System.out.println("=====================================================================");
        System.out.println("=====================================================================");
        final InitialContext jndiContext = new InitialContext(properties);
        System.out.printf("\t jndiContext=%s\n", jndiContext);
        System.out.println("=====================================================================");
        System.out.println("=====================================================================");

        final ConnectionFactory connectionFactory = (ConnectionFactory)jndiContext.lookup("jms/demoConnectionFactory");

        final Queue queue = (Queue)jndiContext.lookup("jms/demoQueue");

        assertNotNull(connectionFactory);
        assertNotNull(queue);

        final Connection connection = connectionFactory.createConnection();
        final JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE );
        final JMSProducer producer = context.createProducer();

        messages.clear();

        producer.setAsync(completionListener);

        producer.send(queue, "hello");
        producer.send(queue, "world");
        producer.send(queue, "asynchronously");

        Thread.sleep(1774);

        System.out.println("Done");
    }
}
