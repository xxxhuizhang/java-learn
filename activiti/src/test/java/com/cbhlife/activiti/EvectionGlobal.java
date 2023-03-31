package com.cbhlife.activiti;


import com.cbhlife.activiti.pojo.Evection;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试流程变量
 */
public class EvectionGlobal {

    private static String processDefinitionKey = "evection-global";

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

    private Task getUserTask(String assingee) {
        return taskService.createTaskQuery()
                .processDefinitionKey(processDefinitionKey)
                .taskAssignee(assingee)
                .singleResult();
    }


    @Test
    public void testDeployment() {

        Deployment deploy = repositoryService.createDeployment()
                .name("出差申请流程-variables1")
                .addClasspathResource("bpmn/evection-global.bpmn")
                .deploy();

        System.out.println("流程部署id=" + deploy.getId());
        System.out.println("流程部署名字=" + deploy.getName());
    }

    /**
     * 启动流程 的时候设置流程变量
     * 设置流程变量num
     * 设置任务负责人
     */
    @Test
    public void testStartProcess() {

        Map<String, Object> variables = new HashMap<>();
        Evection evection = new Evection();
        evection.setNum(3d);

        variables.put("evection", evection);
        variables.put("assignee0", "李四");
        variables.put("assignee1", "王经理");
        variables.put("assignee2", "杨总经理");
        variables.put("assignee3", "张财务");

        runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
    }


    /**
     * <userTask activiti:assignee="${assignee0}" activiti:exclusive="true" id="_3" name="创建出差申请"/>
     * <userTask activiti:assignee="${assignee1}" activiti:exclusive="true" id="_4" name="部门经理审核"/>
     * <userTask activiti:assignee="${assignee2}" activiti:exclusive="true" id="_5" name="总经理审批"/>
     * <userTask activiti:assignee="${assignee3}" activiti:exclusive="true" id="_6" name="财务审批"/>
     */
    @Test
    public void completTask1() {

        String assingee1 = "李四1";
        String assingee2 = "王经理1";
        String assingee3 = "杨总经理1";
        String assingee4 = "张财务1";

        final Task task = getUserTask(assingee1);


        if (task != null) {
            taskService.complete(task.getId());
            //taskService.complete(task.getId(),map); //自定义的变量map可以在这里传
            System.out.println(task.getId() + "----任务已完成");
        }
    }

    @Test
    public void completTask2() {

        String assingee1 = "李四2";
        String assingee2 = "王经理2";
        String assingee3 = "杨总经理2";
        String assingee4 = "张财务2";

        Task task = taskService.createTaskQuery()
                .processDefinitionKey(processDefinitionKey)
                .taskAssignee(assingee1)
                .singleResult();

        if (task != null) {
            taskService.complete(task.getId());
            //taskService.complete(task.getId(),map); //自定义的变量map可以在这里传
            System.out.println(task.getId() + "----任务已完成");
        }
    }


    @Test
    public void completTask3() {

        String assingee1 = "李四3";
        String assingee2 = "王经理3";
        String assingee3 = "杨总经理3";
        String assingee4 = "张财务3";

        Evection evection = new Evection();
        evection.setNum(2d);
        Map<String, Object> map = new HashMap<>();
        map.put("evection", evection);

        Task task = taskService.createTaskQuery()
                .processDefinitionKey(processDefinitionKey)
                .taskAssignee(assingee1)
                .singleResult();

        if (task != null) {
            taskService.complete(task.getId(), map);
            System.out.println(task.getId() + "----任务已完成");
        }

    }


}
