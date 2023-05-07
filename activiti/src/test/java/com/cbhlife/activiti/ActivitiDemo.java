package com.cbhlife.activiti;

import org.activiti.bpmn.model.*;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.IOUtils;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.*;
import java.util.zip.ZipInputStream;

public class ActivitiDemo {

    private static String processDefinitionKey = "myEvection";

    private RepositoryService repositoryService;
    private RuntimeService runtimeService;
    private TaskService taskService;
    private HistoryService historyService;

    @Before
    public void getRepositoryService() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        repositoryService = processEngine.getRepositoryService();
        runtimeService = processEngine.getRuntimeService();
        taskService = processEngine.getTaskService();
        historyService = processEngine.getHistoryService();
    }

    /**
     * insert into ACT_RE_DEPLOYMENT
     * insert into ACT_RE_PROCDEF
     */

    @Test
    public void deployProcessByZip() {

        //使用zip包进行批量的部署
        InputStream inputStream = this.getClass().getClassLoader()
                .getResourceAsStream("bpmn/evection.zip");
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);

        Deployment deploy = repositoryService.createDeployment()
                .addZipInputStream(zipInputStream)
                .deploy();
        System.out.println("流程部署id=" + deploy.getId());
        System.out.println("流程部署的名称=" + deploy.getName());
    }

    /**
     * 使用activiti提供的默认方式来创建mysql的表
     * <p>
     * 需要使用avtiviti提供的工具类 ProcessEngines, 使用方法getDefaultProcessEngine
     * getDefaultProcessEngine会默认从resources下读取名字为actviti.cfg.xml的文件
     * 创建processEngine时，就会创建mysql的表
     */
    @Test
    public void testCreateDbTable() {
        repositoryService.createDeployment();
    }

    @Test
    public void testCreateDbTable2() {
        ProcessEngineConfiguration processEngineConfiguration =
                ProcessEngineConfiguration.createProcessEngineConfigurationFromResource
                        ("activiti.cfg.xml", "processEngineConfiguration");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        System.out.println(processEngine);
    }


    /**
     * 查询流程定义
     */
    @Test
    public void queryProcessDefinition() {

        ProcessDefinitionQuery definitionQuery = repositoryService.createProcessDefinitionQuery();
        // 多次部署会生成不同verion
        List<ProcessDefinition> definitionList = definitionQuery.processDefinitionKey(processDefinitionKey)
                .orderByProcessDefinitionVersion() //version
                .desc()
                .list();

        for (ProcessDefinition processDefinition : definitionList) {
            System.out.println("流程定义ID：" + processDefinition.getId());
            System.out.println("流程定义名称:" + processDefinition.getName());
            System.out.println("流程定义Key:" + processDefinition.getKey());
            System.out.println("流程定义版本:" + processDefinition.getVersion());
            System.out.println("流程部署ID:" + processDefinition.getDeploymentId());
        }
    }

    /**
     * 删除流程部署信息
     * `act_ge_bytearray`
     * `act_re_deployment`
     * `act_re_procdef`
     * 当前的流程如果并没有完成，想要删除的话需要使用特殊方式，原理就是 级联删除
     * <p>
     * delete from ACT_HI_COMMENT where TASK_ID_ ,PROC_INST_ID_
     * delete from ACT_GE_BYTEARRAY where DEPLOYMENT_ID_ = ?
     * delete from ACT_RE_DEPLOYMENT where ID_ = ?
     * delete from ACT_RU_EVENT_SUBSCR
     * delete from ACT_RU_IDENTITYLINK where ID_  PROC_DEF_ID_=?
     * delete from ACT_RU_TASK where ID_ = ? and REV_ = ?
     * delete from ACT_RU_EXECUTION where ID_ = ? and REV_ = ?
     * delete from ACT_RE_PROCDEF where ID_ = ? and REV_ = ?
     * delete from ACT_HI_IDENTITYLINK where ID_ = ?
     * delete from ACT_HI_ACTINST where ID_ = ?
     * delete from ACT_HI_PROCINST where ID_ = ?
     * delete from ACT_HI_TASKINST where ID_ = ?
     */
    @Test
    public void deleteDeployMent() {

        String deploymentId = "85001";
        //repositoryService.deleteDeployment(deploymentId);
        repositoryService.deleteDeployment(deploymentId, true);
    }


    /**
     * 执行此方法后，流程实例的当前任务act_ru_task会被删除，流程历史act_hi_taskinst不会被删除，
     * 并且流程历史的状态置为finished完成。
     */
    @Test
    public void deleteProcessInstance() {
        String processInstanceId = "85001";
        String reason = "删除原因";
        runtimeService.deleteProcessInstance(processInstanceId, reason);
    }


    /**
     * 下载 资源文件
     * 方案1： 使用Activiti提供的api，来下载资源文件,保存到文件目录
     * 方案2： 自己写代码从数据库中下载文件，使用jdbc对blob 类型，clob类型数据读取出来，保存到文件目录
     * 解决Io操作：commons-io.jar
     * 这里我们使用方案1，Activiti提供的api：RespositoryService
     */
    @Test
    public void getDeployment() throws IOException {

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("myEvection")
                .singleResult();

        String deploymentId = processDefinition.getDeploymentId();
        String pngName = processDefinition.getDiagramResourceName();

        InputStream pngInput = repositoryService.getResourceAsStream(deploymentId, pngName);
        String bpmnName = processDefinition.getResourceName();
        InputStream bpmnInput = repositoryService.getResourceAsStream(deploymentId, bpmnName);

        File pngFile = new File("./evectionflow01.png");
        File bpmnFile = new File("./evectionflow01.bpmn");
        FileOutputStream pngOutStream = new FileOutputStream(pngFile);
        FileOutputStream bpmnOutStream = new FileOutputStream(bpmnFile);

        IOUtils.copy(pngInput, pngOutStream);
        IOUtils.copy(bpmnInput, bpmnOutStream);

        pngOutStream.close();
        bpmnOutStream.close();
        pngInput.close();
        bpmnInput.close();

    }

    public void test() {

        final Task task = taskService.createTaskQuery().taskId("").singleResult();
        UserTask userTask = (UserTask) task;
        String documentation = userTask.getDocumentation();
        List<SequenceFlow> outgoingFlows = userTask.getOutgoingFlows();

        for (SequenceFlow outgoingFlow : outgoingFlows) {
            String conditionExpression = outgoingFlow.getConditionExpression();
        }

    }


    public void cancelTask(ActivitiTask doneTask) {

        try {

            // doneTask为封装的Task对象
            String processInstanceId = doneTask.getInstanceId();
            String myTaskId = doneTask.getTaskId();

            // 校验流程是否结束
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(processInstanceId).active()
                    .singleResult();

            if (processInstance == null) {
                System.out.println("流程已结束或已挂起，无法执行撤回操作");
                return;
            }

            // 当前任务
            HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery()
                    .taskId(myTaskId)
                    .processInstanceId(processInstanceId).singleResult();

            if (historicTaskInstance == null) {
                System.out.println("当前任务不存在，无法撤回");
                return;
            }

            String myActivityId = null;
            List<HistoricActivityInstance> actInstList =
                    historyService.createHistoricActivityInstanceQuery()
                            .executionId(historicTaskInstance.getExecutionId())
                            .finished().list();

            for (HistoricActivityInstance hai : actInstList) {
                if (myTaskId.equals(hai.getTaskId())) {
                    myActivityId = hai.getActivityId();
                    break;
                }
            }

            BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
            FlowNode myFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(myActivityId);

            // 获取所有下一任务节点的标识ID
            Map<String, String> taskKeyMap = new HashMap<>();

            // 获取所有下一任务节点对应的FlowElement
            List<FlowElement> flowElementList = getOutgoingTask(bpmnModel, myActivityId);

            for (FlowElement flowElement : flowElementList) {
                String eleId = flowElement.getId();
                taskKeyMap.put(eleId, eleId);
            }

            // 获取当前流程代办事项，没有代办事项表明流程结束或已挂起
            List<Task> alltaskList = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
            if (alltaskList.size() <= 0) {
                System.out.println("流程已结束或已挂起，无法执行撤回操作");
                return;
            }

            // 判断所有下一任务节点中是否有代办任务，没有则表示任务已办理或已撤回，此时无法再执行撤回操作
            List<Task> nextTaskList = Lists.newArrayList();
            for (Task task : alltaskList) {
                if (taskKeyMap.containsKey(task.getTaskDefinitionKey())) {
                    nextTaskList.add(task);
                }
            }

            if (nextTaskList.size() <= 0) {
                System.out.println("任务已办理或已撤回，无法执行撤回操作");
            }

            // 执行撤回操作
            for (Task task : nextTaskList) {

                Execution execution = runtimeService.createExecutionQuery()
                        .executionId(task.getExecutionId()).singleResult();

                String activityId = execution.getActivityId();
                FlowNode flowNode = (FlowNode) bpmnModel.getMainProcess()
                        .getFlowElement(activityId);

 
                // 记录原活动方向
                List<SequenceFlow> oriSequenceFlows = new ArrayList<>();
                oriSequenceFlows.addAll(flowNode.getOutgoingFlows());
                flowNode.getOutgoingFlows().clear();

                // 建立新方向

                List<SequenceFlow> newSequenceFlowList = new ArrayList<SequenceFlow>();

                SequenceFlow newSequenceFlow = new SequenceFlow();
                newSequenceFlow.setId("sid-" + UUID.randomUUID().toString());
                newSequenceFlow.setSourceFlowElement(flowNode);
                newSequenceFlow.setTargetFlowElement(myFlowNode);
                newSequenceFlowList.add(newSequenceFlow);
                flowNode.setOutgoingFlows(newSequenceFlowList);


                taskService.addComment(task.getId(), task.getProcessInstanceId(), "主动撤回");
                taskService.resolveTask(task.getId());
                taskService.claim(task.getId(), doneTask.getTodoUserId());
                taskService.complete(task.getId());
                flowNode.setOutgoingFlows(oriSequenceFlows);

            }

            System.out.println("执行成功");
        } catch (Exception e) {
            System.out.println("任务撤回失败500");

        }

    }

}
