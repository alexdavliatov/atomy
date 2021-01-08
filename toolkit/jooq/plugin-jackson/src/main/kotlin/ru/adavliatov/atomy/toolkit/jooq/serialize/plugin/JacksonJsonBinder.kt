package ru.adavliatov.atomy.toolkit.jooq.serialize.plugin

import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.databind.ObjectMapper
import org.jooq.Binding
import org.jooq.BindingGetResultSetContext
import org.jooq.BindingGetSQLInputContext
import org.jooq.BindingGetStatementContext
import org.jooq.BindingRegisterContext
import org.jooq.BindingSQLContext
import org.jooq.BindingSetSQLOutputContext
import org.jooq.BindingSetStatementContext
import org.jooq.Converter
import org.jooq.JSONB
import org.jooq.impl.DSL
import ru.adavliatov.atomy.common.type.json.impl.JacksonJson
import ru.adavliatov.atomy.common.type.json.impl.JacksonJson.Companion.toJson
import java.sql.SQLException
import java.sql.SQLFeatureNotSupportedException
import java.sql.Types.VARCHAR
import java.util.Objects

class JacksonJsonBinder(val mapper: ObjectMapper = ObjectMapper().configure(FAIL_ON_UNKNOWN_PROPERTIES, false)) :
  Binding<JSONB, JacksonJson> {

  override fun converter() = object : Converter<JSONB, JacksonJson> {
    override fun from(t: JSONB?): JacksonJson =
      (t?.let { mapper.readTree(t.data()) } ?: mapper.nullNode()).toJson()

    override fun to(u: JacksonJson?): JSONB? = u?.run { JSONB.valueOf(mapper.writeValueAsString(node)) }

    override fun fromType() = JSONB::class.java

    @Suppress("UNCHECKED_CAST")
    override fun toType(): Class<JacksonJson> = JacksonJson::class.java
  }

  @Throws(SQLException::class)
  override fun sql(ctx: BindingSQLContext<JacksonJson>) {
    ctx.render().visit(DSL.`val`(ctx.convert(converter()).value())).sql("::jsonb")
  }

  @Throws(SQLException::class)
  override fun register(ctx: BindingRegisterContext<JacksonJson>) =
    ctx.statement().registerOutParameter(ctx.index(), VARCHAR)

  @Throws(SQLException::class)
  override fun set(ctx: BindingSetStatementContext<JacksonJson>) = ctx.statement()
    .setString(ctx.index(), Objects.toString(ctx.convert(converter()).value(), null))

  @Throws(SQLException::class)
  override fun set(ctx: BindingSetSQLOutputContext<JacksonJson>) = throw SQLFeatureNotSupportedException()

  @Throws(SQLException::class)
  override fun get(ctx: BindingGetResultSetContext<JacksonJson>) =
    ctx.convert(converter()).value(JSONB.valueOf(ctx.resultSet().getString(ctx.index())))

  @Throws(SQLException::class)
  override fun get(ctx: BindingGetStatementContext<JacksonJson>) =
    ctx.convert(converter()).value(JSONB.valueOf(ctx.statement().getString(ctx.index())))

  @Throws(SQLException::class)
  override fun get(ctx: BindingGetSQLInputContext<JacksonJson>) = throw SQLFeatureNotSupportedException()
}
