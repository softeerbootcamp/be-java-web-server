package bejavawebserver.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@RequiredArgsConstructor
public class LoginForm {
    @NotNull
    private final String userId;
    @NotNull
    private final String password;
}
