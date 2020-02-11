package today.selfi.item.view

import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.view.ext.IdExtensions.withModel
import today.selfi.common.type.repeat.Repeat
import today.selfi.item.domain.GoalDetails
import today.selfi.item.domain.HabitDetails
import today.selfi.item.domain.ItemDetails
import today.selfi.item.domain.ItemTitle
import today.selfi.item.domain.ItemTitles.GOAL
import today.selfi.item.domain.ItemTitles.HABIT
import today.selfi.item.domain.ItemTitles.NONE
import today.selfi.item.domain.ItemTitles.TASK
import today.selfi.item.domain.MissingDetails
import today.selfi.item.domain.TaskDetails
import today.selfi.item.domain.TaskPriority

//@JsonSubTypes(
//  value = [
//    JsonSubTypes.Type(MissingDetailsView::class, name = "none"),
//    JsonSubTypes.Type(HabitDetailsView::class, name = "habit"),
//    JsonSubTypes.Type(GoalDetailsView::class, name = "goal"),
//    JsonSubTypes.Type(TaskDetailsView::class, name = "task")
//  ]
//)
interface ItemDetailsView {
  val title: ItemTitle
}

data class MissingDetailsView(override val title: ItemTitle = NONE) : ItemDetailsView
data class HabitDetailsView(override val title: ItemTitle = HABIT, val repeat: Repeat) : ItemDetailsView
data class GoalDetailsView(override val title: ItemTitle = GOAL, val parentId: Id<*>?) : ItemDetailsView
class TaskDetailsView(override val title: ItemTitle = TASK, val priority: TaskPriority) : ItemDetailsView

object ItemDetailsResolver {
  fun ItemDetailsView.toDetails() = when (this) {
    is HabitDetailsView -> HabitDetails(repeat)
    is TaskDetailsView -> TaskDetails(priority)
    is GoalDetailsView -> GoalDetails(parentId?.withModel())
    else -> MissingDetails
  }

  fun ItemTitle.detailsClass(): Class<out ItemDetailsView> = when (this) {
    TASK -> TaskDetailsView::class.java
    HABIT -> HabitDetailsView::class.java
    GOAL -> GoalDetailsView::class.java
    else -> MissingDetailsView::class.java
  }

  fun ItemDetails.toDetailsView() = when (this) {
    is HabitDetails -> HabitDetailsView(repeat = repeat)
    is TaskDetails -> TaskDetailsView(priority = priority)
    is GoalDetails -> GoalDetailsView(parentId = parentId)
    else -> MissingDetailsView()
  }
}