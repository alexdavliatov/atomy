package ru.adavliatov.atomy.toolkit.jooq.ext

import org.jooq.Condition
import org.jooq.Configuration
import org.jooq.TableField
import org.jooq.TableRecord
import org.jooq.impl.DSL
import org.jooq.impl.DSL.`val`
import org.jooq.impl.DSL.all
import org.jooq.impl.DSL.any
import org.jooq.impl.DataSourceConnectionProvider
import org.jooq.impl.DefaultConfiguration
import org.jooq.util.postgres.PostgresDSL.arrayOverlap
import javax.sql.DataSource

@Suppress("unused")
object JooqExtensions {
  val Configuration.ds: DataSource
    get() = (connectionProvider() as DataSourceConnectionProvider).dataSource()

  fun DataSource.toJooqConfig(): Configuration = DefaultConfiguration()
    .set(this)
}

object PostgresJooqExtensions {
  fun <R : TableRecord<R>, V> TableField<R, Array<V>>.arrayContains(value: V) = `val`(value).eq(any(this))
  fun <R : TableRecord<R>, V> TableField<R, Array<V>>.arrayNotContains(value: V) = `val`(value).notEqual(all(this))

  inline fun <R : TableRecord<R>, reified V> TableField<R, Array<V>>.arrayContains(values: Collection<V>) =
    if (values.size == 1) arrayContains(values.first())
    else arrayOverlap(`val`(values.toTypedArray()), this)

  inline fun <R : TableRecord<R>, reified V> TableField<R, Array<V>>.arrayNotContainsAny(values: Collection<V>) =
    if (values.size == 1) arrayNotContains(values.first())
    else arrayOverlap(`val`(values.toTypedArray()), this).not()

  inline fun <R : TableRecord<R>, reified V> TableField<R, Array<V>>.arrayContainsOrTrueIfEmpty(values: Collection<V>) =
    if (values.isEmpty()) DSL.trueCondition()
    else arrayContains(values)

  val Configuration.ds: DataSource
    get() = (connectionProvider() as DataSourceConnectionProvider).dataSource()

  fun DataSource.toJooqConfig() = DefaultConfiguration()
    .set(this)
//    .set(POSTGRES_10)

  fun Iterable<Condition>.joinWithOr() = reduce { acc, condition -> acc.or(condition) }
  fun Iterable<Condition>.joinWithAnd() = reduce { acc, condition -> acc.or(condition) }

  fun Condition.joinWithOr(conditions: Iterable<Condition>) = conditions
    .fold(this) { acc, condition -> acc.or(condition) }

  fun Condition.joinWithAnd(conditions: Iterable<Condition>) = conditions
    .fold(this) { acc, condition -> acc.and(condition) }

  val Condition.isTrue
    get() = equals(DSL.trueCondition())

}