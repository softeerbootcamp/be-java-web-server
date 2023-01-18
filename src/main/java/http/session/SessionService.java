package http.session;

public class SessionService {

    private SessionService() {
    }

    private static class SessionHolder {
        private static final SessionService SESSION = new SessionService();
    }

    public static SessionService getInstance() {
        return SessionHolder.SESSION;
    }

    public void addSession(Session session) {
        SessionRepository.addSession(session.getId(), session);
    }

    public Session findById(String id) {
        return SessionRepository.findById(id);
    }
}
