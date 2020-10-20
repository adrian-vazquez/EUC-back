package com.citi.euces.pronosticos.infra;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Service {
    int trn() default 0;

    boolean autentica() default false;

    boolean autoriza() default false;

    boolean bitacora() default false;

    boolean bitacoraInput() default true;

    boolean bitacoraOutput() default false;

    String implementacionTRN() default "";
}
