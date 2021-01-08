package ru.adavliatov.atomy.common.ui.api

import ru.adavliatov.atomy.common.type.chunk.ChunkedData
import ru.adavliatov.atomy.common.type.page.Page
import ru.adavliatov.atomy.common.ui.api.domain.Context
import ru.adavliatov.atomy.common.ui.api.domain.Response
import ru.adavliatov.atomy.common.ui.api.domain.WithAuth

interface WithChunked<Model, View> :
  WithViewableModel<Model, View>,
  WithPropertyExtractor<View>,
  WithPropertyProjector<View> {
  fun chunked(auth: WithAuth, page: Page): ChunkedData<Model>
  fun chunkedViews(auth: WithAuth, page: Page): ChunkedData<View>

  fun chunkedRoute(
    context: Context,
    page: Page
  ): Response {
    val (_, response, auth) = context

    val views = ListViewResponse.by(
      chunkedViews(auth, page),
      page,
//      toView(),
      { propertyExtractor.extractProperty(it, page.sortBy) },
      { propertyProjector.project(it, page.props) }
    )

    return response.withBody(views)
  }
}