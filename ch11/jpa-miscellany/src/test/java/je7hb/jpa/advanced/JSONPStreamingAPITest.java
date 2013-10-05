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

package je7hb.jpa.advanced;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import je7hb.jpa.Utils;
import je7hb.jpa.advanced.entitygraph1.*;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.*;
import org.junit.runner.RunWith;

import javax.json.stream.*;
import static javax.json.stream.JsonParser.Event.*;
import javax.json.*;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Verifies the operation of the JSONPStreamingAPITest
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
public class JSONPStreamingAPITest {
    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(
                        EmptyAsset.INSTANCE,
                        ArchivePaths.create("beans.xml"));
        return jar;
    }

    String TEST_JSON2 =
            "{\n" +
            "    \"firstName\": \"John\",\n" +
            "    \"lastName\": \"Smith\",\n" +
            "    \"age\": 25,\n" +
            "    \"address\": {\n" +
            "        \"streetAddress\": \"21 2nd Street\",\n" +
            "        \"city\": \"New York\",\n" +
            "        \"state\": \"NY\",\n" +
            "        \"postalCode\": 10021\n" +
            "    },\n" +
            "    \"phoneNumbers\": [\n" +
            "        {\n" +
            "            \"type\": \"home\",\n" +
            "            \"number\": \"212 555-1234\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"type\": \"fax\",\n" +
            "            \"number\": \"646 555-4567\"\n" +
            "        }\n" +
            "    ]\n" +
            "}"
            ;


    static String TEST_JSON =
        "{\n" +
        "    \"firstName\": \"John\",\n" +
        "    \"lastName\": \"Smith\",\n" +
        "    \"age\": 25,\n" +
        "    \"address\": {\n" +
        "        \"streetAddress\": \"21 2nd Street\",\n" +
        "        \"city\": \"New York\",\n" +
        "        \"state\": \"NY\",\n" +
        "        \"postalCode\": 10021\n" +
        "    }\n" +
        "}"
        ;

    @Test
    public void shouldParseJSONSchema() {
        StringReader sreader = new StringReader(TEST_JSON);
        JsonParser parser = Json.createParser(sreader);
        JsonParser.Event event = parser.next();
        assertEquals(START_OBJECT, event);
        while (parser.hasNext()) {
            event = parser.next();
            debugJSONEvent(parser, event);
            if ( event == KEY_NAME) {
                switch (parser.getString()) {
                    case "firstName":
                        parser.next();
                        assertThat( parser.getString(), is("John"));
                        break;
                    case "lastName":
                        parser.next();
                        assertThat( parser.getString(), is("Smith"));
                        break;
                    case "age":
                        parser.next();
                        assertThat( parser.getInt(), is(25));
                        break;

                }
            }
        }

    }

    private void debugJSONEvent(
        JsonParser parser, JsonParser.Event event) {
        switch(event) {
            case START_ARRAY:
            case END_ARRAY:
            case START_OBJECT:
            case END_OBJECT:
            case VALUE_FALSE:
            case VALUE_NULL:
            case VALUE_TRUE:
                System.out.println(event.toString());
                break;
            case KEY_NAME:
                System.out.print(event.toString() + " " +
                        parser.getString() + " - ");
                break;
            case VALUE_STRING:
            case VALUE_NUMBER:
                System.out.println(event.toString() + " " +
                        parser.getString());
                break;
        }
    }

    @Test
    public void shouldGenerateJSON() {
        StringWriter swriter = new StringWriter();
        try (JsonGenerator generator =
                     Json.createGenerator(swriter)) {
            generator
                .writeStartObject()
                    .write("artist", "Daft Punk")
                    .write("album", "Random Access Memories")
                    .write("year", 2013)
                    .writeStartArray("collaborators")
                        .writeStartObject()
                            .write("firstName", "Nile")
                            .write("lastName", "Rodgers")
                        .writeEnd()
                        .writeStartObject()
                            .write("firstName", "Giorgio")
                            .write("lastName", "Moroder")
                        .writeEnd()
                    .writeEnd()
                .writeEnd();
        }
        System.out.printf("\n***** >>>>>>>>> swriter.toString=%s\n", swriter.toString());
        String expected = "{\"artist\":\"Daft Punk\"," +
        "\"album\":\"Random Access Memories\",\"year\":2013,\"" +
        "collaborators\":[{\"firstName\":\"Nile\"," +
        "\"lastName\":\"Rodgers\"},{\"firstName\":" +
        "\"Giorgio\",\"lastName\":\"Moroder\"}]}";
        assertThat(swriter.toString().length(), is(not(0)));
        assertThat(swriter.toString(), is(expected));
    }
}
