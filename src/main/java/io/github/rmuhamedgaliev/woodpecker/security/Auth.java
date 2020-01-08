package io.github.rmuhamedgaliev.woodpecker.security;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {

}
