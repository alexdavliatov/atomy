package ru.adavliatov.atomy.example.transfer.service.repo

import ru.adavliatov.atomy.common.service.repo.EntityRepo
import ru.adavliatov.atomy.example.transfer.domain.Transaction

interface TransactionRepo : EntityRepo<Transaction>
