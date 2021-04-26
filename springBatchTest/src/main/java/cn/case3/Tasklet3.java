package cn.case3;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class Tasklet3 implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        chunkContext.setAttribute("chunkAttribute", "chunkAttribute_Tasklet3");
        chunkContext.getStepContext().getStepExecution().getExecutionContext().putString("stepExecutionParameter", "stepExecutionParameter_Tasklet3");



        System.out.println("stepContribution = " + stepContribution);
        System.out.println("chunkAttribute = " + chunkContext.getAttribute("chunkAttribute"));
        System.out.println("stepExecutionParameter = " + chunkContext.getStepContext().getStepExecution().getExecutionContext().get("stepExecutionParameter"));
        System.out.println("stepExecutionParameter1 = " + chunkContext.getStepContext().getStepExecutionContext().get("stepExecutionParameter"));
        System.out.println("jobExecutionParameter = " + chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("jobExecutionParameter"));
        System.out.println("jobExecutionParameter1 = " + chunkContext.getStepContext().getJobExecutionContext().get("jobExecutionParameter"));
        System.out.println("jobParameter = " + chunkContext.getStepContext().getJobParameters().get("timestamp"));
        System.out.println("jobParameter1 = " + chunkContext.getStepContext().getStepExecution().getJobParameters().getString("timestamp"));
        System.out.println("jobParameter2 = " + chunkContext.getStepContext().getStepExecution().getJobExecution().getJobParameters().getString("timestamp"));
        return RepeatStatus.FINISHED;
    }
}
