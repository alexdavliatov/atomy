package ru.adavliatov.atomy.toolkit.fuel.service

import ru.adavliatov.atomy.common.service.repo.WithHolder

interface WithHolderToHeader<Holder : WithHolder> {
  fun Holder.toHeader(): String
}