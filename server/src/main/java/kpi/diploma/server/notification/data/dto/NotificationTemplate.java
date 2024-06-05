package kpi.diploma.server.notification.data.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum NotificationTemplate {
    APPROVAL_REQUEST("Approve Vacation request", "approval-request"),
    VACATION_APPROVED("Vacation Approved", "vacation-approved"),
    VACATION_DENIED("Vacation Denied", "vacation-denied");

    private final String subject;
    private final String contentTemplateName;
}
