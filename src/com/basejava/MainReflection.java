package com.basejava;

import com.basejava.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainReflection {

    public static void main(String[] args) throws Exception {
        Resume r = new Resume();
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "new_uuid");
        Method toStringMethod = r.getClass().getMethod("toString");
        String result = (String) toStringMethod.invoke(r);
        System.out.println(result);
    }
}