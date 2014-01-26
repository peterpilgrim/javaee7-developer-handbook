package je7hb.intro.xentracker.control;

import je7hb.intro.xentracker.boundary.ProjectTaskService;
import je7hb.intro.xentracker.entity.Project;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import java.util.List;

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
        System.out.printf("===>>> %s.init() called id=%d\n", this.getClass().getSimpleName(), id );
        if (id <= 0) {
            String message = "Bad request. Please use a link from within the system.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            return;
        }

        Project project = service.findProjectById(id).get(0);

        if (project == null) {
            String message = "Bad request. Unknown user.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
        }
        name = project.getName();
        headline = project.getHeadline();
        description = project.getDescription();
    }

    public String cancel() {
        System.out.printf("%s.cancel() called\n", this.getClass().getSimpleName());
        return "index?faces-redirect=true";
    }

    public String viewEdit(int projectId) {
        System.out.printf("===>>> %s.viewEdit( projectId=%d ) called\n", this.getClass().getSimpleName(), projectId);

        Project project = service.findProjectById(projectId).get(0);
        id = project.getId();
        name = project.getName();
        headline = project.getHeadline();
        description = project.getDescription();
        System.out.printf("===>>> project = %s\n", project);

        String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

        return contextPath +"/views/editProject.xhtml";
    }

    public String saveEdit() {
        System.out.printf("===>>> %s.saveEdit() called\n", this.getClass().getSimpleName());

        Project project = service.findProjectById(id).get(0);
        project.setName(name);
        project.setHeadline(headline);
        project.setDescription(description);

        service.updateProject(project);
        System.out.printf("===>>> project = %s\n", project);

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
