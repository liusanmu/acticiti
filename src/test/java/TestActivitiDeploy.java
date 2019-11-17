import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * @author: lsm
 * @description:
 * @create: 2019-11-13 0:46
 */
public class TestActivitiDeploy {

    ProcessEngine processEngine = null;

    @Before
    public void beforeTest() {
        //1.创建ProcessEngineConfiguration对象  第一个参数:配置文件名称  第二个参数是processEngineConfiguration的bean的id
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration
                .createProcessEngineConfigurationFromResource("activiti.cfg.xml", "processEngineConfiguration01");
        //2.创建ProcesEngine对象
        processEngine = configuration.buildProcessEngine();
    }

    // 分别将 bpmn 文件和 png图片文件部署。
    @Test
    public void testDeploy1ByManyFile() throws Exception {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        InputStream resourceBpmn = this.getClass().getClassLoader().getResourceAsStream("holiday.bpmn");
        InputStream resourcePng = this.getClass().getClassLoader().getResourceAsStream("holiday.png");
        Deployment deployment = repositoryService.createDeployment()
                .addInputStream("holiday.bpmn", resourceBpmn)
                .addInputStream("holiday.png", resourcePng).name("请假申请单流程").deploy();
        System.out.println("流程部署id：" + deployment.getId());
        System.out.println("流程部署名称：" + deployment.getName());
    }

    // 将 holiday .bpmn 和 holiday .png 压缩成 zip 包
    @Test
    public void testDeployByZip() throws Exception {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("holiday.zip");
        ZipInputStream zipInputStream = new ZipInputStream(resourceAsStream);
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()//
                .addZipInputStream(zipInputStream).deploy();
        System.out.println("流程部署id：" + deployment.getId());
        System.out.println("流程部署名称：" + deployment.getName());
    }

    // 流程定义的查询

    /**
     * SELECT * FROM act_re_deployment #流程定义部署表，记录流程部署信息
     * SELECT * FROM act_re_procdef #流程定义表，记录流程定义信息
     * SELECT * FROM act_ge_bytearray #资源表
     * 说明：
     * act_re_deployment 和 act_re_procdef 一对多关系，一次部署在流程部署表生成一条记录，但一次部署
     * 可以部署多个流程定义，每个流程定义在流程定义表生成一条记录。每一个流程定义在
     * act_ge_bytearray会存在两个资源记录，bpmn 和 png。
     * 建议：一次部署一个流程，这样部署表和流程定义表是一对一有关系，方便读取流程部署及流程定
     * 义信息。
     *
     * @throws Exception
     */
    @Test
    public void testQueryProcessDefinition() throws Exception {
        String processDefinitionKey = "myProcess";
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> list = processDefinitionQuery.processDefinitionKey(processDefinitionKey)
                .orderByProcessDefinitionVersion().desc().list();
        for (ProcessDefinition processDefinition : list) {
            System.out.println("------------------------");
            System.out.println(" 流 程 部 署 id ： " +
                    processDefinition.getDeploymentId());
            System.out.println("流程定义id：" + processDefinition.getId());
            System.out.println("流程定义名称：" + processDefinition.getName());
            System.out.println("流程定义key：" + processDefinition.getKey());
            System.out.println("流程定义版本：" + processDefinition.getVersion());
        }
    }

//    使用 repositoryService 删除流程定义
//2) 如果该流程定义下没有正在运行的流程，则可以用普通删除。
//            3) 如果该流程定义下存在已经运行的流程，使用普通删除报错，可用级联删除方法将流程及相关
//    记录全部删除。项目开发中使用级联删除的情况比较多，删除操作一般只开放给超级管理员使
//    用。
    @Test
    public void testDeleteProcessDefinition() throws Exception {
        // 流程部署id
        String deploymentId = "8801";
        // 通过流程引擎获取repositoryService
        RepositoryService repositoryService = processEngine
                .getRepositoryService();
        //删除流程定义，如果该流程定义已有流程实例启动则删除时出错
        repositoryService.deleteDeployment(deploymentId);
        //设置true 级联删除流程定义，即使该流程有流程实例启动也可以删除，设置为false非级别删除方式，如果流程
        //repositoryService.deleteDeployment(deploymentId, true);
    }
    
    // 流程定义资源查询 获取bpmn 和 png
    @Test
    public void testQueryProcessDefinitionResources()throws Exception{
        
    }

}
