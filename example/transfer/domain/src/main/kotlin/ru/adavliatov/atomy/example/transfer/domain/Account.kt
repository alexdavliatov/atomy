package ru.adavliatov.atomy.example.transfer.domain

import ru.adavliatov.atomy.common.domain.*
import ru.adavliatov.atomy.example.transfer.type.Money
import java.time.Instant

typealias Name = String

data class Account(
    override val id: Id<Account>,
    override val state: State,
    override val createdAt: Instant,
    override val modifiedAt: Instant,

    val name: Name,
    val money: Money
) : WithModel<Account> {
    override fun withId(id: Id<Account>): Account = copy(id = id)
    override fun withState(state: State): Account = copy(state = state)
    override fun modified(ts: Instant): Account = copy(modifiedAt = ts)
}
