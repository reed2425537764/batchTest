package cn.case1;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: shiyuqin
 * @Date: 2021/3/20 16:33
 */
@EnableBatchProcessing
public class Spring1 {
    public static void main(String[] args) {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("batch-case1.xml");
        JobLauncher jobLauncher = context.getBean(JobLauncher.class);
        Job job1 = context.getBean("job1", Job.class);
        Job job2 = context.getBean("job2", Job.class);
        try {
            JobExecution jobExecution = jobLauncher.run(job2
                    , new JobParametersBuilder().addLong("timestamp", System.currentTimeMillis()).toJobParameters());
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }

        context.close();
    }
}
