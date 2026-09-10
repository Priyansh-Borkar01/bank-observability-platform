package com.npst.observability.audit.annotation;

import com.npst.observability.audit.registry.AuditAction;
import com.npst.observability.audit.registry.AuditModule;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuditLog {

    AuditModule module();

    AuditAction action();

    String entity();

    String entityId() default "";

    String description() default "";
}