package cn.case2;

import cn.case1.Writer1;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.PassThroughItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @Author: shiyuqin
 * @Date: 2021/3/28 18:42
 */
@Configuration
public class JobConfig3 {
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Value("file:E:/projects/batchTest/springBatchTest/src/main/resources/testFile1_*")/*new ClassPathResource()*/
    private Resource[] resource ;

    public Resource[] getResource() {
        return resource;
    }

    public void setResource(Resource[] resource) {
        this.resource = resource;
    }

    @Bean
    public ItemReader<String> fileReader() {
        FlatFileItemReader<String> fileItemReader = new FlatFileItemReader<>();
        fileItemReader.setResource(resource[0]);
        fileItemReader.setLinesToSkip(1);
//        LineMapper<String> lineMapper = new PassThroughLineMapper();

        //LineTokenizer
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer(",");
        lineTokenizer.setNames("id","name","age");

        //FieldSetMapper
        FieldSetMapper<String> fieldSetMapper = (fieldSet) -> fieldSet.readString("id") + fieldSet.readString("name") + fieldSet.readString("age");

        //lineMapper = LineTokenizer + FieldSetMapper
        DefaultLineMapper<String> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        fileItemReader.setLineMapper(lineMapper);

        return fileItemReader;
    }

    @Bean
    public ItemProcessor<String, String> buildProcess() {
        return new PassThroughItemProcessor<>();
    }

    @Bean
    public ItemWriter<String> itemWriter() {
        return new Writer1();
    }

    @Bean("step3")
    public Step buildStep(ItemReader<String> reader, ItemProcessor<String, String> processor, ItemWriter<String> writer) {
        return stepBuilderFactory.get("step3").<String, String>chunk(10000)
                .reader(reader).processor(processor).writer(writer).taskExecutor(threadPoolTaskExecutor).throttleLimit(2).build();
    }

    @Bean("job3")
    public Job buildJob(@Qualifier(value = "step3") Step step) {
        return jobBuilderFactory.get("job3").incrementer(new RunIdIncrementer()).start(step).build();
    }

    /*public static void main(String[] args) throws Exception{
        Path path = Paths.get("E:\\projects\\batchTest\\springBatchTest\\src\\main\\resources\\testFile1");
        BufferedWriter bufferedWriter = Files.newBufferedWriter(path);
        for (int i = 0; i < 3000000; i++) {
            bufferedWriter.write(i+","+"jason"+i+","+"2"+i);
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }*/
}
