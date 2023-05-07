package com.cbhlife.activiti.cmd;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.Task;

import java.util.List;

public class RollbackLastTaskCommand implements Command<Void> {

    private String taskId;

    public RollbackLastTaskCommand(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public Void execute(CommandContext commandContext) {

        ProcessEngineConfigurationImpl processEngineConfiguration = commandContext.getProcessEngineConfiguration();
        TaskService taskService = processEngineConfiguration.getTaskService();

        String processInstanceId = taskService.createTaskQuery().taskId(taskId).singleResult().getProcessInstanceId();
        HistoryService historyService = commandContext.getProcessEngineConfiguration().getHistoryService();

        // 查询当前流程实例的所有历史任务
        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByHistoricTaskInstanceEndTime().desc()
                .list();

        HistoricTaskInstance lastCompletedTask = null;
        // 找到最后一个已完成的任务
        for (HistoricTaskInstance task : historicTaskInstances) {
            if (task.getEndTime() != null) {
                lastCompletedTask = task;
                break;
            }
        }

        if (lastCompletedTask != null) {
            String parentTaskId = lastCompletedTask.getParentTaskId();
            Task currentTask = taskService.createTaskQuery().taskId(taskId).singleResult();

            // 更新当前任务的父任务 ID
            ((TaskEntity) currentTask).setParentTaskId(parentTaskId);
            taskService.saveTask(currentTask);
        }
        return null;
    }
}
