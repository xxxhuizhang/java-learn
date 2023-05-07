package com.cbhlife.activiti.cmd;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.history.HistoryManager;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManager;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntityManager;

import java.util.List;

public class JumpTaskCmd implements Command<Void> {

    private String taskId;

    public JumpTaskCmd(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public Void execute(CommandContext commandContext) {

        ProcessEngineConfigurationImpl processEngineConfiguration = commandContext.getProcessEngineConfiguration();
        RepositoryService repositoryService = processEngineConfiguration.getRepositoryService();
        TaskService taskService = processEngineConfiguration.getTaskService();

        TaskEntityManager taskEntityManager = commandContext.getTaskEntityManager();
        ExecutionEntityManager executionEntityManager = commandContext.getExecutionEntityManager();
        HistoryManager historyManager = commandContext.getHistoryManager();

        TaskEntity currentTask = taskEntityManager.findById(taskId);



        //获取上一个节点的任务定义
        BpmnModel bpmnModel = repositoryService.getBpmnModel(currentTask.getProcessDefinitionId());
        FlowNode previousNode = (FlowNode) bpmnModel.getFlowElement(currentTask.getTaskDefinitionKey());

        // 获取上一个节点的历史记录
        List<HistoricTaskInstance> historicTaskInstances = commandContext.getHistoricTaskInstanceEntityManager()
                .findHistoricTasksByProcessInstanceId(currentTask.getProcessInstanceId());
        HistoricTaskInstance previousTask = null;

        for (HistoricTaskInstance historicTaskInstance : historicTaskInstances) {
            if (historicTaskInstance.getTaskDefinitionKey().equals(previousNode.getId())) {
                previousTask = historicTaskInstance;
                break;
            }
        }

        // 更新流程实例状态

        ExecutionEntity executionEntity = executionEntityManager.findById(currentTask.getExecutionId());
        executionEntity.setCurrentFlowElement(previousNode);

        // 更新任务状态
        currentTask.setEndTime(null);
        currentTask.setExecutionId(previousTask.getExecutionId());
        currentTask.setTaskDefinitionKey(previousTask.getTaskDefinitionKey());
        currentTask.setName(previousTask.getName());
        currentTask.setDescription(previousTask.getDescription());
        currentTask.setAssignee(previousTask.getAssignee());
        currentTask.setOwner(previousTask.getOwner());
        currentTask.setPriority(previousTask.getPriority());
        currentTask.setDueDate(previousTask.getDueDate());
        currentTask.setFollowUpDate(previousTask.getFollowUpDate());

        taskEntityManager.save(currentTask);

        return null;
    }
}