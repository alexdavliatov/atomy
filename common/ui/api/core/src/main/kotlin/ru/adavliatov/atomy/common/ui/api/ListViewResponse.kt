package ru.adavliatov.atomy.common.ui.api

import ru.adavliatov.atomy.common.type.chunk.Chunk
import ru.adavliatov.atomy.common.type.chunk.ChunkedData
import ru.adavliatov.atomy.common.type.page.Page
import ru.adavliatov.atomy.common.type.page.ext.CollectionExtensions.reversed
import ru.adavliatov.atomy.common.ui.api.ext.CollectionExtensions.sorted

data class ListViewResponse<Model, View>(val total: Long, val chunk: Chunk, val items: List<Any?>) {
  constructor(
    models: ChunkedData<Model>,
    page: Page,
    modelToView: (Model) -> View,
    propertyExtractor: (View) -> Any?,
    propertyProjector: (View) -> Any? = { it }
  ) : this(
    models.total,
    Chunk(models.total.toInt(), 0),
    models.items
      .map { modelToView(it) }
      .sorted { propertyExtractor(it) }
      .map { propertyProjector(it) }
      .reversed(page)
  )

  constructor(
    views: ChunkedData<View>,
    page: Page,
    propertyExtractor: (View) -> Any?,
    propertyProjector: (View) -> Any? = { it }
  ) : this(
    views.total,
    views.chunk,
    views.items
      .sorted { propertyExtractor(it) }
      .map { propertyProjector(it) }
      .reversed(page)
  )

  constructor(views: ChunkedData<View>) : this(views.total, views.chunk, views.items)

  companion object {
    fun <View> by(
      views: ChunkedData<View>,
      page: Page,
      propertyExtractor: (View) -> Any?,
      propertyProjector: (View) -> Any? = { it }
    ) = ListViewResponse<Any, View>(
      views.total,
      views.chunk,
      views.items
        .sorted { propertyExtractor(it) }
        .map { propertyProjector(it) }
        .reversed(page)
    )
  }
}
