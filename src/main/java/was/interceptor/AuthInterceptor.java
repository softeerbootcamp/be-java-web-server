package was.interceptor;

import db.SessionStorage;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.HttpResponseMessage;

import java.util.Optional;
import java.util.UUID;

public class AuthInterceptor {
    public static boolean checkAuth(HttpRequest httpRequest) {
        UUID sessionId = httpRequest.getSessionId().orElse(null);
        if (sessionId == null || !SessionStorage.isExist(sessionId)) {
            return false;
        }
        return true;
    }
}
