package guru.qa;

import org.junit.jupiter.api.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class JunitCore {

    public static void main(String[] args) throws Exception {
        // lookup classes with annotation @Test
        // here we go with class SimpleTest.class

        Class clazz = SimpleTest.class;
        ArrayList<Method> beforeAllArrayList = new ArrayList<>();
        ArrayList<Method> afterAllArrayList = new ArrayList<>();
        ArrayList<Method> beforeEachArrayList = new ArrayList<>();
        ArrayList<Method> testArrayList = new ArrayList<>();
        ArrayList<Method> afterEachArrayList = new ArrayList<>();

        //перебираем все методы и сортируем их списки по аннотациям
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getAnnotation(BeforeAll.class) != null) {
                beforeAllArrayList.add(method);
            } else if (method.getAnnotation(AfterAll.class) != null) {
                afterAllArrayList.add(method);
            } else if (method.getAnnotation(BeforeEach.class) != null) {
                beforeEachArrayList.add(method);
            } else if (method.getAnnotation(Test.class) != null) {
                testArrayList.add(method);
            } else if (method.getAnnotation(AfterEach.class) != null) {
                afterEachArrayList.add(method);
            }
        }

        //Запуск методов с аннотацией @BeforeAll
        for (Method beforeAllItem: beforeAllArrayList) {
            try {
                beforeAllItem.invoke(clazz.getConstructor().newInstance());
            } catch (InvocationTargetException e) {
                if (e.getCause() instanceof AssertionError) {
                    System.out.println("Test failed: " + beforeAllItem.getName());
                    continue;
                } else {
                    System.out.println("Test broken: " + beforeAllItem.getName());
                    continue;
                }
            }
            System.out.println("Test passed: " + beforeAllItem.getName());
        }

        for (Method testItem: testArrayList) {
            //Запуск всех методов с аннотацией beforeEach перед каждым запуском метода с аннотацией @Test
            for (Method beforeEachItem: beforeEachArrayList) {
                try {
                    beforeEachItem.invoke(clazz.getConstructor().newInstance());
                } catch (InvocationTargetException e) {
                    if (e.getCause() instanceof AssertionError) {
                        System.out.println("Test failed: " + beforeEachItem.getName());
                        continue;
                    } else {
                        System.out.println("Test broken: " + beforeEachItem.getName());
                        continue;
                    }
                }
                System.out.println("Test passed: " + beforeEachItem.getName());
            }
            // Запуск метода с аннотацией @Test
            try {
                testItem.invoke(clazz.getConstructor().newInstance());
            } catch (InvocationTargetException e) {
                if (e.getCause() instanceof AssertionError) {
                    System.out.println("Test failed: " + testItem.getName());
                    continue;
                } else {
                    System.out.println("Test broken: " + testItem.getName());
                    continue;
                }
            }
            System.out.println("Test passed: " + testItem.getName());

            //Запуск всех методов с аннотацией @AfterEach после запуска каждого метода с аннотацией @Test
            for (Method afterEachItem: afterEachArrayList) {
                try {
                    afterEachItem.invoke(clazz.getConstructor().newInstance());
                } catch (InvocationTargetException e) {
                    if (e.getCause() instanceof AssertionError) {
                        System.out.println("Test failed: " + afterEachItem.getName());
                        continue;
                    } else {
                        System.out.println("Test broken: " + afterEachItem.getName());
                        continue;
                    }
                }
                System.out.println("Test passed: " + afterEachItem.getName());
            }
        }

        //Запуск всех методов с аннотацией @BeforeAll
        for (Method afterAllItem: afterAllArrayList) {
            try {
                afterAllItem.invoke(clazz.getConstructor().newInstance());
            } catch (InvocationTargetException e) {
                if (e.getCause() instanceof AssertionError) {
                    System.out.println("Test failed: " + afterAllItem.getName());
                    continue;
                } else {
                    System.out.println("Test broken: " + afterAllItem.getName());
                    continue;
                }
            }
            System.out.println("Test passed: " + afterAllItem.getName());
        }
        // print results
    }
}