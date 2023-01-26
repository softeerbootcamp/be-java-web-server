package webserver.security;

import model.User;
import model.UserPrincipal;
import webserver.utils.HttpSessionUtils;

public class SecurityContext {

    private static ThreadLocal<UserPrincipal> userStore = new ThreadLocal<>();  //쓰레드의 주기에 맞게 생성, 삭제 되는 userContext 정보


    public static UserPrincipal getContext() {
        return userStore.get();
    }

    public static void clearContext(){
        userStore.remove();
    }

    public static void addUser(String sessionId){
        if(userStore.get() == null){
            userStore.set(HttpSessionUtils.getInstance().sessionIdToUserPrincipal(sessionId));
        }
    }
}
