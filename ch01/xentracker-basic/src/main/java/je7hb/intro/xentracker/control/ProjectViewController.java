package je7hb.intro.xentracker.control;

import je7hb.intro.xentracker.boundary.ProjectTaskService;
import je7hb.intro.xentracker.entity.Project;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import java.util.List;

/**
 * The type ProjectViewController
 *
 * @author Peter Pilgrim
 */

@ManagedBean(name="projectViewController")
@SessionScoped
public class ProjectViewController {

    @Inject ProjectTaskService service;

    private int id;
    private String name;
    private String headline;
    private String description;

    public List<Project> getProjectList() {
        List<Project> projects = service.findAllProjects();
        return projects;
    }

    public String navigateNewProject() {
        return "createProject?faces-redirect=true";
    }

    public String createNewProject() {
        Project project = new Project(name, headline, description);
        service.saveProject(project);
        name = null;
        headline = null;
        description = null;
        return "index?faces-redirect=true";
    }

    public String cancel() {
        return "index?faces-redirect=true";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
