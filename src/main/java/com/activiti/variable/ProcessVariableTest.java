package com.activiti.variable;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: lsm
 * @description:
 * @create: 2019-11-17 15:55
 */
public class ProcessVariableTest {

    //
     /*   1. 部署流程
            act_re_deployment  部署信息
            act_re_procdef     流程定义的一些信息
            act_ge_bytearray   流程定义的bpmn文件及png文件*/
    @Test
    public void test1()throws Exception{
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("holiday4.bpmn")
                .addClasspathResource("holiday4.png")
                .name("请假流程-流程变量")
                .deploy();
        // 流程id1:null:请假流程-流程变量
        System.out.println("流程id:" + deploy.getId()+":"+deploy.getKey()+":"+deploy.getName());

    }

    // 2. 启动流程实例,并设置流程变量
    // act_ge_bytearray act_ru_variable

    @Test
    public void test2()throws Exception{
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        String key = "holiday4";
        Map<String, Object> map  = new HashMap<>();
        Holiday holiday = new Holiday();
        holiday.setNum(5F);
        map.put("holiday",holiday);

        //启动流程实例，设置流程变量
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key, map);
       // null-----holiday4:1:4
        System.out.println(processInstance.getName() +"-----" +processInstance.getProcessDefinitionId());
    }

    // 查询当前流程实例是否有任务，有则完成
             /*   处理当前用户的任务
             *   背后操作的表：
             *    act_hi_actinst
                  act_hi_identitylink
                  act_hi_taskinst
                 act_ru_identitylink
                 act_ru_task*/
    @Test
    public void test3()throws Exception{
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        String key  = "holiday";
        // 查询当前用户是否有任务
        Task task = taskService.createTaskQuery().processDefinitionKey(key).taskAssignee("zhangsan").singleResult();

        if (task != null) {
            taskService.complete(task.getId());
            System.out.println("任务执行完毕");
        }

    }
}
