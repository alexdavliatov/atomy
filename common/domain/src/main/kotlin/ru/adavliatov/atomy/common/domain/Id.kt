package ru.adavliatov.atomy.common.domain

import ru.adavliatov.atomy.common.domain.error.DomainErrors.EmptyIdError
import ru.adavliatov.atomy.common.type.ref.*
import ru.adavliatov.atomy.common.type.ref.error.RefErrors.EmptyRefError
import java.util.*
import java.util.UUID.*
import kotlin.Long.Companion.MAX_VALUE
import kotlin.random.Random

@Suppress("unused")
data class Id<E : WithEntity<E>>(
  val id: Long? = null,
  val uid: UUID? = null,
  val ref: Ref,
  val model: Class<E>
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

  fun withId(id: Long) = copy(id = id)
  fun withUid(uid: UUID) = copy(uid = uid)
  fun withRef(ref: Ref) = copy(ref = ref)

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Id<*>

    if (id != other.id) return false
    if (model != other.model) return false

    return true
  }

  override fun hashCode() = id?.hashCode() ?: 0

  companion object {
    val random = Random

    inline fun <reified E : WithEntity<E>> newId(ref: Ref) =
      Id(ref = ref, model = E::class.java)

    inline fun <reified E : WithEntity<E>> newId(consumerId: ConsumerId) =
      Id(ref = Ref(consumerId), model = E::class.java)

    inline fun <reified E : WithEntity<E>> randomIdWith(ref: Ref): Id<E> =
      Id(
        random.nextLong(0, MAX_VALUE),
        randomUUID(),
        ref,
        E::class.java
      )

    inline fun <reified E : WithEntity<E>> randomNewIdWith(ref: Ref): Id<E> =
      Id(
        uid = randomUUID(),
        ref = ref,
        model = E::class.java
      )
  }
}

interface WithId<E : WithEntity<E>> : WithRef {
  val id: Id<E>
  val checkedId: Long
    get() = id.checkedId
  override val ref: Ref
    get() = id.ref

  @Suppress("unused")
  fun withId(id: Id<E>): WithId<E>
}
