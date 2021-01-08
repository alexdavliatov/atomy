package ru.adavliatov.atomy.example.transfer.service.repo

import ru.adavliatov.atomy.common.service.domain.error.RepoErrors.NotFoundRepoError
import ru.adavliatov.atomy.common.service.repo.ModelRepo
import ru.adavliatov.atomy.common.service.repo.WithFetchOrCreate
import ru.adavliatov.atomy.example.transfer.domain.Account
import ru.adavliatov.atomy.example.transfer.domain.Name

interface AccountRepo : ModelRepo<Account>, WithFetchOrCreate<Account> {
  fun findByName(name: Name): Account?
  @Suppress("unused")
  fun findByNameChecked(name: Name) = findByName(name) ?: NotFoundRepoError("Account with name=[$name] not found.")
}
