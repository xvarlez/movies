package me.varlez.movies.common.di

import javax.inject.Scope

/**
 * Annotation scope for our child Dagger components.
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ChildScope