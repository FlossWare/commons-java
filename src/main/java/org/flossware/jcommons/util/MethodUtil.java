package org.flossware.jcommons.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Same vein as <code>java.util.Collections</code>
 *
 * @author sfloess
 */
public final class MethodUtil {
    private MethodUtil() {
        throw new AssertionError("Utility class - do not instantiate");
    }

    /**
     * Finds all public methods in a class that are annotated with a specific annotation class.
     *
     * <p>Note: Only public methods are searched (inherited and declared). Protected, package-private,
     * and private methods are not included in the search.
     *
     * @param klass the class to search for annotated methods
     * @param annotationClass the annotation class to search for
     * @return a list of public methods that have the specified annotation
     */
    public static List<Method> findMethodsForAnnotationClass(final Class<?> klass, final Class<? extends Annotation> annotationClass) {
        Objects.requireNonNull(klass, "Class must not be null");
        Objects.requireNonNull(annotationClass, "Annotation class must not be null");
        return Arrays.stream(klass.getMethods()).filter(t -> t.isAnnotationPresent(annotationClass)).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Finds all public methods in a class that are annotated with a specific annotation.
     *
     * <p>Note: Only public methods are searched (inherited and declared). Protected, package-private,
     * and private methods are not included in the search.
     *
     * @param klass the class to search for annotated methods
     * @param annotation the annotation instance to search for
     * @return a list of public methods that have the specified annotation
     */
    public static List<Method> findMethodsForAnnotation(final Class<?> klass, final Annotation annotation) {
        Objects.requireNonNull(klass, "Class must not be null");
        Objects.requireNonNull(annotation, "Annotation must not be null");
        return findMethodsForAnnotationClass(klass, annotation.annotationType());
    }

    /**
     * Finds the first annotation of a specific type on any public method in a class.
     *
     * <p>Note: Only public methods are searched (inherited and declared). Protected, package-private,
     * and private methods are not included in the search.
     *
     * @param <T> the annotation type
     * @param klass the class to search for annotated methods
     * @param annotationClass the annotation class to search for
     * @return Optional containing the first annotation found on a public method, or empty if not found
     */
    public static <T extends Annotation> Optional<T> findAnnotationOnMethods(final Class<?> klass, final Class<T> annotationClass) {
        Objects.requireNonNull(klass, "Class must not be null");
        Objects.requireNonNull(annotationClass, "Annotation class must not be null");

        for (final Method method : klass.getMethods()) {
            T annotation = method.getAnnotation(annotationClass);
            if (annotation != null) {
                return Optional.of(annotation);
            }
        }

        return Optional.empty();
    }

    /**
     * Finds the first annotation matching the given annotation instance on any public method in a class.
     *
     * <p>Note: Only public methods are searched (inherited and declared). Protected, package-private,
     * and private methods are not included in the search.
     *
     * @param <T> the annotation type
     * @param klass the class to search for annotated methods
     * @param annotation the annotation instance to search for
     * @return Optional containing the first matching annotation found on a public method, or empty if not found
     */
    @SuppressWarnings("unchecked") // Safe: Cast from Optional<? extends Annotation> to Optional<T> where T is the annotation type
    public static <T extends Annotation> Optional<T> findAnnotationOnMethods(final Class<?> klass, final T annotation) {
        Objects.requireNonNull(klass, "Class must not be null");
        Objects.requireNonNull(annotation, "Annotation must not be null");
        return (Optional<T>) findAnnotationOnMethods(klass, annotation.annotationType());
    }
}
