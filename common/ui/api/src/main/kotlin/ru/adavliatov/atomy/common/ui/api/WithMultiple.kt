package ru.adavliatov.atomy.common.ui.api

import ru.adavliatov.atomy.common.type.chunk.Chunk
import ru.adavliatov.atomy.common.type.page.Page
import ru.adavliatov.atomy.common.ui.api.domain.Auth
import ru.adavliatov.atomy.common.ui.api.domain.Context
import ru.adavliatov.atomy.common.ui.api.domain.Response

interface WithMultiple<Id, Model, View : Any> : WithViewableModel<Model, View>,
  WithPropertyExtractor<View>,
  WithPropertyProjector<View> {
  fun multiple(auth: Auth, ids: List<Id>, page: Page): Chunk<Model>

  fun multipleRoute(
    context: Context,
    page: Page,
    ids: List<Id>?
  ): Response {
    val (_, response, auth) = context

    val views = ListViewResponse(
      multiple(auth, ids ?: listOf(), page),
      page,
      toView(),
      { propertyExtractor.extractProperty(it, page.sortBy) },
      { propertyProjector.project(it, page.properties) }
    )

    return response
      .withBody(views)
  }
}