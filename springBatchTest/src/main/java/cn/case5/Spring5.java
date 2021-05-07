package cn.case5;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author shiyuqin
 * @date 2021/4/28 10:53
 */
public class Spring5 {
    public static void main(String[] args) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext("batch-case5.xml");
        Job job = (Job) applicationContext.getBean("job");
        JobLauncher jobLauncher = applicationContext.getBean(JobLauncher.class);
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .addString("filePath","E:/workspace/myWorkSpace/batchTest/springBatchTest/src/main/resources/testFile1_1")
                .addString("strict", "false")
                .addString("hasHeader", "true")
                .addString("spiltNum", "5")
                .toJobParameters();
        JobExecution jobExecution = jobLauncher.run(job, jobParameters);
        System.out.println("jobExecution = " + jobExecution);
        applicationContext.close();
    }
}
