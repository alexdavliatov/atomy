package today.selfi.item.ui.api.view

import com.fasterxml.jackson.annotation.JsonSubTypes
import ru.adavliatov.atomy.common.domain.Id
import today.selfi.common.type.repeat.Repeat

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
class TaskDetailsView : ItemDetailsView
