package today.selfie.domain

import ru.adavliatov.atomy.common.domain.*
import ru.adavliatov.atomy.common.type.name.*
import today.selfie.common.type.duration.Duration
import today.selfie.common.type.repeat.Repeat
import java.time.Instant
import java.time.Period

data class Item(
  override val id: Id<Item>,
  override val state: State,
  override val createdAt: Instant,
  override val modifiedAt: Instant,

  val name: NameValue,
  val repeat: Repeat,
  val duration: Duration,
  val title: ItemTitle
) : WithModel<Item> {
  override fun withId(id: Id<Item>): Item = copy(id = id)
  override fun withState(state: State): Item = copy(state = state)
  override fun modified(ts: Instant): Item = copy(modifiedAt = ts)
}

data class ItemTitle(val name: NameValue): ValueHolder<NameValue>(name)

object ItemTitles {
  val HABIT = ItemTitle(NameValue("habit"))
  val GOAL = ItemTitle(NameValue("goal"))
}
