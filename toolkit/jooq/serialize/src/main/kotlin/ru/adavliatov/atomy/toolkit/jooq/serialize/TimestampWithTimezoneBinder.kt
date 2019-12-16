package ru.adavliatov.atomy.toolkit.jooq.serialize

import org.jooq.*
import org.jooq.impl.DSL
import java.sql.SQLException
import java.sql.SQLFeatureNotSupportedException
import java.sql.Types.VARCHAR
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset.UTC
import java.util.*

class TimestampWithTimezoneBinder : Binding<OffsetDateTime, Instant> {

  override fun converter() = object : Converter<OffsetDateTime, Instant> {
    override fun from(t: OffsetDateTime?) = t?.toInstant()

    override fun to(u: Instant?) = u?.let { OffsetDateTime.ofInstant(u, UTC) }

    override fun fromType() = OffsetDateTime::class.java

    override fun toType() = Instant::class.java
  }

  @Throws(SQLException::class)
  override fun sql(ctx: BindingSQLContext<Instant>) {
    ctx.render().visit(DSL.`val`(ctx.convert(converter()).value()))
  }

  @Throws(SQLException::class)
  override fun register(ctx: BindingRegisterContext<Instant>) =
      ctx.statement().registerOutParameter(ctx.index(), VARCHAR)

  @Throws(SQLException::class)
  override fun set(ctx: BindingSetStatementContext<Instant>) = ctx.statement()
      .setString(ctx.index(), Objects.toString(ctx.convert(converter()).value(), null))

  @Throws(SQLException::class)
  override fun set(ctx: BindingSetSQLOutputContext<Instant>) = throw SQLFeatureNotSupportedException()

  @Throws(SQLException::class)
  override fun get(ctx: BindingGetResultSetContext<Instant>) = ctx.convert(converter()).value(ctx.resultSet().getObject(ctx.index(), OffsetDateTime::class.java))

  @Throws(SQLException::class)
  override fun get(ctx: BindingGetStatementContext<Instant>) = ctx.convert(converter()).value(ctx.statement().getObject(ctx.index(), OffsetDateTime::class.java))

  @Throws(SQLException::class)
  override fun get(ctx: BindingGetSQLInputContext<Instant>) = throw SQLFeatureNotSupportedException()
}
