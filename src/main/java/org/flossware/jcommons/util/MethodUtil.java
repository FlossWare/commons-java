package org.flossware.jcommons.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Same vein as <code>java.util.Collections</code>
 *
 * @author sfloess
 */
public class MethodUtil {
    /**
     * Our logger.
     */
    private static final Logger logger = Logger.getLogger(MethodUtil.class.getName());

    /**
     * Return the logger.
     */
    private static Logger getLogger() {
        return logger;
    }

    private MethodUtil() {
    }

    /**
     * Finds all methods in a class that are annotated with a specific annotation class.
     *
     * @param klass the class to search for annotated methods
     * @param annotationClass the annotation class to search for
     * @return a list of methods that have the specified annotation
     */
    public static List<Method> findMethodsForAnnotationClass(final Class klass, final Class annotationClass) {
        // To do:  param validation
        return Arrays.stream(klass.getMethods()).filter(t -> t.isAnnotationPresent(annotationClass)).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Finds all methods in a class that are annotated with a specific annotation.
     *
     * @param klass the class to search for annotated methods
     * @param annotation the annotation instance to search for
     * @return a list of methods that have the specified annotation
     */
    public static List<Method> findMethodsForAnnotation(final Class klass, final Annotation annotation) {
        // To do:  param valdation
        return findMethodsForAnnotationClass(klass, annotation.getClass());
    }

    /**
     * Finds the first annotation of a specific type on any method in a class.
     *
     * @param <T> the annotation type
     * @param klass the class to search for annotated methods
     * @param annotationClass the annotation class to search for
     * @return the first annotation found on any method, or null if not found
     */
    public static <T extends Annotation> T findAnnotationOnMethods(final Class klass, final Class annotationClass) {
        // To do:  param validation

        for (final Method method : klass.getMethods()) {
            if (null != method.getAnnotation(annotationClass)) {
                return (T) method.getAnnotation(annotationClass);
            }
        }

        // TO DO:  Use streams
        //return Arrays.stream(klass.getMethods()).filter(t -> t.isAnnotationPresent(annotationClass)).collect(Collectors.toCollection(ArrayList::new));

        return null;

    }

    /**
     * Finds the first annotation matching the given annotation instance on any method in a class.
     *
     * @param <T> the annotation type
     * @param klass the class to search for annotated methods
     * @param annotation the annotation instance to search for
     * @return the first matching annotation found on any method, or null if not found
     */
    public static <T extends Annotation> T findAnnotationOnMethods(final Class klass, final T annotation) {
        // To do:  param validation
        return findAnnotationOnMethods(klass, annotation.getClass());
    }
}
