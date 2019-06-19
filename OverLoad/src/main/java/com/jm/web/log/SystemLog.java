package com.jm.web.log;
import java.lang.annotation.*;
@Target({ElementType.PARAMETER, ElementType.METHOD})  
@Retention(RetentionPolicy.RUNTIME)  
@Documented 
public @interface SystemLog {
    String module()  default "";  
    String methods()  default ""; 
}
