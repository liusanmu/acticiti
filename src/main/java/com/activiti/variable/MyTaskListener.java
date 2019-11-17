package com.activiti.variable;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: lsm
 * @description:
 * @create: 2019-11-17 21:07
 */
public class MyTaskListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.setAssignee("zhangsan");
    }


    //启动流程实例时设计流程变量
    //定义流程变量
  /*  Map<String, Object> variables = new HashMap<String, Object>();
    //设置流程变量assignee
    variables.put("assignee","张三");
    ProcessInstance processInstance = runtimeService
    .startProcessInstanceByKey(processDefinitionKey, variables);*/
}
