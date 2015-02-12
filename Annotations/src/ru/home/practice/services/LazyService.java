package ru.home.practice.services;

import ru.home.practice.annotations.Init;
import ru.home.practice.annotations.Service;

/**
 * Created by dima on 07.02.15.
 */
@Service(name = "Very working service")
public class LazyService {
    @Init
    private void lazyInit() throws Exception {
        System.out.println("LazyService!");
    }
}
