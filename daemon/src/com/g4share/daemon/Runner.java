package com.g4share.daemon;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * User: gm
  */
public class Runner {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();
    }
}
