package com.core.corelib.eventbus3;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by admin on 16/2/18.
 */
public class EventBus3Helper {

    private static EventBus bus3 = null;

    public static void init(boolean debug) {

        bus3 = EventBus.builder()
                .logNoSubscriberMessages(false)
                .sendNoSubscriberEvent(false)
                .throwSubscriberException(debug)
                .build();
    }

    private static void checkBusInit() {
        if (bus3 == null) {
            throw new RuntimeException("please invoke the method Bus3Helper.init()");
        }
    }

    public static void register(Object subscriber) {
        checkBusInit();
        bus3.register(subscriber);
    }

    public static void unregister(Object subscriber) {
        checkBusInit();
        bus3.unregister(subscriber);
    }

    public static void post(Object event) {
        checkBusInit();
        bus3.post(event);
    }

    public static void postSticky(Object event) {
        checkBusInit();
        bus3.postSticky(event);
    }




}
