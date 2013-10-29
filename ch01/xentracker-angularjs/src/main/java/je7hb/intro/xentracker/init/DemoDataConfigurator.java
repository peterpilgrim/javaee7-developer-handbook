package je7hb.intro.xentracker.init;

import je7hb.intro.xentracker.boundary.ProjectTaskService;
import je7hb.intro.xentracker.entity.Project;
import je7hb.intro.xentracker.entity.Task;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.transaction.UserTransaction;
import java.util.Date;

/**
 * The type DemoDataConfigurator
 *
 * @author Peter Pilgrim
 */
@Startup
@Singleton
public class DemoDataConfigurator {
    @EJB
    ProjectTaskService projectTaskService;

    public static Date getFutureRandomDate() {
        return getFutureRandomDate( new Date() );
    }

    public static Date getFutureRandomDate( Date now ) {
        int days = (int)(Math.random() * 14 + 1);
        return new Date( days * 86400L * 1000L + now.getTime());
    }

    @PostConstruct
    public void createInitialProjectData() {
        System.out.printf("%s.createInitialProjectData() projectTaskService=%s\n",
                getClass().getSimpleName(),
                projectTaskService );

        Date p = getFutureRandomDate();
        Date q = getFutureRandomDate(p);
        Date r = getFutureRandomDate(q);

//        Project project1 = new Project("Technology Presentation",
//            "Demonstration of the Milestone 1",
//            "Show off the project to the key stakeholders and decision makers");
//        project1.addTask( new Task("Design concept", getFutureRandomDate(), true ));
//        project1.addTask( new Task("Write slides", p, false ));
//        project1.addTask( new Task("Talk to the organizer", q, false ));
//        project1.addTask( new Task("Prepare demos", r, false ));
//
//        projectTaskService.saveProject(project1);


//        Project project2 = new Project("Family Birthday",
//            "Grandparent Birthday",
//            "important anniversary so tell immediate relatives not to miss it!");
//        project2.addTask( new Task("Secretly find out gifts", getFutureRandomDate(), true ));
//        project2.addTask( new Task("Buy gifts", getFutureRandomDate(), false ));
//        project2.addTask( new Task("Buy birthday card", getFutureRandomDate(), true ));
//        project2.addTask( new Task("Organize the party", getFutureRandomDate(), false ));
//
//        projectTaskService.saveProject(project2);
//
//        Project project3 = new Project("Business Report",
//                "Important phase in our sales strategy",
//                "Renegotiate the principal account with the enrolled customer.");
//        Date x = getFutureRandomDate();
//        Date y = getFutureRandomDate(x);
//        Date z = getFutureRandomDate(y);
//        project3.addTask( new Task("Set up a meeting", x, true ));
//        project3.addTask( new Task("Hold the meeting", y, false ));
//        project3.addTask( new Task("Write the report", z, false ));
//        project3.addTask( new Task("Furnish report to the boss", getFutureRandomDate(z), false ));
//
//        projectTaskService.saveProject(project3);
    }
}
