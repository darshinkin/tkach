package ru.home.practice.services;

import ru.home.practice.annotations.Init;
import ru.home.practice.annotations.Service;

/**
 * Created by dima on 07.02.15.
 */
@Service(name = "Super imple service", lazyLoad = true)
public class SimpleService {
    @Init
    public void initService() {
        System.out.println("SimpleService!");
    }

    public void stubMethod() {
        System.out.println("Stub method");
    }
}
