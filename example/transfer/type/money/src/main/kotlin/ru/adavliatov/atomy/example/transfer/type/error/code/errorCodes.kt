package ru.adavliatov.atomy.example.transfer.type.error.code

import ru.adavliatov.atomy.common.domain.error.code.CommonErrorCode

object InvalidCurrency : CommonErrorCode("invalid-currency", "Invalid currency")
object InvalidAmount : CommonErrorCode("invalid-amount", "Invalid amount")
