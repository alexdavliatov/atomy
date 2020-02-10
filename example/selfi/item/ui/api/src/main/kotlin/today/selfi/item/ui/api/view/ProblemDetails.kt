package today.selfi.item.ui.api.view

//@JsonSubTypes(value = [
//  Type(TextAnswerDetails::class, name = "TEXT_ANSWER_PROBLEM"),
//  Type(EmptyProblemDetails::class, name = "PROBLEM_WITH_CHECKER"),
//  Type(InteractiveDetails::class, name = "INTERACTIVE_PROBLEM"),
//  Type(TestDetails::class, name = "TEST_PROBLEM")
//])
//interface ProblemDetails
//
//object EmptyProblemDetails : ProblemDetails
//
//data class TextAnswerDetails(
//    val correctAnswer: String?,
//    val answerType: FieldType?,
//    val maxLength: Long?,
//    val pattern: String?
//) : ProblemDetails {
//
//  fun applyTo(problem: MProblem): (ProblemFileReader) -> Unit = { reader ->
//    reader.writeDefaultAnswer(problem, correctAnswer)
//    problem.setFieldType(answerType)
//    problem.validation = mapOf("submit" to pattern, "maxLength" to maxLength)
//        .filterValues { it != null }
//        .mapValues { it.value.toString() }
//        .let { toHashMap(it) }
//  }
//
//  companion object : ProblemDetailsProvider<TextAnswerProblem, TextAnswerDetails> {
//    override fun provide(problem: TextAnswerProblem): (ProblemFileReader) -> TextAnswerDetails? = { reader ->
//      val answer = reader.readDefaultAnswer(problem).orNull()
//      val fieldType = problem.fieldType.orNull
//      val maxLength = problem.validation.getO("maxLength").orNull?.toLongOrNull()
//      val pattern = problem.validation.getO("submit").orNull
//      TextAnswerDetails(answer, fieldType, maxLength, pattern)
//    }
//  }
//}
//
//data class InteractiveDetails(
//    val interactorSettings: InteractorSettingsView?
//) : ProblemDetails {
//
//  fun applyTo(problem: InteractiveProblem): (ProblemManagementService) -> Unit = { service ->
//    val currentSettings = problem.interactorSettings
//    if (currentSettings == null) {
//      problem.interactorSettings = interactorSettings?.toInteractorSettings()
//    } else {
//      interactorSettings?.also { it.applyTo(currentSettings) }
//    }
//  }
//
//  companion object : ProblemDetailsProvider<InteractiveProblem, InteractiveDetails> {
//    override fun provide(problem: InteractiveProblem): (ProblemFileReader) -> InteractiveDetails? = {
//      InteractiveDetails(problem.interactorSettings.toView())
//    }
//  }
//}
