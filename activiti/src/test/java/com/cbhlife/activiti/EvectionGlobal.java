package com.cbhlife.activiti;


import com.cbhlife.activiti.pojo.Evection;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
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

    private Task getUserTask(String assingee, String processInstanceId) {
        return taskService.createTaskQuery()
                .processDefinitionKey(processDefinitionKey)
                .processInstanceId(processInstanceId)
                .taskAssignee(assingee)
                .singleResult();
    }


    @Test
    public void testDeployment() {

        Deployment deploy = repositoryService.createDeployment()
                .name("出差申请流程-evection-global")
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

        for (int i = 1; i <= 3; i++) {

            Map<String, Object> variables = new HashMap<>();
            Evection evection = new Evection();
            evection.setNum(3d);

            variables.put("evection", evection);
            variables.put("assignee1", "李四" + i);
            variables.put("assignee2", "王经理" + i);
            variables.put("assignee3", "杨总经理" + i);
            variables.put("assignee4", "张财务" + i);

            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);

            System.out.println("流程定义ID：" + processInstance.getProcessDefinitionId());
            System.out.println("流程定义：" + processInstance.getProcessDefinitionKey());
            System.out.println("流程定义名称：" + processInstance.getProcessDefinitionName());
            System.out.println("流程实例ID：" + processInstance.getId());
            System.out.println("流程实例ID：" + processInstance.getProcessInstanceId());
            System.out.println("流程业务主键：" + processInstance.getBusinessKey());
            System.out.println("流程部署ID：" + processInstance.getDeploymentId());
            System.out.println("--------------\n");


        }

    }


    /**
     * select distinct RES.*
     * from ACT_RU_TASK RES
     * inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_
     * WHERE RES.ASSIGNEE_ = ?
     * and RES.PROC_INST_ID_ = ?
     * and D.KEY_ = ?
     * order by RES.ID_ asc
     */
    @Test
    public void completTask1() {

        String assingee1 = "李四1";
        String assingee2 = "王经理1";
        String assingee3 = "杨总经理1";
        String assingee4 = "张财务1";

        String processInstanceId = "90001";
        Task task = getUserTask(assingee1, processInstanceId);

        if (task != null) {
            taskService.complete(task.getId());
            //taskService.complete(task.getId(),map); //自定义的变量map可以在这里传
            System.out.println(task.getId() + "----任务已完成");
        }

        UserTask userTask = (UserTask) task;
        String documentation = userTask.getDocumentation();
        List<SequenceFlow> outgoingFlows = userTask.getOutgoingFlows();

        for (SequenceFlow outgoingFlow : outgoingFlows) {
            String conditionExpression = outgoingFlow.getConditionExpression();

        }


    }

    @Test
    public void completTask2() {

        String assingee1 = "李四2";
        String assingee2 = "王经理2";
        String assingee3 = "杨总经理2";
        String assingee4 = "张财务2";

        String processInstanceId = "90014";
        Task task = getUserTask(assingee1, processInstanceId);

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

        String processInstanceId = "90027";
        Task task = getUserTask(assingee1, processInstanceId);

        if (task != null) {
            taskService.complete(task.getId(), map);
            System.out.println(task.getId() + "----任务已完成");
        }


    }


}
