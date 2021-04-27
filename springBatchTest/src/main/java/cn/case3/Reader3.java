package cn.case3;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

@Component
//@Scope(value = "step", proxyMode = ScopedProxyMode.INTERFACES)
@StepScope
public class Reader3 implements ItemReader<String>{

    private int count;

    private StepExecution stepExecution;

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        stepExecution.getExecutionContext().put("stepExecutionParameter", "stepExecutionParameter");
        return count++ < 2 ? "" : null;
    }

    //@BeforeStep不适用于@StepScope
    //你告诉Spring使用代理模式 ScopedProxyMode.TARGET_CLASS . 但是，通过返回 MyInterface 而不是 MyInterfaceImpl ，代理只能查看 MyInterface 上的方法
    @BeforeStep
    public void stepExecution(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }
}
