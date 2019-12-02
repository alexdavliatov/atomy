package ru.adavliatov.atomy.example.transfer.type

typealias Currency = String
typealias Amount = Double
data class Money(val currency: Currency,
                 val amount: Amount
)
