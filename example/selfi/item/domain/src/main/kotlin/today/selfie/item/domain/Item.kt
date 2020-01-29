package today.selfie.item.domain

import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.State
import ru.adavliatov.atomy.common.domain.WithModel
import ru.adavliatov.atomy.common.type.name.NameValue
import ru.adavliatov.atomy.common.type.name.ValueHolder
import today.selfie.common.type.duration.Duration
import today.selfie.common.type.repeat.Repeat
import today.selfie.item.domain.ItemTitles.GOAL
import today.selfie.item.domain.ItemTitles.HABIT
import today.selfie.item.domain.ItemTitles.NONE
import today.selfie.item.domain.ItemTitles.TASK
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
