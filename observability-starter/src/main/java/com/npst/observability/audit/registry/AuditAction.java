package com.npst.observability.audit.registry;

public enum AuditAction {

    LOGIN,
    LOGOUT,

    BALANCE_VIEW,

    FREEZE_ACCOUNT,
    UNFREEZE_ACCOUNT,

    BENEFICIARY_ADD,
    BENEFICIARY_DELETE,

    IMPS_TRANSFER,
    NEFT_TRANSFER,
    RTGS_TRANSFER,

    FD_CREATE,
    FD_CLOSE,

    LOAN_APPLY,
    EMI_PAYMENT
}