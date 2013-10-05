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

package je7hb.jaxrs.basic;

import javax.crypto.*;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.*;
import java.io.*;

/**
 * The type AESCipherInterceptor
 *
 * @author Peter Pilgrim (peter)
 */
@Provider
public class AESCipherInterceptor
    implements ReaderInterceptor, WriterInterceptor{
    private final AESCipher cipher;

    public AESCipherInterceptor() throws Exception {
        final byte[] salt =
            { 1,2,4,8,16,32,64,-64,-32,-16,-8,-4,-2,-1};
        final String password = "java1995";

        cipher = new AESCipher(password, salt);
    }

    @Override
    public Object aroundReadFrom(ReaderInterceptorContext context)
            throws IOException, WebApplicationException {
        InputStream old = context.getInputStream();
        context.setInputStream( new CipherInputStream(
                old, cipher.getDecryptCipher()));
        try {
            return context.proceed();
        }
        finally {
            context.setInputStream(old);
        }
    }

    @Override
    public void aroundWriteTo(WriterInterceptorContext context)
            throws IOException, WebApplicationException {
        OutputStream old = context.getOutputStream();
        context.setOutputStream( new CipherOutputStream(
                old, cipher.getEncryptCipher()));
        try {
            context.proceed();
            context.getHeaders().add("X-Encryption", "AES");
        }
        finally {
            context.setOutputStream(old);
        }
    }
}
