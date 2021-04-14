package cn.case1;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

/**
 * @Author: shiyuqin
 * @Date: 2021/3/21 15:20
 */
public class Reader1 implements ItemReader<String> {
    int count;
    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        System.out.println("hello batch ItemReader");
        count++;
        return count!=10 ? "hello batch ItemReader" : null;
    }
}
