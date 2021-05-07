package cn.case5;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author shiyuqin
 * @date 2021/5/6 14:42
 */
@Component
@StepScope
public class SpiltFileTasklet implements Tasklet {

    @Value("file:/#{jobParameters['filePath']}")
    private Resource file;

    @Value("#{jobParameters['strict']}")
    private boolean strict = true;

    @Value("#{jobParameters['hasHeader']}")
    private boolean hasHeader = false;

    @Value("#{jobParameters['spiltNum']?:2}")
    private int spiltNum = 2;

    @Value("#{jobParameters['spiltPath']}")
    private String spiltPath;

    @PostConstruct
    public void init() {
        Assert.notNull(file, "file cannot be null");
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        if (!file.exists()) {
            if (strict) {
                throw new RuntimeException("file not exists");
            } else {
                System.out.println("file not exists");
                return RepeatStatus.FINISHED;
            }
        }

        BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(file.getURI()));
        String totalNum = bufferedReader.readLine();
        if (totalNum == null || totalNum.trim().length() == 0) {
            throw new RuntimeException("the first line is supposed to be a number which is the total line of the content");
        } else {
            try {
                Integer.parseInt(totalNum);
            } catch (NumberFormatException e) {
                throw new RuntimeException("the first line is not a number which is supposed to be the total line of the content");
            }
        }

        String header = null;
        if (hasHeader) {
            header = bufferedReader.readLine();
        }

        /* this kind of spilt way is not doing well
        int remainder = Integer.parseInt(totalNum) % spiltNum;
        int result = Integer.parseInt(totalNum) / spiltNum;
        int linesPerFile = remainder == 0 ? result : result + 1 *//*Integer.parseInt(totalNum) / spiltNum +1*//*;*/

//        createDir(spiltPath);

        spiltFile(bufferedReader, header, spiltNum, /*linesPerFile*/Integer.parseInt(totalNum));
        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().putString("spiltFilePath", "E:/workspace/myWorkSpace/batchTest/spiltFile_*");

        bufferedReader.close();
        return RepeatStatus.FINISHED;
    }

    private void createDir(String spiltPath) throws IOException {
        if (spiltPath == null && spiltPath.trim().length()==0) {
            spiltPath = file.getFile().getParentFile().getPath() + "spiltPath";
        }
        Path path = Paths.get(spiltPath);
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }
    }

    //尾差法
    private void spiltFile(BufferedReader bufferedReader, String header, int spiltNum, int totalNum) throws IOException {

        int linesPerFile = totalNum / spiltNum;

        for (int i = 0; i < spiltNum; i++) {
            Path path = Paths.get("spiltFile_"+i);
            Files.deleteIfExists(path);
            Files.createFile(path);
            BufferedWriter bufferedWriter = Files.newBufferedWriter(path);
            bufferedWriter.write(String.valueOf(totalNum<2*linesPerFile ? totalNum : linesPerFile));
            bufferedWriter.newLine();
            bufferedWriter.write(header);
            bufferedWriter.newLine();

            for (int j = 0; j < linesPerFile || totalNum!=0&&totalNum<linesPerFile; j++) {
                bufferedWriter.write(bufferedReader.readLine());
                bufferedWriter.newLine();
                totalNum--;
            }

            bufferedWriter.close();
        }
    }

}
