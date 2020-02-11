package today.selfi.item.ui.api.serialize

import com.fasterxml.jackson.databind.module.SimpleModule
import ru.adavliatov.atomy.common.type.name.NameValue
import ru.adavliatov.atomy.common.ui.api.serializer.AsStringDeserializer
import ru.adavliatov.atomy.common.ui.api.serializer.AsStringSerializer
import ru.adavliatov.atomy.common.ui.api.serializer.DetailsDeserializer
import ru.adavliatov.atomy.common.ui.api.serializer.addDeserializer
import today.selfi.item.domain.ItemTitle
import today.selfi.item.view.ItemDetailsResolver.detailsClass
import today.selfi.item.view.ItemDetailsView

object ItemModule {
  val itemModule = SimpleModule("item").apply {
    addSerializer(AsStringSerializer(ItemTitle::class.java) { it.name.name })
    addDeserializer(AsStringDeserializer(ItemTitle::class.java) { ItemTitle(NameValue(it)) })
    addDeserializer(DetailsDeserializer(ItemDetailsView::class.java) { title ->
      ItemTitle(NameValue(title)).detailsClass()
    })
  }
}