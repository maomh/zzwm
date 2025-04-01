package site.mhjn.zzwm.config;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@PreAuthorize("hasRole('ADMIN')")
public @interface HasRoleAdmin {
    // 该注解用于标记需要 ADMIN 角色的接口
    // 可以在方法或类上使用
    // 使用 @PreAuthorize 注解来实现权限控制
}
