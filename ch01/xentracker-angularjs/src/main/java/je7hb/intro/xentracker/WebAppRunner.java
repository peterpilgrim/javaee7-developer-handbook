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

package je7hb.intro.xentracker;

import je7hb.common.webcontainer.embedded.glassfish.SimpleEmbeddedRunner;
import je7hb.intro.xentracker.boundary.ProjectTaskService;
import je7hb.intro.xentracker.control.ProjectRESTServerEndpoint;
import je7hb.intro.xentracker.entity.Project;
import je7hb.intro.xentracker.init.DemoDataConfigurator;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.impl.base.exporter.zip.ZipExporterImpl;

import java.io.File;
import java.util.Scanner;

public class WebAppRunner {
	public static void main(String args[]) throws Exception {
        System.out.println("=================================================");
        System.out.println("=================================================");
        System.out.println("=================================================");
		System.out.println("Start the Server with a ShrinkWrap build here!!!!");

        final WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "test.war")
            .addPackage(DemoDataConfigurator.class.getPackage())
            .addPackage(ProjectTaskService.class.getPackage())
            .addPackage(ProjectRESTServerEndpoint.class.getPackage())
            .addPackage(Project.class.getPackage())
            .addClasses(StringHelper.class)
            .addAsWebInfResource(
                new File("src/main/resources/META-INF/persistence.xml"),
                "classes/META-INF/persistence.xml")
            .addAsWebInfResource(
                new File("src/main/webapp/WEB-INF/glassfish-resources.xml"),
                "glassfish-resources.xml")
            .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
            .addAsWebInfResource(
                EmptyAsset.INSTANCE, "beans.xml");

        webArchive.merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class)
            .importDirectory("src/main/webapp").as(GenericArchive.class),
            "/", Filters.include(".*\\.(html|jsp|css|js|png|jpg|jpeg|gif)$"));

        System.out.println(webArchive.toString(true));

        final File warFile = new File(webArchive.getName());
        new ZipExporterImpl(webArchive).exportTo(warFile, true);
        final SimpleEmbeddedRunner runner =
            SimpleEmbeddedRunner.launchDeployWarFile(
                warFile, "xentracker", 8080);

        System.out.println("=================================================");
        System.out.println("=================================================");
        System.out.println("=================================================");

        System.out.print("\nHIT THE RETURN KEY >");
        final Scanner sc = new Scanner(System.in);
        while(!sc.nextLine().equals(""));
        System.out.println("\nStopping service ...");

        runner.stop();
	}
} 	

