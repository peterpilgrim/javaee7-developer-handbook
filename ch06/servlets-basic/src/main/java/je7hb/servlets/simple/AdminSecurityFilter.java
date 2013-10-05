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
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;


/**
 * The type SimpleLoggingFilter
 *
 * @author Peter Pilgrim
 */
@WebFilter(filterName="MySecurityFilterLogger",
    urlPatterns = {"/admin/*"},
    initParams = {
        @WebInitParam(name = "fruit", value = "Pear"),
    }
)
public class AdminSecurityFilter implements Filter {
    private FilterConfig filterConfig;

    public void init(FilterConfig filterConfig) {
        System.out.printf("init() on %s\n"+
                "Metadata filter name=%s\n",
                getClass().getSimpleName(),
                filterConfig.getFilterName());
        this.filterConfig = filterConfig;
    }

    public void doFilter(
        ServletRequest req, ServletResponse res,
        FilterChain filterChain)
        throws IOException, ServletException
    {
        HttpServletRequest req2 = (HttpServletRequest)req;
        HttpServletResponse res2 = (HttpServletResponse)res;
        if ( req2.getUserPrincipal().getName()
                .equals("admin")) {
            filterChain.doFilter(req, res);
        }
        else {
            res2.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    public void destroy() {
        System.out.printf("destroy() on %s\n", getClass().getSimpleName());
    }
}
