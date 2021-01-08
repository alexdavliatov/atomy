package ru.adavliatov.atomy.common.ext

import java.util.HashSet

object CollectionExtensions {
  fun <T, R> Iterable<T>.mapToSet(transform: (T) -> R): Set<R> = mapTo(HashSet(), transform)
  fun <T, R> Array<T>.mapToSet(transform: (T) -> R): Set<R> = mapTo(HashSet(), transform)
  fun <T, R> Iterable<T>.flatMapToSet(transform: (T) -> Iterable<R>): Set<R> = flatMapTo(HashSet(), transform)
  fun <T, R> Sequence<T>.mapToSet(transform: (T) -> R): Set<R> = mapTo(HashSet(), transform)
  fun <T, R : Any> Iterable<T>.mapNotNullToSet(transform: (T) -> R): Set<R> = mapNotNullTo(HashSet(), transform)
  fun <T> Iterable<T>?.toSetOrEmpty(): Set<T> = this?.toSet() ?: setOf()
  fun <T> Iterable<T>?.toListOrEmpty(): List<T> = this?.toList() ?: listOf()
  fun <T> List<T>.getOrThrow(index: Int, exception: () -> RuntimeException): T =
    getOrNull(index) ?: throw exception()

  fun <T> Iterable<T>.firstOrThrow(error: () -> RuntimeException): T = firstOrNull() ?: throw error()
  fun <T> Sequence<T>.firstOrThrow(error: () -> RuntimeException): T = firstOrNull() ?: throw error()
}
