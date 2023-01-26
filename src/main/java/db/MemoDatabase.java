package db;

import com.google.common.collect.Maps;
import model.Memo;

import java.util.Collection;
import java.util.Map;

// TODO: MySQL과 연동
public class MemoDatabase {
    private static final Map<Long, Memo> memos = Maps.newHashMap();
    private static Long sequence = 0L;

    public static void addMemo(Memo memo) {
        memo.setMemoId(++sequence);
        memos.put(memo.getMemoId(), memo);
    }

    public static Collection<Memo> findAll() {
        return memos.values();
    }
}
