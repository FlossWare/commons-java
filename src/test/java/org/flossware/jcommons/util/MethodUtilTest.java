package org.flossware.jcommons.util;

import org.junit.jupiter.api.Test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MethodUtilTest {

    @Retention(RetentionPolicy.RUNTIME)
    @interface TestAnnotation {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface AnotherAnnotation {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface NotPresentAnnotation {
    }

    static class TestClass {
        @TestAnnotation
        public void annotatedMethod1() {
        }

        @TestAnnotation
        public void annotatedMethod2() {
        }

        @AnotherAnnotation
        public void anotherAnnotatedMethod() {
        }

        public void nonAnnotatedMethod() {
        }
    }

    @Test
    void testFindMethodsForAnnotationClass() {
        List<Method> methods = MethodUtil.findMethodsForAnnotationClass(TestClass.class, TestAnnotation.class);
        assertEquals(2, methods.size());
        assertTrue(methods.stream().allMatch(m -> m.isAnnotationPresent(TestAnnotation.class)));
    }

    @Test
    void testFindMethodsForAnnotationClass_withNullClass() {
        assertThrows(NullPointerException.class, () ->
            MethodUtil.findMethodsForAnnotationClass(null, TestAnnotation.class));
    }

    @Test
    void testFindMethodsForAnnotationClass_withNullAnnotation() {
        assertThrows(NullPointerException.class, () ->
            MethodUtil.findMethodsForAnnotationClass(TestClass.class, null));
    }

    @Test
    void testFindMethodsForAnnotation() throws NoSuchMethodException {
        TestAnnotation annotation = TestClass.class.getMethod("annotatedMethod1").getAnnotation(TestAnnotation.class);
        List<Method> methods = MethodUtil.findMethodsForAnnotation(TestClass.class, annotation);
        assertEquals(2, methods.size());
    }

    @Test
    void testFindMethodsForAnnotation_withNullClass() throws NoSuchMethodException {
        TestAnnotation annotation = TestClass.class.getMethod("annotatedMethod1").getAnnotation(TestAnnotation.class);
        assertThrows(NullPointerException.class, () ->
            MethodUtil.findMethodsForAnnotation(null, annotation));
    }

    @Test
    void testFindMethodsForAnnotation_withNullAnnotation() {
        assertThrows(NullPointerException.class, () ->
            MethodUtil.findMethodsForAnnotation(TestClass.class, null));
    }

    @Test
    void testFindAnnotationOnMethods() {
        var annotation = MethodUtil.findAnnotationOnMethods(TestClass.class, TestAnnotation.class);
        assertTrue(annotation.isPresent());
    }

    @Test
    void testFindAnnotationOnMethods_notFound() {
        var annotation = MethodUtil.findAnnotationOnMethods(TestClass.class, NotPresentAnnotation.class);
        assertTrue(annotation.isEmpty());
    }

    @Test
    void testFindAnnotationOnMethods_withNullClass() {
        assertThrows(NullPointerException.class, () ->
            MethodUtil.findAnnotationOnMethods(null, TestAnnotation.class));
    }

    @Test
    void testFindAnnotationOnMethods_withNullAnnotationClass() {
        assertThrows(NullPointerException.class, () ->
            MethodUtil.findAnnotationOnMethods(TestClass.class, (Class<TestAnnotation>) null));
    }

    @Test
    void testFindAnnotationOnMethodsWithInstance() throws NoSuchMethodException {
        TestAnnotation annotationInstance = TestClass.class.getMethod("annotatedMethod1").getAnnotation(TestAnnotation.class);
        var foundAnnotation = MethodUtil.findAnnotationOnMethods(TestClass.class, annotationInstance);
        assertTrue(foundAnnotation.isPresent());
    }

    @Test
    void testFindAnnotationOnMethodsWithInstance_withNullClass() throws NoSuchMethodException {
        TestAnnotation annotation = TestClass.class.getMethod("annotatedMethod1").getAnnotation(TestAnnotation.class);
        assertThrows(NullPointerException.class, () ->
            MethodUtil.findAnnotationOnMethods(null, annotation));
    }

    @Test
    void testFindAnnotationOnMethodsWithInstance_withNullAnnotation() {
        assertThrows(NullPointerException.class, () ->
            MethodUtil.findAnnotationOnMethods(TestClass.class, (TestAnnotation) null));
    }

    @Test
    void testPrivateConstructor() throws Exception {
        Constructor<MethodUtil> constructor = MethodUtil.class.getDeclaredConstructor();
        assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

}
