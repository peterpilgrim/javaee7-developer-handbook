package je7hb.intro.xentracker.init;

import je7hb.intro.xentracker.boundary.ProjectTaskService;
import je7hb.intro.xentracker.entity.Project;
import je7hb.intro.xentracker.entity.Task;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
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

    @Resource
    TimerService timerService;

    private boolean initialized = false;

    public static Date getFutureRandomDate() {
        return getFutureRandomDate( new Date() );
    }

    public static Date getFutureRandomDate( Date now ) {
        int days = (int)(Math.random() * 14 + 1);
        return new Date( days * 86400L * 1000L + now.getTime());
    }

    @PostConstruct
    public void initialise() {
        // FIXME: GlassFish 4.1 / Payara - Get around the issues of initialisation. It appears the transaction manager and entity manager are not ready by the time a singleton starts up! We use a EJB timer workaround.
        System.out.printf("***** %s.initialise() projectTaskService=%s, timerService=%s\n", getClass().getSimpleName(), projectTaskService, timerService);
        final ScheduleExpression expression = new ScheduleExpression().second("*/10").minute("*").hour("*");
        timerService.createCalendarTimer(expression);
    }

    @Timeout
    public void createInitialProjectData(Timer timer) {
        if (initialized) {
            return;
        }
        initialized = true;

        System.out.printf("***** %s.createInitialProjectData() projectTaskService=%s, initialized=%s, timer=%s\n",
                getClass().getSimpleName(), projectTaskService, initialized, timer );

        final Date p = getFutureRandomDate();
        final Date q = getFutureRandomDate(p);
        final Date r = getFutureRandomDate(q);

        final Project project1 = new Project("Technology Presentation",
            "Demonstration of the Milestone 1",
            "Show off the project to the key stakeholders and decision makers");
        project1.addTask( new Task("Design concept", getFutureRandomDate(), true ));
        project1.addTask( new Task("Write slides", p, false ));
        project1.addTask( new Task("Talk to the organizer", q, false ));
        project1.addTask( new Task("Prepare demos", r, false ));

        projectTaskService.saveProject(project1);

        final Project project2 = new Project("Family Birthday",
            "Grandparent Birthday",
            "important anniversary so tell immediate relatives not to miss it!");
        project2.addTask( new Task("Secretly find out gifts", getFutureRandomDate(), true ));
        project2.addTask( new Task("Buy gifts", getFutureRandomDate(), false ));
        project2.addTask( new Task("Buy birthday card", getFutureRandomDate(), true ));
        project2.addTask( new Task("Organize the party", getFutureRandomDate(), false ));

        projectTaskService.saveProject(project2);

        final Project project3 = new Project("Business Report",
                "Important phase in our sales strategy",
                "Renegotiate the principal account with the enrolled customer.");
        final Date x = getFutureRandomDate();
        final Date y = getFutureRandomDate(x);
        final Date z = getFutureRandomDate(y);
        project3.addTask( new Task("Set up a meeting", x, true ));
        project3.addTask( new Task("Hold the meeting", y, false ));
        project3.addTask( new Task("Write the report", z, false ));
        project3.addTask( new Task("Furnish report to the boss", getFutureRandomDate(z), false ));

        projectTaskService.saveProject(project3);
    }
}
