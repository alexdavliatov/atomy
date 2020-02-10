package today.selfi.item.view

import com.fasterxml.jackson.annotation.JsonSubTypes
import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.view.ext.IdExtensions.withModel
import today.selfi.common.type.repeat.Repeat
import today.selfi.item.domain.GoalDetails
import today.selfi.item.domain.HabitDetails
import today.selfi.item.domain.MissingDetails
import today.selfi.item.domain.TaskDetails
import today.selfi.item.domain.TaskPriority

@JsonSubTypes(
  value = [
    JsonSubTypes.Type(MissingDetailsView::class, name = "none"),
    JsonSubTypes.Type(HabitDetailsView::class, name = "habit"),
    JsonSubTypes.Type(GoalDetailsView::class, name = "goal"),
    JsonSubTypes.Type(TaskDetailsView::class, name = "task")
  ]
)
interface ItemDetailsView

object MissingDetailsView : ItemDetailsView
data class HabitDetailsView(val repeat: Repeat) : ItemDetailsView
data class GoalDetailsView(val parentId: Id<*>?) : ItemDetailsView
class TaskDetailsView(val priority: TaskPriority) : ItemDetailsView

object ItemDetailsResolver {
  fun ItemDetailsView.toDetails() = when (this) {
    is HabitDetailsView -> HabitDetails(repeat)
    is TaskDetailsView -> TaskDetails(priority)
    is GoalDetailsView -> GoalDetails(parentId?.withModel())
    else -> MissingDetails
  }
}