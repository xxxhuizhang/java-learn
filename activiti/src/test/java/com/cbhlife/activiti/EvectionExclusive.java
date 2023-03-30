package com.cbhlife.activiti;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;
import com.cbhlife.activiti.pojo.Evection;

import java.util.HashMap;
import java.util.Map;

public class EvectionExclusive {

    private static String processDefinitionKey = "exclusive";

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
     * 部署流程定义
     */
    @Test
    public void testDeployment() {
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("bpmn/evection-exclusive.bpmn") // 添加bpmn资源
                //.addClasspathResource("bpmn/evection.png")  // 添加png资源
                .name("出差申请流程-排他网关")
                .deploy();

        System.out.println("流程部署id：" + deployment.getId());
        System.out.println("流程部署名称：" + deployment.getName());
    }

    /**
     * 启动流程实例,设置流程变量的值
     */
    @Test
    public void startProcess() {

        Map<String, Object> variables = new HashMap<>();
        Evection evection = new Evection();
        evection.setNum(2d);
        variables.put("evection", evection);

        //启动流程实例，并设置流程变量的值
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceByKey(processDefinitionKey, variables);

        System.out.println("流程实例名称=" + processInstance.getName());
        System.out.println("流程定义id==" + processInstance.getProcessDefinitionId());
    }

    @Test
    public void completTask() {

        String assingee1 = "tom"; //第一步 填写出差单
        String assingee2 = "jerry"; //第二步 部门经理审批
        String assingee3 = "rose"; //第三步 财务审批

        Task task = taskService.createTaskQuery()
                .processDefinitionKey(processDefinitionKey)
                .taskAssignee(assingee3)
                .singleResult();

        if (task != null) {
            //根据任务id来   完成任务
            taskService.complete(task.getId());
        }
    }


}
