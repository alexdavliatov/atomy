package ru.adavliatov.atomy.common.ui.api

import ru.adavliatov.atomy.common.type.chunk.ChunkedData
import ru.adavliatov.atomy.common.type.page.Page
import ru.adavliatov.atomy.common.ui.api.domain.Context
import ru.adavliatov.atomy.common.ui.api.domain.Response
import ru.adavliatov.atomy.common.ui.api.domain.WithAuth

interface WithMultiple<Id, Model, View> : WithViewableModel<Model, View>,
  WithPropertyExtractor<View>,
  WithPropertyProjector<View> {
  fun multiple(auth: WithAuth, ids: List<Id>, page: Page): ChunkedData<Model>
  fun multipleViews(auth: WithAuth, ids: List<Id>, page: Page): ChunkedData<View>

  fun multipleRoute(
    context: Context,
    page: Page,
    ids: List<Id>?
  ): Response {
    val (_, response, auth) = context

    val views = ListViewResponse.by<View>(
      multipleViews(auth, ids ?: listOf(), page),
      page,
//      toView(),
      { propertyExtractor.extractProperty(it, page.sortBy) },
      { propertyProjector.project(it, page.props) }
    )

    return response
      .withBody(views)
  }
}