package com.cbhlife.activiti;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Evection {

    private static String processDefinitionKey = "myEvection";

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
        Deployment deploy = repositoryService.createDeployment()
                //.tenantId().category()
                .name("出差申请流程")
                .addClasspathResource("bpmn/evection.bpmn")
                .addClasspathResource("bpmn/evection.png")
                .deploy();

        System.out.println("流程部署id=" + deploy.getId());
        System.out.println("流程部署名字=" + deploy.getName());
    }

    /**
     * 启动流程实例
     * `ACT_HI_VARINST`     流程任务的历史变量
     * `act_hi_taskinst`     流程任务的历史信息
     * `act_hi_procinst`     流程实例的历史信息
     * `act_hi_actinst`      流程实例执行历史信息
     * `act_hi_identitylink` 流程参与用户的历史信息
     * `act_ru_execution`    流程执行信息
     * `act_ru_task`         流程当前任务信息
     * `act_ru_identitylink`  流程的正在参与用户信息
     * `ACT_RU_VARIABLE`      流程的运行时变量
     *
     */
    @Test
    public void testStartProcess() {

        String businessKey = "businessKey"; // 添加业务主键 key

        Map<String, Object> variables = new HashMap<>();
        variables.put("assignee", "赵财务");
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);

        System.out.println("流程定义ID：" + instance.getProcessDefinitionId());
        System.out.println("流程实例ID：" + instance.getId());
        System.out.println("当前活动的ID：" + instance.getActivityId());
    }

    /**
     * 查询个人待执行的任务
     */
    @Test
    public void testFindPersonalTaskList() {

        List<Task> taskList = taskService.createTaskQuery()
                .processDefinitionKey(processDefinitionKey) //流程Key
                .taskAssignee("zhangsan")  //要查询的负责人
                .list();

        for (Task task : taskList) {
            printTaskLog(task);
        }
    }


    /**
     * 完成 zhangsan 个人任务
     * 创建出差申请
     * <p>
     * select distinct RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_
     * WHERE RES.ASSIGNEE_ = "zhangsan" and D.KEY_ = "myEvection" order by RES.ID_ asc
     * <p>
     * insert into ACT_HI_TASKINST
     * insert into ACT_HI_ACTINST
     * insert into ACT_HI_IDENTITYLINK
     * insert into ACT_RU_TASK
     * insert into ACT_RU_IDENTITYLINK
     * <p>
     * update ACT_HI_TASKINST
     * update ACT_HI_ACTINST
     * update ACT_RU_EXECUTION
     */
    @Test
    public void completTask1() {
        //获取 zhangsan - myEvection 对应的任务
        Task task = taskService.createTaskQuery()
                .processDefinitionKey(processDefinitionKey)
                .taskAssignee("zhangsan")
                .singleResult();
        completeTask(taskService, task);

    }

    /**
     * 完成 jerry2 个人任务
     * 经理审批
     */
    @Test
    public void completTask2() { // 使用 MyTaskListener 设置办理人
        Task task = taskService.createTaskQuery()
                .processDefinitionKey(processDefinitionKey)
                .taskAssignee("jerry2") //在 MyTaskListener 里设置的 jerry2
                .singleResult();
        completeTask(taskService, task);
    }

    /**
     * 候选人拾取、归还任务
     */
    @Test
    public void claimTask() { // jack,peter
        String candidateUser = "jack";
        Task task = taskService.createTaskQuery()
                .processDefinitionKey(processDefinitionKey)
                .taskCandidateUser(candidateUser)
                .singleResult();//.list()

        if (task != null) {
            taskService.claim(task.getId(), candidateUser); //拾取任务
            //taskService.setAssignee(task.getId(), null);  //归还任务
            //taskService.setAssignee(task.getId(), "mike");  //交接任务
            printTaskLog(task);
        }
    }

    /**
     * 完成 jack 个人任务
     * 总经理审批
     */
    @Test
    public void completTask3() {
        //获取 jack - myEvection 对应的任务
        Task task = taskService.createTaskQuery()
                .processDefinitionKey(processDefinitionKey)
                .taskAssignee("jack")
                .singleResult();
        completeTask(taskService, task);
    }

    /**
     * 完成 赵财务 个人任务
     * 财务审批
     */
    @Test
    public void completTask4() { //通过 流程变量设置 处理人 赵财务
        //获取 rose - myEvection 对应的任务
        Task task = taskService.createTaskQuery()
                .processDefinitionKey(processDefinitionKey)
                .taskAssignee("赵财务")
                .singleResult();
        completeTask(taskService, task);
    }


    /**
     * 全部流程实例的 挂起和 激活
     * suspend 暂停
     * <p>
     * ACT_RE_PROCDEF  SUSPENSION_STATE_ 1 激活 2 挂起
     * ACT_RU_TASK    SUSPENSION_STATE_  1 激活 2 挂起
     * ACT_RU_EXECUTION    SUSPENSION_STATE_  1 激活 2 挂起
     */
    @Test
    public void suspendAllProcessInstance() {

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey)
                .singleResult();

        boolean suspended = processDefinition.isSuspended();
        String definitionId = processDefinition.getId();

        if (suspended) { // 参数1：流程定义id 参数2：是否暂停 参数3 ：暂停的时间
            repositoryService.activateProcessDefinitionById(definitionId, true, null);
            System.out.println("流程定义id:" + definitionId + "，已激活");
        } else {
            repositoryService.suspendProcessDefinitionById(definitionId, true, null);
            System.out.println("流程定义id:" + definitionId + "，已挂起");
        }
    }

    /**
     * 挂起、激活单个流程实例
     * <p>
     * ACT_RU_TASK    SUSPENSION_STATE_  1 激活 2 挂起
     * ACT_RU_EXECUTION    SUSPENSION_STATE_  1 激活 2 挂起
     */
    @Test
    public void suspendSingleProcessInstance() {

        ProcessInstance instance = runtimeService.createProcessInstanceQuery()
                .processInstanceId("2501")
                .singleResult();

        boolean suspended = instance.isSuspended(); //得到当前流程实例的暂停状态
        String instanceId = instance.getId();

        if (suspended) { // 如果已经暂停，就执行激活
            runtimeService.activateProcessInstanceById(instanceId);
            System.out.println("流程实例id:" + instanceId + "已经激活");
        } else { // 如果已经激活，就执行激活暂停
            runtimeService.suspendProcessInstanceById(instanceId);
            System.out.println("流程实例id:" + instanceId + "已经暂停");
        }
    }

    private void completeTask(TaskService taskService, Task task) {
        printTaskLog(task);
        taskService.complete(task.getId());
    }

    private void printTaskLog(Task task) {
        System.out.println("任务Id=" + task.getId());
        System.out.println("任务名称=" + task.getName());
        System.out.println("任务办理人=" + task.getAssignee());
        System.out.println("任务创建时间=" + task.getClaimTime());
        System.out.println("流程实例id=" + task.getProcessInstanceId());
        System.out.println("执行对象id=" + task.getExecutionId());
        System.out.println("流程定义id=" + task.getProcessDefinitionId());
        System.out.println("\n----------------------\n");
    }


}
