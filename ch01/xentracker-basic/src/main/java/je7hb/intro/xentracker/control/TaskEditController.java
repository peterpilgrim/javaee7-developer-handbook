package je7hb.intro.xentracker.control;

import je7hb.intro.xentracker.boundary.ProjectTaskService;
import je7hb.intro.xentracker.entity.Project;
import je7hb.intro.xentracker.entity.Task;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.Date;

/**
 * The type ProjectViewController
 *
 * @author Peter Pilgrim
 */

@ManagedBean(name="taskEditController")
@ViewScoped
public class TaskEditController {

    @Inject ProjectTaskService service;

    private int taskId;
    private String name;
    private Date targetDate;
    private boolean completed;
    private Project taskProject;

    public void findTaskById() {
        if (taskId <= 0) {
            String message = "Bad request. Please use a link from within the system.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            return;
        }

        final Task task = service.findTaskById(taskId).get(0);
        if (task == null) {
            String message = "Bad request. Unknown task.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
        }
        name = task.getName();
        targetDate = task.getTargetDate();
        completed = task.isCompleted();
        taskProject = task.getProject();
    }

    public String cancel() {
        return String.format("projectTaskList?id=%d&faces-redirect=true", taskProject.getId() );
    }

    public String editTask() {
        final Task task = service.findTaskById(taskId).get(0);
        task.setName(name);
        task.setCompleted(completed);
        task.setTargetDate(targetDate);
        final Project project = task.getProject();
        service.updateProject(project);
        return String.format("projectTaskList?id=%d&faces-redirect=true", project.getId() );
    }

    public String removeTask() {
        final Task task = service.findTaskById(taskId).get(0);
        final Project project = task.getProject();
        task.getProject().removeTask(task);
        service.updateProject(project);
        return String.format("projectTaskList?id=%d&faces-redirect=true", project.getId() );
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
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

    public Project getTaskProject() { return taskProject; }

    public void setTaskProject(Project taskProject) { this.taskProject = taskProject; }
}
