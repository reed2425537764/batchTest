package cn.case3;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: shiyuqin
 * @Date: 2021/4/25 10:01
 */
public class Spring3 {
    public static void main(String[] args) {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("batch-case3.xml");
//        Job job3 = (Job) context.getBean("job3");
        JobOperator jobOperator = context.getBean(JobOperator.class);
        try {
            Long executionId = jobOperator.start("job3", "jobParameter=1234\ntimestamp="+System.currentTimeMillis());
            System.out.println("executionId = " + executionId);
        } catch (NoSuchJobException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyExistsException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }


        context.close();
    }
}
