package com.cbhlife.activiti;

import com.cbhlife.activiti.pojo.Evection;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class EvectionParallel {

    private static String processDefinitionKey = "parallel";

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


    @Test
    public void testDeployment() {

        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("bpmn/evection-parallel.bpmn") // 添加bpmn资源
                //.addClasspathResource("bpmn/evection.png")  // 添加png资源
                .name("出差申请流程-并行网关")
                .deploy();
        System.out.println("流程部署id：" + deployment.getId());
        System.out.println("流程部署名称：" + deployment.getName());
    }

    @Test
    public void startProcess() {

        Map<String, Object> map = new HashMap<>();
        Evection evection = new Evection();
        evection.setNum(4d);
        map.put("evection", evection);

        ProcessInstance processInstance = runtimeService
                .startProcessInstanceByKey(processDefinitionKey, map);

        System.out.println("流程实例名称=" + processInstance.getName());
        System.out.println("流程定义id：" + processInstance.getProcessDefinitionId());
        System.out.println("流程实例id：" + processInstance.getId());
        System.out.println("当前活动Id：" + processInstance.getActivityId());
    }

    @Test
    public void completTask() {

        String assingee1 = "tom"; //出差申请
        String assingee21 = "jerry"; //技术经理
        String assingee22 = "jack";//项目经理
        String assingee3 = "rose";//总经理

        Task task = taskService.createTaskQuery()
                .processDefinitionKey(processDefinitionKey)
                .taskAssignee(assingee3)
                .singleResult();
        if (task != null) {
            taskService.complete(task.getId());
        }

    }
}
