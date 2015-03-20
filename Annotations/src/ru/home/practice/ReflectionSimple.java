package ru.home.practice;

import ru.home.practice.annotations.Init;
import ru.home.practice.annotations.Service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by dima on 07.02.15.
 */
public class ReflectionSimple {
    static Map<String, Object> servicesMap = new HashMap<>();
    static Set<Object> initializedServicesSet = new HashSet<>();

    public static void main(String[] args) {
        loadService("ru.home.practice.services.SimpleService");
        loadService("ru.home.practice.services.LazyService");
        loadService("java.lang.String");
        if (servicesMap.get("Super imple service") != null) {
            System.out.println("It is found service \"Super imple service\"");
        } else {
            System.out.println("Service is not found");
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                invokeInitMethodFromServices(true);
            }
        }).start();
        invokeInitMethodFromServices(false);
    }

    private static Object getServiceByName(String serviceName) {
        Object service = servicesMap.get(serviceName);
        if (!initializedServicesSet.contains(service)) {
            invokeInitMethod(service, true);
        }
        return service;
    }

    synchronized private static void invokeInitMethod(Object service, boolean lazyLoadService) {
        if (!initializedServicesSet.contains(service)) {
            Class<?> clazz = service.getClass();
            boolean lazyLoad = false;
            if (clazz.isAnnotationPresent(Service.class)) {
                Service annService = clazz.getAnnotation(Service.class);
                lazyLoad = annService.lazyLoad();
            }
            if ((!lazyLoad && !lazyLoadService) || (lazyLoad && lazyLoadService)) {
                for (Method method : clazz.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(Init.class)) {
                        try {
                            System.out.println("Name thread: "+Thread.currentThread().getName());
                            method.setAccessible(true);
                            method.invoke(service);
                            initializedServicesSet.add(service);
                        } catch (Exception e) {
                            Init annIntt = method.getAnnotation(Init.class);
                            if (annIntt.suppressException()) {
                                System.err.println(e.getMessage());
                            } else {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            }
        }
    }

    private static void invokeInitMethodFromServices(boolean lazyLoad) {
        for (Map.Entry<String, Object> entry : servicesMap.entrySet()) {
            Object service = entry.getValue();
            invokeInitMethod(service, lazyLoad);
        }
    }

    static void loadService(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            if (clazz.isAnnotationPresent(Service.class)) {
                Object serviceObject = clazz.newInstance();
                String nameService = clazz.getAnnotation(Service.class).name();
                servicesMap.put(nameService, serviceObject);
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
