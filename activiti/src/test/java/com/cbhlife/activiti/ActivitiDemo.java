package com.cbhlife.activiti;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

public class ActivitiDemo {

    private RepositoryService repositoryService;

    @Before
    public void getRepositoryService() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        repositoryService = processEngine.getRepositoryService();
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
        RuntimeService runtimeService = processEngine.getRuntimeService();
        System.out.println(processEngine);
    }


    /**
     * 查询流程定义
     */
    @Test
    public void queryProcessDefinition() {

        ProcessDefinitionQuery definitionQuery = repositoryService.createProcessDefinitionQuery();
        // 多次部署会生成不同verion
        List<ProcessDefinition> definitionList = definitionQuery.processDefinitionKey("myEvection")
                .orderByProcessDefinitionVersion() //version
                .desc()
                .list();
//        输出信息
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
     *
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
     *
     */
    @Test
    public void deleteDeployMent() {

        String deploymentId = "20001";
        //repositoryService.deleteDeployment(deploymentId);
        repositoryService.deleteDeployment(deploymentId, true);
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

    /**
     * 查看历史信息
     */
    @Test
    public void findHistoryInfo() {

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        HistoryService historyService = processEngine.getHistoryService();
        //获取 actinst表的查询对象
        HistoricActivityInstanceQuery instanceQuery = historyService.createHistoricActivityInstanceQuery();
        //instanceQuery.processInstanceId("2501"); // actinst表，根据 InstanceId 查询
        instanceQuery.processDefinitionId("myEvection:1:4"); // actinst表，根据 DefinitionId 查询
        instanceQuery.orderByHistoricActivityInstanceStartTime().asc();

        List<HistoricActivityInstance> activityInstanceList = instanceQuery.list();
//        输出
        for (HistoricActivityInstance hi : activityInstanceList) {
            System.out.println(hi.getActivityId());
            System.out.println(hi.getActivityName());
            System.out.println(hi.getProcessDefinitionId());
            System.out.println(hi.getProcessInstanceId());
            System.out.println("<==========================>");
        }
    }
}
