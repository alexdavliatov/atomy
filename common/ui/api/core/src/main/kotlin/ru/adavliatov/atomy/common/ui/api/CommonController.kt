package ru.adavliatov.atomy.common.ui.api

interface CommonController<Id, Model, View> :
  WithNew<Id, View>,
  WithModify<Id, View>,
  WithRemove<Id>,
  WithOne<Id, Model, View>,
  WithPaginated<Model, View>,
  WithMultiple<Id, Model, View>,
  WithView<View>