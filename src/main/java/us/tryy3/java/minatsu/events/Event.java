package us.tryy3.java.minatsu.events;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * Created by dennis.planting on 11/6/2015.
 */
public class Event {
    private List<EventType> registeredEvents = new ArrayList<>();

    public Event() {

    }

    public void registerEvents(Listener listener) {
        Class<?> clazz = listener.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            Parameter[] parameters = methods[i].getParameters();
            for (int p = 0; p < parameters.length; p++) {
                if (parameters[p].getType().getSuperclass().equals(Event.class)) {
                    registeredEvents.add(new EventType(methods[i], parameters[p].getType(), listener));
                }
            }
        }
    }

    public void callEvents(Event event) {
        try {
            for (EventType types : registeredEvents) {
                if (types.getType().equals(event.getClass())) {
                    types.getMethod().invoke(types.getListener(), event);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class EventType {
        Method method;
        Class<?> type;
        Listener listener;

        public EventType(Method method, Class<?> type, Listener listener) {
            this.method = method;
            this.type = type;
            this.listener = listener;
        }

        public Method getMethod() {
            return method;
        }

        public Class<?> getType() {
            return type;
        }

        public Listener getListener() {
            return listener;
        }

        public void setListener(Listener listener) {
            this.listener = listener;
        }

        public void setMethod(Method method) {
            this.method = method;
        }

        public void setType(Class<?> type) {
            this.type = type;
        }
    }
}
