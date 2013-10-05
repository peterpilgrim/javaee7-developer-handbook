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

import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import static org.junit.Assert.assertEquals;

/**
 * The type AESCipherTest
 *
 * @author Peter Pilgrim (peter)
 */
public class AESCipherTest {

    @Test
    public void shouldEncryptAndDecrypt() throws Exception {

        final byte[] salt = {1,2,4,8,16,32,64,-64,-32,-16,-8,-4,-2,-1};

        AESCipher cipher1 = new AESCipher("java1995", salt);
        byte data[] = cipher1.encrypt("Guidelines for Java EE web developers");

        System.out.printf("data.length=%d\n", data.length);

        AESCipher cipher2 = new AESCipher("java1995", salt);
        String output = cipher2.decrypt(data);

        assertEquals( "Guidelines for Java EE web developers", output );
    }


}
