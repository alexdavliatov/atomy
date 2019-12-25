package ru.adavliatov.atomy.toolkit.jooq.serialize.plugin.jackson

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
import org.jooq.impl.DSL
import ru.adavliatov.common.type.json.impl.gson.*
import ru.adavliatov.common.type.json.impl.gson.JacksonJson.Companion.toJson
import java.sql.SQLException
import java.sql.SQLFeatureNotSupportedException
import java.sql.Types.VARCHAR
import java.util.*

class JsonBinder(val mapper: ObjectMapper = ObjectMapper().configure(FAIL_ON_UNKNOWN_PROPERTIES, false)) :
  Binding<Any, JacksonJson> {

  override fun converter() = object : Converter<Any, JacksonJson> {
    override fun from(t: Any?): JacksonJson =
      (t?.let { mapper.createObjectNode() } ?: mapper.readTree(t as String)).toJson()

    override fun to(u: JacksonJson?): String? = u?.run { mapper.writeValueAsString(node) }

    override fun fromType() = Any::class.java

    @Suppress("UNCHECKED_CAST")
    override fun toType(): Class<JacksonJson>? = JacksonJson::class.java
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
    ctx.convert(converter()).value(ctx.resultSet().getString(ctx.index()))

  @Throws(SQLException::class)
  override fun get(ctx: BindingGetStatementContext<JacksonJson>) =
    ctx.convert(converter()).value(ctx.statement().getString(ctx.index()))

  @Throws(SQLException::class)
  override fun get(ctx: BindingGetSQLInputContext<JacksonJson>) = throw SQLFeatureNotSupportedException()
}
