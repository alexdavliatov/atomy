package ru.adavliatov.atomy.example.transfer.type.error.code

import ru.adavliatov.atome.common.type.error.code.*

object MoneyErrorCodes {
  object InvalidCurrency : CommonErrorCode("invalid-currency", "Invalid currency")
  object InvalidAmount : CommonErrorCode("invalid-amount", "Invalid amount")
  object DifferentCurrencies : CommonErrorCode("different-currencies", "Different currencies can not used together")
}
