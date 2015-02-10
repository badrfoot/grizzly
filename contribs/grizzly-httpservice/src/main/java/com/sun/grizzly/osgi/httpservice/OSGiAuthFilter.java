/*
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2007-2008 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License. You can obtain
 * a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 * or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 * Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [] replaced by your own
 * identifying information: "Portions Copyrighted [year]
 * [name of copyright owner]"
 *
 * Contributor(s):
 *
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 *
 */
package com.sun.grizzly.osgi.httpservice;

import org.osgi.service.http.HttpContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * OSGi Authentication filter.
 *
 * @author Hubert Iwaniuk
 */
public class OSGiAuthFilter implements Filter {
    private final HttpContext httpContext;

    public OSGiAuthFilter(
            final HttpContext httpContext) {
        this.httpContext = httpContext;
    }

    /**
     * {@inheritDoc}
     */
    public void init(final FilterConfig filterConfig) throws ServletException {
        // nothing to do here
    }

    /**
     * OSGi integration. Relies on {@link HttpContext#handleSecurity(HttpServletRequest, HttpServletResponse)}.
     * <p/>
     * If {@link HttpContext#handleSecurity(HttpServletRequest, HttpServletResponse)} returns <code>true</code> proceed
     * to next {@link Filter} in {@link FilterChain}, else do nothing so processing stops here.
     * <p/>
     * {@inheritDoc}
     */
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        if (httpContext.handleSecurity((HttpServletRequest) request, (HttpServletResponse) response)) {
            chain.doFilter(request, response);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void destroy() {
        // nothing to do here
    }
}