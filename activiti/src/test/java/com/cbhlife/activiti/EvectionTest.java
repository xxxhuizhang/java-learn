package com.cbhlife.activiti;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

public class EvectionTest {

    /**
     * 流程部署
     */
    @Test
    public void testDeployment() {
//        1、创建ProcessEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        2、获取RepositoryServcie
        RepositoryService repositoryService = processEngine.getRepositoryService();
//        3、使用service进行流程的部署，定义一个流程的名字，把bpmn和png部署到数据中
        Deployment deploy = repositoryService.createDeployment()
                .name("出差申请流程")
                .addClasspathResource("bpmn/evection.bpmn")
                .addClasspathResource("bpmn/evection.png")
                .deploy();
//        4、输出部署信息
        System.out.println("流程部署id=" + deploy.getId());
        System.out.println("流程部署名字=" + deploy.getName());
    }

    /**
     * 启动流程实例
     * `act_hi_actinst`      流程实例执行历史信息
     * `act_hi_procinst`     流程实例的历史信息
     * `act_hi_taskinst`     流程任务的历史信息
     * `act_hi_identitylink` 流程参与用户的历史信息
     * `act_ru_execution`    流程执行信息
     * `act_ru_identitylink`  流程的正在参与用户信息
     * `act_ru_task`         流程当前任务信息
     */
    @Test
    public void testStartProcess() {
//        1、创建ProcessEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        2、获取RunTimeService
        RuntimeService runtimeService = processEngine.getRuntimeService();
//        3、根据流程定义的id启动流程
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("myEvection");
//        4、输出内容
        System.out.println("流程定义ID：" + instance.getProcessDefinitionId());
        System.out.println("流程实例ID：" + instance.getId());
        System.out.println("当前活动的ID：" + instance.getActivityId());
    }

    /**
     * 查询个人待执行的任务
     */
    @Test
    public void testFindPersonalTaskList() {
//        1、获取流程引擎
        TaskService taskService = getTaskService();
//        3、根据流程key 和 任务的负责人 查询任务
        List<Task> taskList = taskService.createTaskQuery()
                .processDefinitionKey("myEvection") //流程Key
                .taskAssignee("zhangsan")  //要查询的负责人
                .list();
//        4、输出
        for (Task task : taskList) {
            System.out.println("流程实例id=" + task.getProcessInstanceId());
            System.out.println("任务Id=" + task.getId());
            System.out.println("任务负责人=" + task.getAssignee());
            System.out.println("任务名称=" + task.getName());
        }
    }


    private void completeTaskAndLog(TaskService taskService, Task task) {
        System.out.println("流程实例id=" + task.getProcessInstanceId());
        System.out.println("任务Id=" + task.getId());
        System.out.println("任务负责人=" + task.getAssignee());
        System.out.println("任务名称=" + task.getName());
        //完成任务
        taskService.complete(task.getId());
    }

    private TaskService getTaskService() {
        //获取引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取操作任务的服务 TaskService
        return processEngine.getTaskService();
    }


    /**
     * 完成 zhangsan 个人任务
     * 创建出差申请
     *
     * select distinct RES.* from ACT_RU_TASK RES
     * inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_
     * WHERE RES.ASSIGNEE_ = "zhangsan" and D.KEY_ = "myEvection"
     * order by RES.ID_ asc
     */
    @Test
    public void completTask1() {
        TaskService taskService = getTaskService();
        //完成任务,参数：任务id,完成zhangsan的任务
        //第一种方法直接在数据库里查出id结束
        //taskService.complete("2505");

        //获取 zhangsan - myEvection 对应的任务
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("myEvection")
                .taskAssignee("zhangsan")
                .singleResult();
        completeTaskAndLog(taskService, task);

    }


    /**
     * 完成 jerry 个人任务
     * 经理审批
     */
    @Test
    public void completTask2() {
        //获取 jerry - myEvection 对应的任务
        TaskService taskService = getTaskService();
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("myEvection")
                .taskAssignee("jerry")
                .singleResult();
        completeTaskAndLog(taskService, task);
    }

    /**
     * 完成 jack 个人任务
     * 总经理审批
     */
    @Test
    public void completTask3() {
        //获取 jack - myEvection 对应的任务
        TaskService taskService = getTaskService();
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("myEvection")
                .taskAssignee("jack")
                .singleResult();
        completeTaskAndLog(taskService, task);
    }

    /**
     * 完成 rose 个人任务
     * 财务审批
     */
    @Test
    public void completTask4() {
        //获取 rose - myEvection 对应的任务
        TaskService taskService = getTaskService();
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("myEvection")
                .taskAssignee("rose")
                .singleResult();
        completeTaskAndLog(taskService, task);
    }


}
