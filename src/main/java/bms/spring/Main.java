package bms.spring;

import bms.spring.cache.LFUCache;
import bms.spring.cache.LRUCache;
import bms.spring.domain.Application;

public class Main {
    public static void main(String[] args) {
        LRUCache<Application, Integer> applicationCache = new LRUCache<>(3);
        Application app1 = new Application(1L, "Program1");
        Application app2 = new Application(2L, "Program2");
        Application app3 = new Application(3L, "Program3");
        Application app4 = new Application(4L, "Program4");
        Application app5 = new Application(5L, "Program5");
        Application app6 = new Application(6L, "Program6");

        applicationCache.put(app1, 1);
        applicationCache.put(app2, 2);
        applicationCache.put(app3, 3);
        applicationCache.get(app1);
        applicationCache.put(app4, 4); // app2 will be evicted
        System.out.println(applicationCache);
        applicationCache.get(app3);
        applicationCache.put(app5, 5); // app1 will be evicted
        applicationCache.put(app6, 6);
        System.out.println(applicationCache);

        System.out.println("------------- application LFU Cache ----------------");
        LFUCache<Application, Integer> applicationLFUCache = new LFUCache<>(3);
        applicationLFUCache.put(app1, 1);
        applicationLFUCache.put(app2, 2);
        applicationLFUCache.put(app3, 3);
        applicationLFUCache.get(app1);
        applicationLFUCache.put(app4, 4); // app2 will be evicted
        System.out.println(applicationLFUCache);
        applicationLFUCache.get(app3);
        applicationLFUCache.get(app3);
        applicationLFUCache.put(app5, 5); // app4 will be evicted
        applicationLFUCache.get(app5); //app1 will be evicted
        applicationLFUCache.put(app6, 6);
        System.out.println(applicationLFUCache);



    }
}