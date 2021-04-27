package cn.case4;


import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

/**
 * @author shiyuqin
 * @date 2021/04/2021/4/27 16:38
 */
@Component
@StepScope
public class ChunkListener4 implements ChunkListener {

    @Override
    public void beforeChunk(ChunkContext context) {
        System.out.println("===ChunkListener4.beforeChunk===");
    }

    @Override
    public void afterChunk(ChunkContext context) {
        System.out.println("===ChunkListener4.afterChunk===");
    }

    @Override
    public void afterChunkError(ChunkContext context) {
        System.out.println("===ChunkListener4.afterChunkError===");
    }
}
