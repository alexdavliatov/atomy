package ru.adavliatov.atomy.common.ui.api.serializer

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.module.kotlin.KotlinModule
import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.State
import ru.adavliatov.atomy.common.type.ref.Ref
import java.time.Instant

object JsonMapper {
  private val customModule = SimpleModule("role").apply {
    addSerializer(AsStringSerializer(State::class.java) { it.name })
    addSerializer(Id::class.java, IdSerializer())
    addSerializer(AsLongSerializer(Instant::class.java) { it.toEpochMilli() })
//    addSerializer(AsStringSerializer(PrincipalTitle::class.java) { it.title })
//    addSerializer(AsStringSerializer(ResourceTitle::class.java) { it.title })

    addDeserializer(Ref::class.java, RefDeserializer)
    addDeserializer(Id::class.java, IdDeserializer)
    addDeserializer(AsStringDeserializer(State::class.java) { State(it) })
    addDeserializer(AsLongDeserializer(Instant::class.java) { Instant.ofEpochMilli(it) })
//    addDeserializer(AsStringDeserializer(PrincipalTitle::class.java) { PrincipalTitle(it) })
//    addDeserializer(AsStringDeserializer(ResourceTitle::class.java) { ResourceTitle(it) })

  }

  fun mapper(): ObjectMapper = ObjectMapper().registerModules(modules)
    .apply {
      setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
      setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
    }

  val modules = listOf(
    customModule,
    Jdk8Module(),
//      JavaTimeModule(),
    KotlinModule()
  )
}

fun <T> SimpleModule.addDeserializer(deserializer: AsStringDeserializer<T>) =
  addDeserializer(deserializer.klass, deserializer)

fun <T> SimpleModule.addDeserializer(deserializer: AsLongDeserializer<T>) =
  addDeserializer(deserializer.klass, deserializer)
