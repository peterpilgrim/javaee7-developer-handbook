/*******************************************************************************
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013,2014 by Peter Pilgrim, Addiscombe, Surrey, XeNoNiQUe UK
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPL v3.0
 * which accompanies this distribution, and is available at:
 * http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Developers:
 * Peter Pilgrim -- design, development and implementation
 *               -- Blog: http://www.xenonique.co.uk/blog/
 *               -- Twitter: @peter_pilgrim
 *
 * Contributors:
 *
 *******************************************************************************/

package je7hb.servlets.simple;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintStream;
import java.util.StringTokenizer;

/**
 * The type AsyncWriterServlet
 *
 * @author Peter Pilgrim
 */
@WebServlet(name = "AsyncWriterServlet",
        urlPatterns = { "/writer"}, asyncSupported = true )
public class AsyncWriterServlet extends HttpServlet {

    private static final String LOREM_IPSUM =
        "Lorem ipsum dolor sit amet, consectetur adipisicing " +
        "elit, sed do eiusmod tempor incididunt ut labore et " +
        "dolore magna aliqua. Ut enim ad minim veniam, quis " +
        "nostrud exercitation ullamco laboris nisi ut " +
        "aliquip ex ea commodo consequat. Duis aute irure " +
        "dolor in reprehenderit in voluptate velit esse " +
        "cillum dolore eu fugiat nulla pariatur. Excepteur " +
        "sint occaecat cupidatat non proident, sunt in " +
        "culpa qui officia deserunt mollit anim id " +
        "est laborum.";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.printf("init() called on %s\n",
            getClass().getSimpleName());
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException {
        System.out.printf("doGet() called on %s\n",
                getClass().getSimpleName());
        processResponse(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
        throws IOException, ServletException {
        System.out.printf("doPost() called on %s\n",
            getClass().getSimpleName());
        processResponse(request, response);
    }

    private void processResponse(HttpServletRequest request,
                                 HttpServletResponse response)
        throws IOException {
        System.out.printf("processRequest() called %s" +
            " on thread [%s]\n",
            getClass().getSimpleName(),
            Thread.currentThread().getName());
        AsyncContext context = request.startAsync();
        ServletOutputStream output = response.getOutputStream();
        output.setWriteListener(
                new AsyncWriteListener( output, context));
    }

    private class AsyncWriteListener implements WriteListener {
        private ServletOutputStream output;
        private AsyncContext context;

        private static final String LOREM_IPSUM =
            "Lorem ipsum dolor sit amet, consectetur adipisicing " +
            "elit, sed do eiusmod tempor incididunt ut labore et " +
            "dolore magna aliqua. Ut enim ad minim veniam, quis " +
            "nostrud exercitation ullamco laboris nisi ut " +
            "aliquip ex ea commodo consequat. Duis aute irure " +
            "dolor in reprehenderit in voluptate velit esse " +
            "cillum dolore eu fugiat nulla pariatur. Excepteur " +
            "sint occaecat cupidatat non proident, sunt in " +
            "culpa qui officia deserunt mollit anim id " +
            "est laborum.";


        private AsyncWriteListener(ServletOutputStream output,
                               AsyncContext context) {
            this.output = output;
            this.context = context;
            System.out.printf("thread [%s] AsyncWriteListener()\n ",
                Thread.currentThread().getName());
        }

        @Override
        public void onWritePossible() {
            System.out.printf("thread [%s] onWritePossible() " +
                "Sending data ...\n",
                Thread.currentThread().getName());

            StringTokenizer stk =
                    new StringTokenizer(LOREM_IPSUM," \t,.");
            PrintStream ps = new PrintStream(output);
            try {
                while ( output.isReady() && stk.hasMoreTokens() ) {
                    if ( stk.hasMoreTokens()) {
                        ps.println( stk.nextToken() );
                        ps.flush();
                        Thread.sleep(200);
                    }
                }
                ps.println("End of server *push*");
                ps.flush();
                ps.close();
                System.out.printf("thread [%s] Finished sending ...\n",
                    Thread.currentThread().getName());
            }
            catch (Exception e) {
                e.printStackTrace(System.err);
            }
            finally {
                context.complete();
            }
        }

        @Override
        public void onError(Throwable t) {
            System.out.printf("thread [%s] Error occurred=%s\n",
                    Thread.currentThread().getName(),
                    t.getMessage());
            context.complete();
        }
    }
}

