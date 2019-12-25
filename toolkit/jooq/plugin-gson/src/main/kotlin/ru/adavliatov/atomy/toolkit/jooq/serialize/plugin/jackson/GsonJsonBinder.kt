package ru.adavliatov.atomy.toolkit.jooq.serialize.plugin.jackson

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import org.jooq.Binding
import org.jooq.BindingGetResultSetContext
import org.jooq.BindingGetSQLInputContext
import org.jooq.BindingGetStatementContext
import org.jooq.BindingRegisterContext
import org.jooq.BindingSQLContext
import org.jooq.BindingSetSQLOutputContext
import org.jooq.BindingSetStatementContext
import org.jooq.Converter
import org.jooq.conf.ParamType
import org.jooq.impl.DSL
import ru.adavliatov.common.type.json.impl.gson.*
import ru.adavliatov.common.type.json.impl.gson.GsonJson.Companion.toJson
import java.sql.SQLException
import java.sql.SQLFeatureNotSupportedException
import java.sql.Types
import java.util.*

class GsonJsonBinder : Binding<Any, GsonJson> {
  override fun converter() = object : Converter<Any, GsonJson> {
    override fun from(t: Any?): GsonJson = (t?.let {
      Gson().fromJson("" + t, JsonElement::class.java)
    } ?: JsonNull.INSTANCE)
      .toJson()

    override fun to(u: GsonJson?) = if (u == null || u.node === JsonNull.INSTANCE) null else Gson().toJson(u)

    override fun fromType() = Any::class.java
    override fun toType(): Class<GsonJson> = GsonJson::class.java
  }

  @Throws(SQLException::class)
  override fun sql(ctx: BindingSQLContext<GsonJson>) {
    if (ctx.render().paramType() == ParamType.INLINED) ctx.render().visit(
      DSL.inline(
        ctx.convert(converter()).value()
      )
    ).sql("::json") else ctx.render().sql("?::json")
  }

  @Throws(SQLException::class)
  override fun register(ctx: BindingRegisterContext<GsonJson>) =
    ctx.statement().registerOutParameter(ctx.index(), Types.VARCHAR)

  @Throws(SQLException::class)
  override fun set(ctx: BindingSetStatementContext<GsonJson>) = ctx.statement()
    .setString(ctx.index(), Objects.toString(ctx.convert(converter()).value(), null))

  @Throws(SQLException::class)
  override fun get(ctx: BindingGetResultSetContext<GsonJson>) =
    ctx.convert(converter()).value(ctx.resultSet().getString(ctx.index()))

  @Throws(SQLException::class)
  override fun get(ctx: BindingGetStatementContext<GsonJson>) =
    ctx.convert(converter()).value(ctx.statement().getString(ctx.index()))

  @Throws(SQLException::class)
  override fun set(ctx: BindingSetSQLOutputContext<GsonJson>) = throw SQLFeatureNotSupportedException()

  @Throws(SQLException::class)
  override fun get(ctx: BindingGetSQLInputContext<GsonJson>) = throw SQLFeatureNotSupportedException()
}