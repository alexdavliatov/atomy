package ru.adavliatov.atomy.common.ui.api

import ru.yandex.commune.page.PageWithData
import ru.yandex.contest.extension.reversed

data class ListViewResponse<Model, View>(val count: Int, val items: List<Any?>) {
  constructor(
    models: PageWithData<Model>,
    chunk: Chunk,
    modelToView: (Model) -> View,
    propertyExtractor: (View) -> Any?,
    propertyProjector: (View) -> Any? = { it }
  ) : this(
    models.page.entitiesCount,
    models.data.map { modelToView(it) }
      .sortedBy { propertyExtractor(it) }
      .map { propertyProjector(it) }
      .reversed(chunk)
  )

  constructor(views: PageWithData<View>) : this(views.page.entitiesCount, views.data)
}
