package cn.case1;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: shiyuqin
 * @Date: 2021/3/20 18:57
 */
@Configuration
public class JobConfig1 {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Reader1 read1() {
        return new Reader1();
    }

    @Bean
    public Processor1 process1() {
        return new Processor1();
    }

    @Bean("step1")
    public Step buildStep(Reader1 reader1, Processor1 process1) {
        System.out.println(reader1);
        return stepBuilderFactory.get("step1").<String, String>chunk(10)
                .reader(reader1).processor(process1).writer(new Writer1()).build();
    }

    @Bean("job1")
    public Job buildJob(@Qualifier(value = "step1") Step step) {
        System.out.println(step);
        return jobBuilderFactory.get("job1").start(step).build();
    }
}
