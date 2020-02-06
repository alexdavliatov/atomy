package ru.adavliatov.atomy.common.ui.api

import ru.adavliatov.atomy.common.type.chunk.Chunk
import ru.adavliatov.atomy.common.type.page.Page
import ru.adavliatov.atomy.common.type.page.ext.CollectionExtensions.reversed
import ru.adavliatov.atomy.common.ui.api.ext.CollectionExtensions.sorted

data class ListViewResponse<Model, View>(val count: Long, val items: List<Any?>) {
  constructor(
    models: Chunk<Model>,
    page: Page,
    modelToView: (Model) -> View,
    propertyExtractor: (View) -> Any?,
    propertyProjector: (View) -> Any? = { it }
  ) : this(
    models.total,
    models.items
      .map { modelToView(it) }
      .sorted { propertyExtractor(it) }
      .map { propertyProjector(it) }
      .reversed(page)
  )

  constructor(views: Chunk<View>) : this(views.total, views.items)
}
