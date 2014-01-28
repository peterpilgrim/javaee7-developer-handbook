package je7hb.intro.xentracker.control;

import je7hb.intro.xentracker.boundary.ProjectTaskService;
import je7hb.intro.xentracker.entity.Project;
import je7hb.intro.xentracker.entity.Task;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * The type ProjectViewController
 *
 * @author Peter Pilgrim
 */

@ManagedBean(name= "taskListViewController")
@ViewScoped
public class TaskListViewController {

    @Inject ProjectTaskService service;

    private int id;
    private Project project;
    private String name;
    private Date targetDate;
    private boolean completed;

    public void findProjectById() {
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

    public String navigateNewTask() {
        System.out.printf("%s.navigateNewTask() called\n", this.getClass().getSimpleName());
        return String.format("createTask?id=%d&faces-redirect=true", id);
    }

    public String cancel() {
        return String.format("projectTaskList?id=%d&faces-redirect=true", id);
    }

    public String returnToProjects() {
        return "index?faces-redirect=true";
    }

    public String createNewTask() {
        final Task task = new Task(name, targetDate, completed );
        project = service.findProjectById(id).get(0);
        project.addTask(task);
        service.updateProject(project);
        return String.format("projectTaskList?id=%d&faces-redirect=true", id );
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
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

    public Date getTargetDate() { return targetDate; }

    public void setTargetDate(Date targetDate) { this.targetDate = targetDate; }

    public boolean isCompleted() { return completed; }

    public void setCompleted(boolean completed) { this.completed = completed; }

}
