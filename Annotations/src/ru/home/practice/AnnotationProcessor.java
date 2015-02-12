package ru.home.practice;

import ru.home.practice.annotations.Init;
import ru.home.practice.annotations.Service;
import ru.home.practice.services.LazyService;
import ru.home.practice.services.SimpleService;

import java.lang.reflect.Method;

/**
 * Created by dima on 07.02.15.
 */
public class AnnotationProcessor {
    public static void main(String[] args) {
        inspectService(SimpleService.class);
        inspectService(LazyService.class);
        inspectService(String.class);
    }

    static void inspectService(Class<?> service) {
        if (service.isAnnotationPresent(Service.class)) {
            Service ann = service.getAnnotation(Service.class);
            System.out.println(ann.name() + "; LazyLoad is "+ann.lazyLoad()
                    + "; Class's name is " + service.getName());
            boolean isInitMethod = false;
            for (Method method : service.getMethods()) {
                if(method.isAnnotationPresent(Init.class)) {
                    Init annInit = method.getAnnotation(Init.class);
                    System.out.println("There is method with init annotation!"+
                            "; SuppressException is "+annInit.suppressException());
                    isInitMethod = true;
                }
            }
            if (!isInitMethod){
                System.out.println("There is not method with init annotation!");
            }
        } else {
            System.out.println("Annotation not found");
        }
    }
}
