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

package je7hb.appendix.utilities.jsondemos;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Verifies the operation of the JSONPStreamingAPITest
 *
 * @author Peter Pilgrim
 */
@RunWith(Arquillian.class)
public class JSONPObjectAPITest {
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
        StringReader sreader = new StringReader(TEST_JSON2);
        JsonReader reader = Json.createReader(sreader);
        JsonObject obj = reader.readObject();
        System.out.printf("**********  json=%s\n", obj);
        assertThat( obj.getString("firstName"), is("John"));
        assertThat( obj.getString("lastName"), is("Smith"));
        assertThat( obj.getInt("age"), is(25));
        JsonObject address = obj.getJsonObject("address");
        assertNotNull(address);
        assertThat( address.getString("streetAddress"), is("21 2nd Street"));
        assertThat( address.getString("city"), is("New York"));
        assertThat( address.getString("state"), is("NY"));
        assertThat( address.getInt("postalCode"), is(10021));
        JsonArray phones = obj.getJsonArray("phoneNumbers");
        assertNotNull(phones);
        assertThat(phones.size(), is(2));
        assertThat(phones.getJsonObject(0).getString("type"), is("home"));
        assertThat(phones.getJsonObject(0).getString("number"), is("212 555-1234"));
        assertThat(phones.getJsonObject(1).getString("type"), is("fax"));
        assertThat(phones.getJsonObject(1).getString("number"), is("646 555-4567"));
    }

    @Test
    public void shouldWriteJSON() {
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonObjectBuilder builder = factory.createObjectBuilder();
        JsonObject obj =
        builder.add("artist", "Daft Punk")
            .add("album", "Random Access Memories")
            .add("year", 2013)
            .add("collaborators",
                factory.createArrayBuilder()
                .add( factory.createObjectBuilder()
                    .add("firstName", "Nile")
                    .add("lastName", "Rodgers")
                    .build())
                .add( factory.createObjectBuilder()
                    .add("firstName", "Giorgio")
                    .add("lastName", "Moroder")
                    .build())
                .build()
            )
            .build();

        Map<String,Object> properties = new HashMap<String, Object>() {{
            put(JsonGenerator.PRETTY_PRINTING, true);
        }};
        StringWriter swriter = new StringWriter();
        try (JsonWriter writer =
                 Json.createWriterFactory(null /*properties*/)
                         .createWriter(swriter)) {
            writer.writeObject(obj);
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
