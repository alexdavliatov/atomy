package ru.adavliatov.atomy.common.ui.api

import ru.adavliatov.atomy.common.type.chunk.ChunkedData
import ru.adavliatov.atomy.common.type.page.Page
import ru.adavliatov.atomy.common.ui.api.domain.Auth
import ru.adavliatov.atomy.common.ui.api.domain.Context
import ru.adavliatov.atomy.common.ui.api.domain.Response

interface WithPaginated<Model, View> :
  WithViewableModel<Model, View>,
  WithPropertyExtractor<View>,
  WithPropertyProjector<View> {
  fun paginated(auth: Auth, page: Page): ChunkedData<Model>

  fun paginatedRoute(
    context: Context,
    page: Page
  ): Response {
    val (_, response, auth) = context

    val views = ListViewResponse(
      paginated(auth, page),
      page,
      toView(),
      { propertyExtractor.extractProperty(it, page.sortBy) },
      { propertyProjector.project(it, page.props) }
    )

    return response.withBody(views)
  }
}