package je7hb.intro.xentracker.control;

import je7hb.intro.xentracker.boundary.ProjectTaskService;
import je7hb.intro.xentracker.entity.Project;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 * The type ProjectViewController
 *
 * @author Peter Pilgrim
 */

@ManagedBean(name="projectEditController")
@ViewScoped
public class ProjectEditController {

    @Inject ProjectTaskService service;

    private int id;
    private String name;
    private String headline;
    private String description;

    public void findProjectById() {
        if (id <= 0) {
            String message = "Bad request. Please use a link from within the system.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            return;
        }

        Project project = service.findProjectById(id).get(0);
        if (project == null) {
            String message = "Bad request. Unknown project.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
        }
        name = project.getName();
        headline = project.getHeadline();
        description = project.getDescription();
    }

    public String cancel() {
        return "index?faces-redirect=true";
    }

    public String editProject() {
        final Project project = service.findProjectById(id).get(0);
        project.setName(name);
        project.setHeadline(headline);
        project.setDescription(description);
        service.updateProject(project);

        return "index?faces-redirect=true";
    }

    public String removeProject() {
        final Project project = service.findProjectById(id).get(0);
        service.removeProject(project);

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
