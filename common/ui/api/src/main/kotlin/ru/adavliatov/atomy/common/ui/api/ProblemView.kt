//package ru.adavliatov.atomy.common.ui.api
//
//import com.fasterxml.jackson.annotation.JsonTypeInfo
//import com.fasterxml.jackson.annotation.JsonTypeInfo.As.EXTERNAL_PROPERTY
//import com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME
//import org.joda.time.Instant
//import ru.yandex.bolts.collection.Cf.toList
//import ru.yandex.bolts.collection.Cf.toSet
//import ru.yandex.contest.common.invocation.impl.CompilerGroupLimits
//import ru.yandex.contest.core.problem.*
//import ru.yandex.contest.core.runs.standalone.StandaloneRunsManager
//import ru.yandex.contest.domain.ObjectWithId
//import ru.yandex.contest.domain.error.InvalidProblemName
//import ru.yandex.contest.extension.*
//import ru.yandex.contest.extension.OptionAliases.just
//import ru.yandex.contest.master.services.impl.ProblemManagementService
//import ru.yandex.contest.master.services.impl.UserService
//import ru.yandex.contest.web.actions.api.BadRequestError
//import ru.yandex.contest.web.actions.api.InvalidProblemTypeError
//import ru.yandex.contest.web.api.admin.problem.fields.readers.ProblemFileReader
//import ru.yandex.contest.web.api.admin.problem.lifecycle.Generators
//import ru.yandex.contest.web.api.admin.problem.limits.AttachedFiles
//import ru.yandex.contest.web.api.admin.problem.limits.io.FileSettings.Companion.fromProblem
//import ru.yandex.contest.web.api.priv.admin.v1.ProblemStatementView.Companion.applyTo
//import ru.yandex.contest.web.api.priv.admin.v1.pojos.ProblemContext
//import ru.yandex.contest.web.api.priv.admin.v1.pojos.ProblemTestSetView
//import ru.yandex.contest.web.api.priv.admin.v1.pojos.StandaloneRunView
//import ru.yandex.contest.web.api.priv.admin.v1.pojos.UserView
//import java.util.*
//
//data class ProblemView(
//  override val id: String?,
//  val name: String? = "",
//  val names: Map<String, String>? = null,
//  val owner: UserView?,
//  val createdAt: Instant?,
//  val modifiedAt: Instant?,
//  val type: ProblemType?,
//  val compileLimit: ProblemLimitsView?,
//  val runtimeLimit: ProblemLimitsView?,
//  val postprocessorLimit: ProblemLimitsView?,
//  val statements: Set<ProblemStatementView>?,
//  val testSets: Set<ProblemTestSetView>?,
//  val fileSettings: FileSettingsView?,
//  val problemSets: Set<ProblemProblemSetView>?,
//  val shortName: String?,
//  val validators: Set<ValidatorView>?,
//  val solutions: Set<SolutionView>?,
//  val submissions: Set<StandaloneRunView>?,
//  val checkerSettings: CheckerSettingsView?,
//  val generatorsSettings: GeneratorsView?,
//  val generatedTests: List<GeneratedTestsView>?,
//  @JsonTypeInfo(use = NAME, include = EXTERNAL_PROPERTY, property = "type")
//  val details: ProblemDetails?
//) {
////    , WithResolvers<String>
//
//  companion object {
//    fun by(context: ProblemContext) =
//      { runsService: StandaloneRunsManager, userService: UserService, problemService: ProblemManagementService ->
//        val problem = context.problem
//        ProblemView(
//          problem.id,
//          problem.name,
//          problem.names,
//          context.owner,
//          problem.creationTime,
//          problem.modifyTime,
//          problem.problemType,
//          ProblemLimitsView(
//            problem.compilationLimits,
//            problem.customCompilationGroupLimits,
//            problem.compileIncludeFiles
//          ),
//          ProblemLimitsView(problem.solutionLimits, problem.customSolutionGroupLimits, problem.runIncludeFiles),
//          ProblemLimitsView(files = AttachedFiles().with(problem.postProcessFiles)),
//          context.projection.resolve("statements") {
//            problem.statements.mapToSet { ProblemStatementView.by(it) }
//          },
//          problem.testSets.viewsSet(),
//          FileSettingsView(fromProblem(problem)),
//          context.projection.resolve("problemSets") {
//            context.problemSets.mapToSet { ProblemProblemSetView(it) }
//          },
//          problem.shortName.orNull,
//          problem.validators.mapToSet { ValidatorView(it) },
//          problem.solutions.mapToSet { SolutionView(it) },
//          context.projection.resolve("runs") {
//            runsService.findProblemRuns(problem.id)?.data
//              ?.map { StandaloneRunView.by(it)(runsService, userService) }
//              ?.toSet()
//          },
//          problem.checkerSettingsView(),
//          context.projection.resolve("generators") { GeneratorsView.by(problem.generatorsWithScript) },
//          context.projection.resolve("generatedTests") {
//            problem.id.let { problemService.makeListOfGeneratedTests(it).map(::GeneratedTestsView) }
//          },
//          context.projection.resolve("details") { context.details }
//        )
//      }
//  }
//
////    fun prepareResolvers(context: ProblemContext, runsService: StandaloneRunsManager, userService: UserService) : Map<String , Function<Any>> {
////      return mapOf(
////          "runs" to { runsService.findProblemRuns(context.problem.id)?.data
////              ?.map { StandaloneRunView.by(it)(runsService, userService) }
////              .toSetOrEmpty() }
////      )
////    }
////  }
////
////    private fun resolveRuns(context: ProblemContext, runsService: StandaloneRunsManager, userService: UserService) =
////        (resolvers.getValue("runs") as ProjectionResolver<ProblemContext, Function2<StandaloneRunsManager, UserService, Set<StandaloneRunView>>>)(context)(runsService, userService)
////
////    val resolvers: Map<String, ProjectionResolver<*, *>> = mapOf(
////        "runs" to object : ProjectionResolver<ProblemContext, Function2<StandaloneRunsManager, UserService, Set<StandaloneRunView>>> {
////          override fun invoke(context: ProblemContext): (StandaloneRunsManager, UserService) -> Set<StandaloneRunView> = { runsService, userService ->
////            runsService.findProblemRuns(context.problem.id)?.data
////                ?.map { StandaloneRunView.by(it)(runsService, userService) }
////                .toSetOrEmpty()
////          }
////        }
////
////        val runsResolver =object : ProjectionResolver<ProblemContext, Function2<StandaloneRunsManager, UserService, Set<StandaloneRunView>>> {
////      override fun invoke(context: ProblemContext): (StandaloneRunsManager, UserService) -> Set<StandaloneRunView> = { runsService, userService ->
////        runsService.findProblemRuns(context.problem.id)?.data
////            ?.map { StandaloneRunView.by(it)(runsService, userService) }
////            .toSetOrEmpty()
////      }
////      )
////    }
////
////    override val resolvers: Map<String, ProjectionResolver<*>> = mapOf()
//
//  fun validate() {
//    name?.run {
//      validate(isNotBlank()) { BadRequestError(InvalidProblemName) }
//    }
//    runtimeLimit?.validate()
//    validators?.forEach { it.validate() }
//  }
//
//  fun validateForCreation() {
//    validateNot(name.isNullOrBlank()) { BadRequestError(InvalidProblemName) }
//  }
//
//  fun applyTo(context: ProblemContext): (ProblemManagementService, ProblemFileReader, TexStatementManager) -> ProblemContext =
//    { service, storage, texStatementService ->
//      val problem = context.problem
//      val newProblem = problem
//        .let { service.modifyProblemType(it) }
//
//      name?.let { newProblem.name = it }
//      names?.let { newProblem.mergeNames(it) }
//      compileLimit?.run {
//        applyTo(newProblem.compilationLimits)?.let { newProblem.compilationLimits = it }
//        files?.run { newProblem.withCompileIncludeFiles(files) }
//        custom?.run { newProblem.setCustomCompilationCompilerLimits(map { it.toCompilerGroupLimits() }) }
//      }
//      runtimeLimit?.run {
//        applyTo(newProblem.solutionLimits)?.let { newProblem.solutionLimits = it }
//        files?.run { newProblem.withRunIncludeFiles(files) }
//        custom?.run { newProblem.setCustomCompilerLimits(map { it.toCompilerGroupLimits() }) }
//      }
//      postprocessorLimit?.run {
//        files?.run { newProblem.withPostProcessorFiles(files) }
//      }
//      fileSettings?.run { applyTo(newProblem) }
//      statements?.applyTo(newProblem)?.invoke(texStatementService)
//      validators?.run { newProblem.validators = toSet(map { it.toValidator() }) }
//      testSets?.forEach { it.applyTo(newProblem) }
//      shortName?.let { newProblem.shortName = just(it) }
//      solutions?.also { newProblem.solutions.clear() }?.forEach { it.applyTo(newProblem) }
//      checkerSettings?.also { if (newProblem is ProblemWithChecker) it.applyTo(newProblem)(service) }
//      generatorsSettings?.run { applyTo(newProblem) }
//
//      when (details) {
//        is TestDetails -> {
//          val testProblem = problem.validateType<TestProblem> {
//            InvalidProblemTypeError(toString(), TestProblem::class)
//          }
//          details.applyTo(testProblem)(storage)
//        }
//        is TextAnswerDetails -> details.applyTo(problem)(storage)
//        is InteractiveDetails -> {
//          val problemWithChecker = problem.validateType<InteractiveProblem> {
//            InvalidProblemTypeError(toString(), InteractiveProblem::class)
//          }
//          details.applyTo(problemWithChecker)(service)
//        }
//      }
//      context.copy(problem = newProblem)
//    }
//
//  private fun ProblemManagementService.modifyProblemType(problem: MProblem) =
//    type?.let { changeProblemType(problem, it) } ?: problem
//
//  class ProblemProblemSetView(val id: String, val name: String?) {
//    constructor(problemSet: MProblemSet) : this(problemSet.id, problemSet.name)
//  }
//
//  data class CompilerGroupLimitView(val compilers: Set<String>, val limit: ProblemLimitView) {
//    constructor(compilerGroupLimits: CompilerGroupLimits) : this(
//      compilerGroupLimits.compilerIds,
//      ProblemLimitView(compilerGroupLimits.limits)
//    )
//
//    fun validate() = limit.validate()
//
//    fun toCompilerGroupLimits() = CompilerGroupLimits(toList(compilers), limit.toInvocationLimits())
//  }
//}
//
//private val problemTypeToDetails = mapOf(
//  TestProblem::class to TestDetails::class,
//  TextAnswerProblem::class to TextAnswerDetails::class,
//  ProblemWithChecker::class to EmptyProblemDetails::class,
//  InteractiveProblem::class to InteractiveDetails::class
//)
//
//val MProblem.details
//  get() = { reader: ProblemFileReader ->
//    problemTypeToDetails[this::class]
//      ?.companionObjectInstance
//      ?.takeAs<ProblemDetailsProvider<MProblem, ProblemDetails>>()
//      ?.let { it.provide(this)(reader) }
//  }
//
//val MProblem.checkerSettingsView
//  get() = { if (this is ProblemWithChecker) checkerSettings?.toView() else null }
//
//fun List<TestSet>.viewsSet() = mapIndexedToSet { i, testSet -> ProblemTestSetView(i, testSet) }
//  .toSortedSet(Comparator { t1, t2 ->
//    when {
//      t1.sample ?: false -> -1
//      t2.sample ?: false -> 1
//      else -> (t1.id ?: 0) - (t2.id ?: 0)
//    }
//  })
//
//val MProblem.generatorsWithScript
//  get() = Generators.fromProblem(this)
