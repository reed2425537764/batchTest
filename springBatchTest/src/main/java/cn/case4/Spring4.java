package cn.case4;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author shiyuqin
 * @date 2021/04/2021/4/26 13:19
 */
public class Spring4 {
    public static void main(String[] args) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext("batch-case4.xml");
        JobLauncher jobLauncher = applicationContext.getBean(JobLauncher.class);
        Job job4 = (Job) applicationContext.getBean("job4");
        JobExecution jobExecution = jobLauncher.run(job4, new JobParametersBuilder().addLong("timestamp", System.currentTimeMillis()).toJobParameters());
        System.out.println("jobExecution = " + jobExecution);

        applicationContext.close();
    }
}
