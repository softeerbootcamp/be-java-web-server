package bejavawebserver.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class LoginForm {
    @NonNull
    private final String userId;
    @NonNull
    private final String password;
}
