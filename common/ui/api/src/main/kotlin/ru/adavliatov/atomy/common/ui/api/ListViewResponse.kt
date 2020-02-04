package ru.adavliatov.atomy.common.ui.api

import ru.adavliatov.atomy.common.type.chunk.*
import ru.adavliatov.atomy.common.type.page.*
import ru.adavliatov.atomy.common.type.page.ext.CollectionExtensions.reversed
import ru.adavliatov.atomy.common.ui.api.ext.CollectionExtensions.sortedBy

data class ListViewResponse<Model, View : Any>(val count: Long, val items: List<Any?>) {
  constructor(
    models: Chunk<Model>,
    page: Page,
    modelToView: (Model) -> View,
    propertyExtractor: (View) -> Any?,
    propertyProjector: (View) -> Any? = { it }
  ) : this(
    models.total,
    models.items.map { modelToView(it) }
      .sortedBy { propertyExtractor(it) }
      .map { propertyProjector(it) }
      .reversed(page)
  )

  constructor(views: Chunk<View>) : this(views.total, views.items)
}
