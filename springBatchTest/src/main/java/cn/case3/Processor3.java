package cn.case3;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
//@Scope(value = "step", proxyMode = ScopedProxyMode.INTERFACES)
@StepScope
public class Processor3 implements ItemProcessor<String, String> {

    @Autowired
    private ApplicationContext applicationContext;



    @Value("#{stepExecutionContext}")
    private Properties stepExecutionContext;

    @Value("#{jobExecutionContext}")
    private Properties jobExecutionContext;

    //You can access the stepExecutionContext only within a bean defined in the scope="step"
    @Value("#{stepExecutionContext['stepExecutionParameter']}")
    private String str;

    @Override
    public String process(String s) throws Exception {
        System.out.println("stepExecutionParameter = " + stepExecutionContext.get("stepExecutionParameter"));
        System.out.println(str);
        System.out.println("jobExecutionParameter = " + jobExecutionContext.get("jobExecutionParameter"));
        return "null";
    }

}
