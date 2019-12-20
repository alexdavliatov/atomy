package today.selfie.domain

import ru.adavliatov.atomy.common.domain.*
import java.time.Instant

typealias Name = String

data class Item(
  override val id: Id<Item>,
  override val state: State,
  override val createdAt: Instant,
  override val modifiedAt: Instant,

  val name: Name,
  val title: ItemTitle
) : WithModel<Item> {
  override fun withId(id: Id<Item>): Item = copy(id = id)
  override fun withState(state: State): Item = copy(state = state)
  override fun modified(ts: Instant): Item = copy(modifiedAt = ts)
}

data class ItemTitle(val name: String)

object ItemTitles {
  val HABIT = ItemTitle("habit")
  val GOAL = ItemTitle("goal")
}
