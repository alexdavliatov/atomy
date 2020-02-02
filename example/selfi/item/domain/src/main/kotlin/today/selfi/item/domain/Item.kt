package today.selfi.item.domain

import ru.adavliatov.atomy.common.domain.*
import ru.adavliatov.atomy.common.type.name.*
import today.selfi.common.type.duration.Duration
import today.selfi.common.type.repeat.Repeat
import today.selfi.item.domain.ItemTitles.GOAL
import today.selfi.item.domain.ItemTitles.HABIT
import today.selfi.item.domain.ItemTitles.NONE
import today.selfi.item.domain.ItemTitles.TASK
import java.time.Instant

typealias OwnerId = Long

data class Item(
  override val id: Id<Item>,
  override val state: State,
  override val createdAt: Instant,
  override val modifiedAt: Instant = createdAt,

  val name: NameValue,
  val duration: Duration?,
  val ownerId: OwnerId,
  val details: ItemDetails
) : WithModel<Item> {
  val title: ItemTitle
    get() = details.title

  override fun withId(id: Id<Item>): Item = copy(id = id)
  override fun withState(state: State): Item = copy(state = state)
  override fun modified(ts: Instant): Item = copy(modifiedAt = ts)
}

data class ItemTitle(val name: NameValue) : ValueHolder<NameValue>(name)

object ItemTitles {
  val NONE = ItemTitle(NameValue("none"))
  val HABIT = ItemTitle(NameValue("habit"))
  val GOAL = ItemTitle(NameValue("goal"))
  val TASK = ItemTitle(NameValue("task"))
}

sealed class ItemDetails(val title: ItemTitle)

object MissingDetails : ItemDetails(NONE)
class HabitDetails(val repeat: Repeat) : ItemDetails(HABIT)
class GoalDetails(val parentId: Id<Item>?) : ItemDetails(GOAL)
class TaskDetails : ItemDetails(TASK)
