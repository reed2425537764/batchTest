package cn.case3;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConf3 {
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private Reader3 reader3;

    @Autowired
    private Processor3 processor3;

    @Bean("taskletPre")
    @StepScope
    public Tasklet tasklet() {
        return (stepContribution, chunkContext) -> {
            System.out.println("===taskletPre===");
            chunkContext.setAttribute("chunkAttribute", "chunkAttribute");
            chunkContext.getStepContext().getStepExecution().getExecutionContext().putString("stepExecutionParameter", "stepExecutionParameter");
            chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().putString("jobExecutionParameter", "jobExecutionParameter");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Step step3(@Qualifier("tasklet3") Tasklet tasklet) {
        return stepBuilderFactory.get("step3").tasklet(tasklet).build();
    }

    @Bean
    public Step stepPre(@Qualifier("taskletPre") Tasklet taskletPre) {
        return stepBuilderFactory.get("stepPre").tasklet(taskletPre).build();
    }

    @Bean
    public Step step3_1(/*ItemReader<String> reader3, ItemProcessor<String, String> processor3*/) {
        return stepBuilderFactory.get("step3_1").<String, String>chunk(1)
                .reader(reader3)
                .processor(processor3)
                .writer((list -> {
                }))
                .build();
    }

    /*@Bean
    //Spring BatchAdmin通过XML配置StepScope，它提供了Java代理作为代理机制。但是，@StepScope使用动态子类。为了使它起作用，而不是使用@StepScope快捷方式
    @Scope(value = "step", proxyMode = ScopedProxyMode.INTERFACES)
    public Processor3 processor3() {
        return new Processor3();
    }

    @Bean
    @Scope(value = "step", proxyMode = ScopedProxyMode.INTERFACES)
    public Reader3 reader3() {
        return new Reader3();
    }*/

    @Bean
    public Job job3(Step step3, Step stepPre, Step step3_1) {
        return jobBuilderFactory.get("job3").start(stepPre).next(step3).next(step3_1).build();
    }
}
