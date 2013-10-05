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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The type WebMethodUtils
 *
 * @author Peter Pilgrim (peter)
 */
public class WebMethodUtils {
    private WebMethodUtils() {}

    public static List<String> getLines( InputStream inputStream) throws Exception {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream));
        List<String> lines = new ArrayList<>();
        String text = null;
        int count=0;
        while ( ( text = reader.readLine()) != null ) {
            lines.add(text);
            System.out.printf("**** OUTPUT **** text[%d] = %s\n", count, text);
            ++count;
        }
        return lines;
    }

    public static List<String> makeGetRequest( URL url ) throws Exception {
        InputStream inputStream = url.openStream();
        return getLines(inputStream);
    }

    public static List<String> makeDeleteRequest( URL url ) throws Exception {
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.setDoOutput(true);
        httpCon.setRequestProperty(
                "Content-Type", "application/x-www-form-urlencoded" );
        httpCon.setRequestMethod("DELETE");
        httpCon.connect();

        InputStream inputStream = httpCon.getInputStream();
        return getLines(inputStream);
    }

    public static List<String> makePostRequest( URL url, Map<String, String> params)
            throws Exception {
        return makeWebRequest("POST", url, params);
    }

    public static List<String> makePutRequest( URL url, Map<String, String> params)
            throws Exception {
        return makeWebRequest("PUT", url, params);
    }

    public static List<String> makeWebRequest( String requestMethod,
                                               URL url, Map<String,String> params)
            throws Exception {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod(requestMethod);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("charset", "utf-8");
        connection.setUseCaches(false);

        StringBuilder urlParameters = new StringBuilder();
        for ( Map.Entry<String,String> entry: params.entrySet()) {
            urlParameters.append(entry.getKey()+
                    "="+entry.getValue()+"&");
        }
        DataOutputStream wr = new DataOutputStream(connection.getOutputStream ());
        wr.writeBytes(urlParameters.toString());
        wr.flush();
        wr.close();
        connection.disconnect();

        InputStream inputStream = connection.getInputStream();
        return getLines(inputStream);
    }
}
