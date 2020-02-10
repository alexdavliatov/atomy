package today.selfi.item.ui.api.ext

import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.type.ref.ConsumerId
import ru.adavliatov.atomy.common.type.ref.Ref
import today.selfi.item.domain.Item
import today.selfi.item.service.repo.ItemRepo
import today.selfi.item.view.ItemView
import java.util.UUID

object ItemViewExtensions {
  fun ItemView.toModel(
    uid: UUID?,
    consumer: ConsumerId
  ): (ItemRepo) -> Item? = { repo ->
    val id = Id
      .newId<Item>(Ref(consumer))
      .withUid(uid)

    repo.findById(id)
  }
}