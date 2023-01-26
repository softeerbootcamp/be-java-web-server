package service;

import db.MemoDatabase;
import model.Memo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Map;

public class MemoService {

    private static final Logger logger = LoggerFactory.getLogger(MemoService.class);
    private static final MemoService instance = new MemoService();

    public static MemoService getInstance() {
        return instance;
    }

    private MemoService() {
    }

    public void write(Map<String, String> memoInfo) {
        Memo memo = createMemo(memoInfo);
        MemoDatabase.addMemo(memo);

        logger.debug("Memo: {}", memo);
    }

    private Memo createMemo(Map<String, String> memoInfo) {
        String writer = memoInfo.get("writer");
        String content = memoInfo.get("content");

        return new Memo(writer, content, LocalDateTime.now());
    }
}
