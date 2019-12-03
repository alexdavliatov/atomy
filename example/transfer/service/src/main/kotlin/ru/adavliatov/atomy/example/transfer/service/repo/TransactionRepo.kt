package ru.adavliatov.atomy.example.transfer.service.repo

import ru.adavliatov.atomy.common.service.domain.error.RepoErrors.NotFoundRepoError
import ru.adavliatov.atomy.common.service.repo.*
import ru.adavliatov.atomy.example.transfer.domain.*

interface TransactionRepo : EntityRepo<Transaction>
