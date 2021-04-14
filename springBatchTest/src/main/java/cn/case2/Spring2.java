package cn.case2;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.JobParametersNotFoundException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @Author: shiyuqin
 * @Date: 2021/3/28 18:37
 */
public class Spring2 {
    public static void main(String[] args) throws IOException {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("batch-case2.xml");
        JobOperator jobOperator = context.getBean(JobOperator.class);

        JobConfig3 config3 = context.getBean(JobConfig3.class);
        System.out.println(config3.getResource()[0].getFile().getCanonicalPath());

        System.out.println(context.containsBean("jobOperator"));
        System.out.println(context.containsBean("jobRegistry"));
        System.out.println(context.containsBean("jobExplorer"));
        System.out.println(context.containsBean("jobRepository"));

        try {
            Long jobExecutionId = jobOperator.startNextInstance("job3");
            System.out.println("jobExecutionId = " + jobExecutionId);
        } catch (NoSuchJobException e) {
            e.printStackTrace();
        } catch (JobParametersNotFoundException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }

        context.close();
    }
}
