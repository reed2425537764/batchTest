package cn.case4;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.stereotype.Component;

/**
 * @author shiyuqin
 * @date 2021/04/2021/4/27 16:37
 */
@Component
@StepScope
public class StepExecutionListener4 implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("===StepExecutionListener.beforeStep===");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("===StepExecutionListener.afterStep===");
        return /*stepExecution.getExitStatus()*/ExitStatus.STOPPED;
    }
}
