package cn.case4;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * @author shiyuqin
 * @date 2021/04/2021/4/26 13:29
 */
@Configuration
@SuppressWarnings(value = "all")
public class JobConfig4 {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    /*@Autowired
    private Tasklet4 tasklet4;

    @Autowired(required = false)
    private Decision4 decision4;*/

    @Autowired
    private StepExecutionListener4 stepExecutionListener4;

    @Autowired
    private ChunkListener4 chunkListener4;

    @Bean
    public Step step(Tasklet4 tasklet4) {
        return stepBuilderFactory.get("step4").tasklet(tasklet4).build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step4.1").tasklet((stepContribution, chunkContext)->{
            System.out.println("===step1===");
            System.out.println("JobExeParam = " + chunkContext.getStepContext().getJobExecutionContext().get("param1"));
            System.out.println("StepExeParam = " + chunkContext.getStepContext().getStepExecutionContext().get("param2"));
         return RepeatStatus.FINISHED;
        }).build();
    }



    @Bean
    public Step step2() {
        AtomicInteger cnt = new AtomicInteger(1);
        return stepBuilderFactory.get("step2")
                .<String, String>chunk(1)
                .reader(() -> {
                    System.out.println("===writer===");
                    return cnt.getAndIncrement() == 2 ? null : "";
                })
                .processor((Function<? super String, ? extends String>) s -> "")
                .writer((list -> {}))
                .listener(stepExecutionListener4)
                .listener(chunkListener4)
                .build();
    }


    @Bean
    public Job job4(Step step, Step step1, Decision4 decision4, Step step2, JobExecutionListener4 jobExecutionListener4) {
        return jobBuilderFactory.get("job4")
                .start(step)
                .next(decision4)
                .on("status1")
                .to(step1)
                .next(step2)
                .end()
                .listener(jobExecutionListener4)
                .build();
    }
}
