package je7hb.jms.async;

import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSDestinationDefinitions;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The type ApplicationServlet
 *
 * @author Peter Pilgrim
 */

@JMSDestinationDefinitions({
        @JMSDestinationDefinition(name = "java:global/jms/syncQueue",
            resourceAdapter = "jmsra",
            interfaceName = "javax.jms.Queue",
            destinationName="syncQueue",
            description="My Synchronous Queue"),
        @JMSDestinationDefinition(name = "java:global/jms/asyncQueue",
                resourceAdapter = "jmsra",
                interfaceName = "javax.jms.Queue",
                destinationName="asyncQueue",
                description="My Asynchronous Queue")
})
@WebServlet(urlPatterns = {"/AppServe"})
public class ApplicationServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>JMS2 Send Message (Async)</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>JMS2 Send/Receive (Async)</h1>");
            out.println("<b>Async send not permitted in Java EE, using sync send instead</b><br><br>");
            out.println("<br><br>Check server.log for output from asynchronous bean.");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
