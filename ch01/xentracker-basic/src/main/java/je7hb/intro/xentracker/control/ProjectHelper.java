package je7hb.intro.xentracker.control;

import je7hb.intro.xentracker.entity.Project;
import je7hb.intro.xentracker.entity.Task;

import javax.json.stream.JsonGenerator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * The type ProjectHelper
 *
 * @author Peter Pilgrim
 */
public class ProjectHelper {
    static SimpleDateFormat FMT =
            new SimpleDateFormat("dd-MMM-yyyy");
    static SimpleDateFormat FMT2 =
            new SimpleDateFormat("yyyy-MM-dd");

    public static JsonGenerator generateProjectsAsJson( JsonGenerator generator, List<Project> projects ) {
        generator.writeStartArray();
        for ( Project project: projects ) {
            writeProjectAsJson(generator, project);
        }
        generator.writeEnd().close();
        return generator;
    }

    public static JsonGenerator writeProjectAsJson( JsonGenerator generator, Project project ) {
        generator.writeStartObject()
                .write("id", project.getId())
                .write("name", project.getName());
        if ( project.getHeadline() != null )
            generator.write("headline", project.getHeadline());
        if ( project.getDescription() != null )
            generator.write("description", project.getDescription());
        generator.writeStartArray("tasks");

        for ( Task task: project.getTasks()) {
            writeTaskAsJson(generator,task);
        }
        generator.writeEnd().writeEnd();
        return generator;
    }

    public static JsonGenerator writeTaskAsJson( JsonGenerator generator, Task task ) {
        generator.writeStartObject()
                .write("id", task.getId())
                .write("name", task.getName())
                .write("targetDate",
                        task.getTargetDate() == null ? "" :
                                FMT2.format(task.getTargetDate()))
                .write("completed", task.isCompleted())
                .write("projectId",
                        task.getProject() != null ? task.getProject().getId() : 0 )
                .writeEnd();
        return generator;
    }

    public static boolean convertToBoolean( String value ) {
        return convertToBoolean(value, false);
    }

    public static boolean convertToBoolean( String value, boolean defaultValue ) {
        if ( value == null )
            return defaultValue;
        value = value.trim();
        if ( value.length() == 0 )
            return defaultValue;
        if ( "true".equalsIgnoreCase(value) ||
                "yes".equalsIgnoreCase(value) || "1".equalsIgnoreCase(value) )
            return true;
        if ( "false".equalsIgnoreCase(value) ||
                "no".equalsIgnoreCase(value) || "0".equalsIgnoreCase(value) )
            return false;
        return defaultValue;
    }

    public static Date convertToDate( String value ) {
        if ( value == null )
            return null;
        value = value.trim();
        if ( value.length() == 0 )
            return null;

        Date date = null;
        try {
            date = FMT.parse(value);
        }
        catch (ParseException pe1) {
            try {
                date = FMT2.parse(value);
            }
            catch (ParseException pe2) {
                throw new RuntimeException(
                        "unable to parse date ["+value+"]");
            }
        }

        return date;
    }
}
