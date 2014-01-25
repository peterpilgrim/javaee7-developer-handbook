package je7hb.intro.xentracker.control;

import je7hb.intro.xentracker.boundary.ProjectTaskService;
import je7hb.intro.xentracker.entity.Project;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import java.util.List;

/**
 * The type ProjectViewController
 *
 * @author Peter Pilgrim
 */

@ManagedBean(name="projectViewController")
@RequestScoped
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

    public void chooseBookFromLink(ActionEvent event) {
        String current = event.getComponent().getId();
        FacesContext context = FacesContext.getCurrentInstance();
//        String bookId = books.get(current);
//        context.getExternalContext().getSessionMap().put("bookId", bookId);
    }

    // Unable to find matching navigation case with from-view-id '/index.xhtml' for action '#{projectViewController.createNewProject}' with outcome 'create-project'
    public String navigateNewProject() {
        System.out.printf("%s.navigateNewProject() called\n", this.getClass().getSimpleName());
        return "createProject?faces-redirect=true";
    }

    // Unable to find matching navigation case with from-view-id '/index.xhtml' for action '#{projectViewController.createNewProject}' with outcome 'create-project'
    public String createNewProject() {
        System.out.printf("%s.createNewProject() called\n", this.getClass().getSimpleName());
        Project project = new Project(name, headline, description);
        service.saveProject(project);
        name = null;
        headline = null;
        description = null;
        return "index?faces-redirect=true";
    }

    public String cancel() {
        System.out.printf("%s.cancel() called\n", this.getClass().getSimpleName());
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
