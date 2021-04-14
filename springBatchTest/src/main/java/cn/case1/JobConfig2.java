package cn.case1;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @Author: shiyuqin
 * @Date: 2021/3/27 19:34
 */
@Configuration
public class JobConfig2 {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private DataSource dataSource;

    @Bean
    public ItemReader<String> dbReader() {
        JdbcCursorItemReader<String> itemReader = new JdbcCursorItemReader<>();
        itemReader.setDataSource(dataSource);
        itemReader.setSql("select * from batch_job_seq limit 1");
        itemReader.setFetchSize(1);
        itemReader.setRowMapper((result, rowNum)-> result.getString(1).concat("::").concat(String.valueOf(rowNum)));
        return itemReader;
    }

    @Bean
    public ItemProcessor<String, String> printProcessor() {
        return (item -> {
            System.out.println(item);
            return item;
        });
    }

    @Bean("step2")
    public Step buildStep(ItemReader<String> dbReader/*bean name is default by the method name*/, ItemProcessor<String, String> printProcessor) {
        return stepBuilderFactory.get("step2").<String, String>chunk(3).reader(dbReader).processor(printProcessor).writer(new Writer1()).build();
    }

    @Bean("job2")
    public Job buildJob(@Qualifier(value = "step2") Step step) {
        return jobBuilderFactory.get("job2").start(step).build();
    }
}
