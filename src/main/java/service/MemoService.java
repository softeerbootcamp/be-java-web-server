package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemoService {

    private static final Logger logger = LoggerFactory.getLogger(MemoService.class);
    private static final MemoService instance = new MemoService();

    public static MemoService getInstance() {
        return instance;
    }

    private MemoService() {
    }

}
