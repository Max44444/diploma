package kpi.diploma.server.user.data.dto;

public record LoginUserRequest (
        String email,
        String password
) {
}
