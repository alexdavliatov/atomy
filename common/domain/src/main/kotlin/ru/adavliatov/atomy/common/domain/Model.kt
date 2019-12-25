package ru.adavliatov.atomy.common.domain

import ru.adavliatov.atomy.common.domain.States.DELETED
import ru.adavliatov.atomy.common.type.ref.*
import java.time.Instant
import java.time.Instant.now

interface WithState {
  val state: State

  fun hasState(state: State) = this.state == state
  fun hasState(states: Array<State>) = states.find { it == state }
  fun hasState(states: Set<State>) = states.find { it == state }
  fun isDeleted() = DELETED == state
  fun isNotDeleted() = !isDeleted()
  fun delete() = withState(DELETED)

  fun withState(state: State): WithState
}

interface WithAudited {
  val createdAt: Instant
  val modifiedAt: Instant

  @Suppress("unused")
  fun modified(ts: Instant = now()): WithAudited
}

interface WithEntity<E : WithEntity<E>> : WithId<E>
interface WithModel<M : WithModel<M>> : WithEntity<M>,
  WithState,
  WithAudited,
  WithRef

@Suppress("unused")
abstract class Model<M : WithModel<M>>(
  override val id: Id<M>,
  override val state: State,
  override val createdAt: Instant = now(),
  override val modifiedAt: Instant = now()
) : WithModel<M> {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Model<*>

    if (id != other.id) return false

    return true
  }

  override fun hashCode() = id.hashCode()
}
