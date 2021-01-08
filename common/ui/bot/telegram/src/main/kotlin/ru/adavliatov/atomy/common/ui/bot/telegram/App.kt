package ru.adavliatov.atomy.common.ui.bot.telegram

import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import ru.adavliatov.atomy.common.ui.bot.telegram.App.TELEGRAM_TOKEN

object App {
  const val TELEGRAM_TOKEN = "918782350:AAGwoWdESfFf5pXpdJ5ySlBHvYN7uklHcYU"
}

class TelegramBot(val token: String, options: DefaultBotOptions) : TelegramLongPollingBot() {
  override fun getBotUsername() = "Selfi.Today Bot"

  override fun getBotToken() = token

  override fun onUpdateReceived(update: Update?) {
    println("***Telegram: messageId = ${update?.message?.messageId}")
    println("***Telegram: chatId = ${update?.message?.chatId}")
    println(update)
    update?.message?.chatId?.let { execute(SendMessage(it, update.message?.text)) }
  }
}

fun main() {
  ApiContextInitializer.init()
  val botOptions = DefaultBotOptions()
  val selfiBot = TelegramBot(TELEGRAM_TOKEN, botOptions)

  val telegramBotsApi = TelegramBotsApi()
  try {
    println("Bot is started")
    telegramBotsApi.registerBot(selfiBot)
    println("Bot is registered")
  } catch (e: TelegramApiException) {
    e.printStackTrace()
  }

}
