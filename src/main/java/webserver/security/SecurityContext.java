package webserver.security;

import model.UserPrincipal;

public class SecurityContext {

    private static ThreadLocal<UserPrincipal> userStore = new ThreadLocal<>();


    public static UserPrincipal getContext() {
        return userStore.get();
    }

    public static void clearContext(){
        userStore.remove();
    }

    public static void addUser(String sessionId){
        UserPrincipal user = SecurityUtils.getUserPrincipalFromCookie(sessionId);
        if(userStore.get() == null)
            userStore.set(user);
    }
}
