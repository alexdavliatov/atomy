package ru.adavliatov.atomy.common.domain

import ru.adavliatov.atomy.common.domain.error.DomainErrors.EmptyIdError
import ru.adavliatov.atomy.common.type.ref.ConsumerId
import ru.adavliatov.atomy.common.type.ref.ConsumerRef
import ru.adavliatov.atomy.common.type.ref.Ref
import ru.adavliatov.atomy.common.type.ref.WithRef
import ru.adavliatov.atomy.common.type.ref.error.RefErrors.EmptyRefError
import java.util.UUID
import java.util.UUID.randomUUID
import kotlin.Long.Companion.MAX_VALUE
import kotlin.random.Random

@Suppress("unused")
data class Id<M : WithModel<M>>(
  val id: Long? = null,
  val uid: UUID? = null,
  val ref: Ref,
  val model: Class<M>
) {
  val isNew: Boolean
    get() = id == null

  val checkedId: Long
    get() = id ?: throw EmptyIdError(this)

  val checkedUid: UUID
    get() = uid ?: throw EmptyIdError(this)

  val uncheckedRef: ConsumerRef?
    get() = ref.ref
  val checkedRef: ConsumerRef
    get() = ref.ref ?: throw EmptyRefError(ref)

  fun withId(id: Long?) = copy(id = id)
  fun withUid(uid: UUID?) = copy(uid = uid)
  fun withRef(ref: Ref) = copy(ref = ref)

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Id<*>

    if (id != other.id) return false
    if (uid != other.uid) return false
    if (model != other.model) return false

    return true
  }

  override fun hashCode() = id?.hashCode() ?: 0

  companion object {
    val random = Random

    inline fun <reified M : WithModel<M>> newId(ref: Ref) =
      Id(ref = ref, model = M::class.java)

    fun <M : WithModel<M>> newId(ref: Ref, modelClass: Class<M>) =
      Id(ref = ref, model = modelClass)

    inline fun <reified M : WithModel<M>> newId(consumerId: ConsumerId) =
      Id(ref = Ref(consumerId), model = M::class.java)

    inline fun <reified M : WithModel<M>> randomIdWith(ref: Ref): Id<M> =
      Id(
        random.nextLong(0, MAX_VALUE),
        randomUUID(),
        ref,
        M::class.java
      )

    inline fun <reified M : WithModel<M>> randomNewIdWith(ref: Ref): Id<M> =
      Id(
        uid = randomUUID(),
        ref = ref,
        model = M::class.java
      )
  }
}

interface WithId<M : WithModel<M>> : WithRef {
  val id: Id<M>
  val checkedId: Long
    get() = id.checkedId
  val checkedUid: UUID
    get() = id.checkedUid
  override val ref: Ref
    get() = id.ref

  @Suppress("unused")
  fun withId(id: Id<M>): WithId<M>
}
