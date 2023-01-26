package model.service;

import model.domain.Memo;
import model.repository.MemoRepository;
import model.repository.MysqlMemoRepository;

import java.util.Collection;

public class MemoService {
    private final MemoRepository memoRepository = MysqlMemoRepository.getInstance();
    private static MemoService memoService;

    private MemoService(){}

    public static MemoService getInstance(){
        if (memoService == null){
            synchronized (MemoService.class){
                if (memoService == null){
                    memoService = new MemoService();
                }
            }
        }
        return memoService;
    }

    public int post(Memo memo){
        memoRepository.addMemo(memo);
        return memo.getMemoId();
    }

    public Collection<Memo> findRecentMemos(int count){ return memoRepository.findRecent(count); }

    public Collection<Memo> findMemos(){ return memoRepository.findAll(); }


}
