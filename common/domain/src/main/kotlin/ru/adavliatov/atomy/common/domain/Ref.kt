package ru.adavliatov.atomy.common.domain

import ru.adavliatov.atomy.common.domain.error.DomainErrors.EmptyRefError

interface ConsumerId
interface ConsumerRef
data class Ref(
  val consumer: ConsumerId,
  val ref: ConsumerRef? = null
) {
  fun checkedRef() = ref ?: throw EmptyRefError(this)
  @Suppress("unused")
  fun isEmpty() = ref == null

  @Suppress("unused")
  fun withRef(ref: ConsumerRef?) = copy(ref = ref)
}

interface WithRef {
  val ref: Ref

  val uncheckedRef: ConsumerRef?
    get() = ref.ref
  val checkedRef: ConsumerRef
    get() = ref.checkedRef()
}

data class Refs(
  val consumer: ConsumerId,
  val refs: Iterable<ConsumerRef>
)

@Suppress("unused")
fun Ref.toRefs() = Refs(consumer, setOf(ref).filterNotNull())
