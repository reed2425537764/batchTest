package cn.case5;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.support.MultiResourcePartitioner;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author shiyuqin
 * @date 2021/4/28 11:13
 */
@Configuration
public class JobConfig5 {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Value("classpath:testFile1_*")
    private Resource[] resource;

    @Bean
    @StepScope
    public FlatFileItemReader<String> reader(@Value("#{stepExecutionContext['path']}") String path) {
        FlatFileItemReader<String> reader = new FlatFileItemReader<>();
        System.out.println(path);
        reader.setResource(new FileSystemResource(path.substring(6)));
        reader.setStrict(false);
        reader.setLinesToSkip(1);

        DefaultLineMapper<String> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setNames("id", "name", "age");
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper((fieldSet -> fieldSet.readString("id")+fieldSet.readString("name")+fieldSet.readString("age")));
        reader.setLineMapper(lineMapper);

        return reader;
    }

    @Bean
    @StepScope
    public ItemProcessor<String, String> processor() {
        return (item)-> {
            System.out.println(item);
            return item;
        };
    }

    @Bean
    @StepScope
    public ItemWriter<String> writer() {
        return list -> {};
    }

    @Bean
    public Step stepPartition(Step step) {
        MultiResourcePartitioner partitioner = new MultiResourcePartitioner();
        partitioner.setKeyName("path");
        partitioner.setResources(resource);
        //JDK线程池ThreadPoolExecutor
        //spring线程池
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(2);
        taskExecutor.setMaxPoolSize(3);
        taskExecutor.setKeepAliveSeconds(1);
        taskExecutor.setThreadNamePrefix("partition_");
        taskExecutor.initialize();
        return stepBuilderFactory.get("stepPartition")
                .partitioner("step5", partitioner)
                .step(step)
                .gridSize(2)
                .taskExecutor(taskExecutor)
                .build();
    }

    @Bean
    public Step step(ItemReader<String> reader, ItemProcessor<String, String> processor, ItemWriter<String> writer) {
        return stepBuilderFactory.get("step5")
                .<String, String>chunk(1)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job job(Step stepPartition) {
        return jobBuilderFactory.get("job5")
                .start(stepPartition)
                .build();
    }
}
