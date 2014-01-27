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

@ManagedBean(name="projectTaskListController")
@ViewScoped
public class ProjectTaskListController {

    @Inject ProjectTaskService service;

    private int id;
    private Project project;

    public void findProjectById() {
        System.out.printf("===>>> %s.init() called id=%d\n", this.getClass().getSimpleName(), id );
        if (id <= 0) {
            String message = "Bad request. Please use a link from within the system.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            return;
        }

        project = service.findProjectById(id).get(0);
        if (project == null) {
            String message = "Bad request. Unknown user.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
        }
    }

    // Unable to find matching navigation case with from-view-id '/index.xhtml' for action '#{projectViewController.createNewProject}' with outcome 'create-project'
    public String navigateNewTask() {
        System.out.printf("%s.navigateNewTask() called\n", this.getClass().getSimpleName());
        return "createTask?faces-redirect=true";
    }

    public String cancel() {
        return "projectTaskList?faces-redirect=true";
    }

    public String returnToProjects() {
        return "index?faces-redirect=true";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
