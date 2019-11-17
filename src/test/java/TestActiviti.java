import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;

/**
 * @author: lsm
 * @description:
 * @create: 2019-11-06 23:08
 */
public class TestActiviti {
    @Test
    public void test1()throws Exception{
        //1.创建ProcessEngineConfiguration对象  第一个参数:配置文件名称  第二个参数是processEngineConfiguration的bean的id
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration
                .createProcessEngineConfigurationFromResource("activiti.cfg.xml","processEngineConfiguration01");
        //2.创建ProcesEngine对象
        ProcessEngine processEngine = configuration.buildProcessEngine();

        //3.输出processEngine对象
        System.out.println(processEngine);

    }

    @Test
    public void test2()throws Exception{
        // 注意，配置一定要为默认
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //3.输出processEngine对象
        System.out.println(processEngine);

    }
}
