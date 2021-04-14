package cn.case1;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * @Author: shiyuqin
 * @Date: 2021/3/21 16:35
 */
public class Writer1 implements ItemWriter<String> {
    @Override
    public void write(List<? extends String> items) throws Exception {
        System.out.println(items.get(0));
        System.out.println(Thread.currentThread());
    }
}
