package com.cbhlife.activiti.cmd;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.Task;

public class RollbackPreviousTaskCommand implements Command<Void> {

    private String taskId;

    public RollbackPreviousTaskCommand(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public Void execute(CommandContext commandContext) {
        TaskService taskService = commandContext.getTaskService();
        RuntimeService runtimeService = commandContext.getProcessEngineConfiguration().getRuntimeService();

        // 获取当前任务所属的执行对象
        Task currentTask = taskService.createTaskQuery().taskId(taskId).singleResult();
        ExecutionEntity executionEntity = (ExecutionEntity) runtimeService.createExecutionQuery()
                .executionId(currentTask.getExecutionId()).singleResult();

        // 获取当前任务的前一个任务 ID，并获取该任务的实例
        String previousTaskId = executionEntity.getActivityInstanceIdHistory().get(executionEntity.getActivityInstanceIdHistory().size() - 2);
        TaskEntity previousTask = (TaskEntity) taskService.createTaskQuery().taskId(previousTaskId).singleResult();

        // 将当前任务的父任务 ID 设置为前一个任务的 ID
        ((TaskEntity) currentTask).setParentTaskId(previousTask.getId());
        taskService.saveTask(currentTask);

        return null;
    }
}
