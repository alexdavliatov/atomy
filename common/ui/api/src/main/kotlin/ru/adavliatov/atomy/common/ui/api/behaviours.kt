package ru.adavliatov.atomy.common.ui.api

import kotlin.reflect.KClass

typealias FieldName = String

interface WithViewableModel<Model, View> {
  fun Model.toView(): View
  fun toView(): (Model) -> View = { it.toView() }
}

interface WithPropertyExtractor<View> {
  val propertyExtractor: PropertyExtractor<View>
}

interface WithPropertyProjector<View> {
  val propertyProjector: PropertyProjector<View>
}

abstract class WithPropertyHandler<View : Any>(klass: KClass<View>) : WithPropertyExtractor<View>,
  WithPropertyProjector<View> {
  override val propertyExtractor: PropertyExtractor<View> = PropertyExtractor(klass)
  override val propertyProjector: PropertyProjector<View> = PropertyProjector(klass)
}

open class IdWrapper<Id>(val id: Id)
