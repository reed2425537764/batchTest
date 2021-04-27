package cn.case4;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

/**
 * @author shiyuqin
 * @date 2021/04/2021/4/26 13:23
 */
@Component
@StepScope
public class Tasklet4 implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().putString("param1", "123");
        chunkContext.getStepContext().getStepExecution().getExecutionContext().putString("param2", "234");
        return RepeatStatus.FINISHED;
    }
}
