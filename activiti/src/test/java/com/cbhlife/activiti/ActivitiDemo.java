package com.cbhlife.activiti;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

public class ActivitiDemo {

    /**
     * 使用zip包进行批量的部署
     */
    @Test
    public void deployProcessByZip() {
//        获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        获取RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();
//        流程部署
//        读取资源包文件，构造成inputStream
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("bpmn/evection.zip");
//        用inputStream 构造ZipInputStream
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
//        使用压缩包的流进行流程的部署
        Deployment deploy = repositoryService.createDeployment()
                .addZipInputStream(zipInputStream)
                .deploy();
        System.out.println("流程部署id=" + deploy.getId());
        System.out.println("流程部署的名称=" + deploy.getName());
    }


    /**
     * 查询流程定义
     */
    @Test
    public void queryProcessDefinition() {
//        获取引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        获取  Repositoryservice
        RepositoryService repositoryService = processEngine.getRepositoryService();
//        获取ProcessDifinitionQuery对象
        ProcessDefinitionQuery definitionQuery = repositoryService.createProcessDefinitionQuery();
//        查询当前所有的流程定义 ,返回流程定义信息的集合
//        processDefinitionKey（流程定义Key）
//        orderByProcessDefinitionVersion 进行排序
//        desc 倒叙
//        list 查询出所有的内容
        List<ProcessDefinition> definitionList = definitionQuery.processDefinitionKey("myEvection")
                .orderByProcessDefinitionVersion()
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
     */
    @Test
    public void deleteDeployMent() {
//      获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        通过引擎来获取 RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();
//        通过部署id来删除流程部署信息
        String deploymentId = "1";
//        repositoryService.deleteDeployment(deploymentId);
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
//         1、得到引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        2、获取api，RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();
//        3、获取查询对象 ProcessDefinitionQuery查询流程定义信息
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("myEvection")
                .singleResult();
//        4、通过流程定义信息，获取部署ID
        String deploymentId = processDefinition.getDeploymentId();
//        5、通过RepositoryService ，传递部署id参数，读取资源信息（png 和 bpmn）
//          5.1、获取png图片的流
//        从流程定义表中，获取png图片的目录和名字
        String pngName = processDefinition.getDiagramResourceName();
//        通过 部署id和 文件名字来获取图片的资源
        InputStream pngInput = repositoryService.getResourceAsStream(deploymentId, pngName);
//          5.2、获取bpmn的流
        String bpmnName = processDefinition.getResourceName();
        InputStream bpmnInput = repositoryService.getResourceAsStream(deploymentId, bpmnName);
//        6、构造OutputStream流
        File pngFile = new File("d:/evectionflow01.png");
        File bpmnFile = new File("d:/evectionflow01.bpmn");
        FileOutputStream pngOutStream = new FileOutputStream(pngFile);
        FileOutputStream bpmnOutStream = new FileOutputStream(bpmnFile);
//        7、输入流，输出流的转换
        IOUtils.copy(pngInput, pngOutStream);
        IOUtils.copy(bpmnInput, bpmnOutStream);
//        8、关闭流
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
//      获取引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        获取HistoryService
        HistoryService historyService = processEngine.getHistoryService();
//        获取 actinst表的查询对象
        HistoricActivityInstanceQuery instanceQuery = historyService.createHistoricActivityInstanceQuery();
//        查询 actinst表，条件：根据 InstanceId 查询
//        instanceQuery.processInstanceId("2501");
//        查询 actinst表，条件：根据 DefinitionId 查询
        instanceQuery.processDefinitionId("myEvection:1:4");
//        增加排序操作,orderByHistoricActivityInstanceStartTime 根据开始时间排序 asc 升序
        instanceQuery.orderByHistoricActivityInstanceStartTime().asc();
//        查询所有内容
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
