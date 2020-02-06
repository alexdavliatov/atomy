package ru.adavliatov.atomy.common.ui.api

import ru.adavliatov.atomy.common.type.chunk.Chunk
import ru.adavliatov.atomy.common.type.page.Page
import ru.adavliatov.atomy.common.ui.api.domain.Auth
import ru.adavliatov.atomy.common.ui.api.domain.Context
import ru.adavliatov.atomy.common.ui.api.domain.Response

interface WithPaginated<Model, View : Any> :
  WithViewableModel<Model, View>,
  WithPropertyExtractor<View>,
  WithPropertyProjector<View> {
  fun all(auth: Auth, page: Page): Chunk<Model>

  fun allRoute(
    context: Context,
    page: Page
  ): Response {
    val (_, response, auth) = context

    val views = ListViewResponse(
      all(auth, page),
      page,
      toView(),
      { propertyExtractor.extractProperty(it, page.sortBy) },
      { propertyProjector.project(it, page.properties) }
    )

    return response.withBody(views)
  }
}