package cn.case4;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.stereotype.Component;

/**
 * @author shiyuqin
 * @date 2021/04/2021/4/26 13:24
 */
@Component
public class Decision4 implements JobExecutionDecider {
    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        System.out.println("stepExecution = " + stepExecution.getExecutionContext().toString());
        System.out.println("jobExecution = " + jobExecution.getExecutionContext().getString("param1"));
        return new FlowExecutionStatus("status1");
    }
}
