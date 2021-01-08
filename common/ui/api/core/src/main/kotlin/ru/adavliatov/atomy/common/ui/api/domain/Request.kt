package ru.adavliatov.atomy.common.ui.api.domain

interface Request {
  fun header(name: String): String?
}