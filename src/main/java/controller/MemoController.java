package controller;

import view.RequestMessage;

import java.io.OutputStream;

public class MemoController implements Controller{

    private static MemoController memoController;
    private MemoController(){}
    public static MemoController getInstance(){
        if (memoController == null){
            synchronized (MemoController.class){
                if (memoController == null){
                    memoController = new MemoController();
                }
            }
        }
        return memoController;
    }

    @Override
    public void control(RequestMessage requestMessage, OutputStream out) {

    }

}
