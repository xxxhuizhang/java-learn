package com.cbhlife.activiti;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.CallActivity;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class SubActiviti {


    private RepositoryService repositoryService;
    private RuntimeService runtimeService;
    private TaskService taskService;


    @Before
    public void getRepositoryService() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        repositoryService = processEngine.getRepositoryService();
        runtimeService = processEngine.getRuntimeService();
        taskService = processEngine.getTaskService();
    }


    /**
     * 流程部署
     */
    @Test
    public void testDeployment() {
        Deployment deployment = repositoryService.createDeployment()
                .name("子流程")
                .addClasspathResource("main-process.bpmn")
                .addClasspathResource("sub-process.bpmn")
                .deploy();

        System.out.println("流程部署id=" + deployment.getId());
        System.out.println("流程部署名字=" + deployment.getName());
    }


    @Test
    public void sub() {

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("mainProcess");
        List<Task> tasks = taskService.createTaskQuery()
                .processInstanceId(processInstance.getId())
                .list();

        // 将 Call Activity 任务连接到当前任务
        BpmnModel model = repositoryService.getBpmnModel(deployment.getId());
        Process process = model.getProcesses().get(0);
        FlowElement currentTask = process.getFlowElement(tasks.get(0).getTaskDefinitionKey());
        CallActivity callActivity = new CallActivity();
        callActivity.setCalledElement("subProcess");
        SequenceFlow sequenceFlow = new SequenceFlow();
        sequenceFlow.setSourceRef(currentTask.getId());
        sequenceFlow.setTargetRef(callActivity.getId());
        process.addFlowElement(callActivity);
        process.addFlowElement(sequenceFlow);

        // 完成当前任务，触发 Call Activity 任务，并等待子流程完成
        taskService.complete(tasks.get(0).getId());
        tasks = taskService.createTaskQuery()
                .processInstanceId(processInstance.getId())
                .list();
        while (!tasks.isEmpty()) {
            Thread.sleep(1000);
            tasks = taskService.createTaskQuery()
                    .processInstanceId(processInstance.getId())
                    .list();
        }


    }


}
