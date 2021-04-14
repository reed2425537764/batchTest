package cn.case1;

import org.springframework.batch.item.ItemProcessor;

/**
 * @Author: shiyuqin
 * @Date: 2021/3/21 15:28
 */
public class Processor1 implements ItemProcessor<String, String> {
    @Override
    public String process(String item) throws Exception {
        System.out.println("hello batch ItemProcessor");
        return "hello batch ItemProcessor";
    }
}
