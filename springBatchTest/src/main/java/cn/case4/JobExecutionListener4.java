package cn.case4;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

/**
 * @author shiyuqin
 * @date 2021/04/2021/4/27 16:58
 */
@Component
public class JobExecutionListener4 implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("===JobExecutionListener4.beforeJob===");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("===JobExecutionListener4.afterJob===");
    }
}
