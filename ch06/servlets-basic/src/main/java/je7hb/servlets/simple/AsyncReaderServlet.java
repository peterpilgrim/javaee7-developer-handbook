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

/**
 * The type AsyncReaderServlet
 *
 * @author Peter Pilgrim
 */
@WebServlet(name = "AsyncReaderServlet", urlPatterns = { "/reader"}, asyncSupported = true )
public class AsyncReaderServlet extends HttpServlet {

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
        processRequest(request);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
        throws IOException, ServletException {
        System.out.printf("doPost() called on %s\n",
            getClass().getSimpleName());
        processRequest(request);
    }

    private void processRequest(HttpServletRequest request)
        throws IOException {
        System.out.printf("processRequest() called %s" +
            " on thread [%s]\n",
            getClass().getSimpleName(),
            Thread.currentThread().getName());
        AsyncContext context = request.startAsync();
        ServletInputStream input = request.getInputStream();
        input.setReadListener(new AsyncReadListener(input, context));
    }

    private class AsyncReadListener implements ReadListener {
        private ServletInputStream input;
        private AsyncContext context;

        private AsyncReadListener(ServletInputStream input,
                                  AsyncContext context) {
            this.input = input;
            this.context = context;
        }

        @Override
        public void onDataAvailable() {
            try {
                StringBuilder sb = new StringBuilder();
                int len = -1;
                byte buffer[] = new byte[2048];
                while (input.isReady()
                        && (len = input.read(buffer)) != -1) {
                    String data = new String(buffer, 0, len);
                    System.out.printf("thread [%s] data: %s\n",
                            Thread.currentThread().getName(),
                            data);
                }
            } catch (IOException ex) {
                ex.printStackTrace(System.err);
            }
        }

        @Override
        public void onAllDataRead() {
            System.out.printf("thread [%s] onAllDataRead()\n",
                Thread.currentThread().getName() );
            context.complete();
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
