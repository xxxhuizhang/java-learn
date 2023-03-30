package com.cbhlife.activiti.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * 监听器
 */
public class MyTaskListener implements TaskListener {

    /**
     * 指定负责人
     *
     * @param delegateTask
     */

    @Override
    public void notify(DelegateTask delegateTask) {//EventName: assignment create complete delete
        if ("经理审批".equals(delegateTask.getName()) && "create".equals(delegateTask.getEventName())) {

            delegateTask.setAssignee("jerry2");

            //delegateTask.addCandidateUser("李四");
            //delegateTask.addCandidateUser("王五");
            //delegateTask.addCandidateUsers();//Collection
            //delegateTask.addCandidateGroups();//group
        }
    }
}
