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

public class EvectionInclusive {

    private static String processDefinitionKey = "inclusive";

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
                .addClasspathResource("bpmn/evection-inclusive.bpmn") // 添加bpmn资源
                //.addClasspathResource("bpmn/evection.png")  // 添加png资源
                .name("出差申请流程-包含网关")
                .deploy();
        System.out.println("流程部署id：" + deployment.getId());
        System.out.println("流程部署名称：" + deployment.getName());
    }

    /**
     * 启动流程实例,设置流程变量的值
     */
    @Test
    public void startProcess() {

        Map<String, Object> map = new HashMap<>();
        Evection evection = new Evection();
        evection.setNum(4d);
        map.put("evection", evection);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, map);

        System.out.println("流程实例名称=" + processInstance.getName());
        System.out.println("流程定义id==" + processInstance.getProcessDefinitionId());
    }


    /**
     * 技术经理审批：请假天数<3天
     * 项目经理审批：请假天>=3天
     * 人事经理审批：没有条件
     *
     * 人事经理审批 和技术或项目经理都批 才能把流程走完
     */
    @Test
    public void completTask() {

        String assingee1 = "tom"; //出差申请
        String assingee2 = "jerry"; //技术经理
        String assingee3 = "miki";//人事经理
        String assingee4 = "jack";//项目经理
        String assingee5 = "rose";//总经理

        Task task = taskService.createTaskQuery()
                .processDefinitionKey(processDefinitionKey)
                .taskAssignee(assingee5)
                .singleResult();
        if (task != null) {
            taskService.complete(task.getId());
        }

    }
}
