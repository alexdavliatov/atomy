package ru.adavliatov.atomy.common.domain.error

import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.State
import ru.adavliatov.atomy.common.domain.error.DomainErrorCodes.EmptyId
import ru.adavliatov.atomy.common.domain.error.DomainErrorCodes.IllegalStateProvided
import ru.adavliatov.atomy.common.domain.error.DomainErrorCodes.InvalidClientId
import ru.adavliatov.atomy.common.domain.error.DomainErrorCodes.InvalidStateName
import ru.adavliatov.atomy.common.type.error.HttpWrapperErrors.InvalidArgumentError
import ru.adavliatov.atomy.common.type.error.code.CommonErrorCode

@Suppress("unused")
object DomainErrors {
  object InvalidStateNameError : InvalidArgumentError(InvalidStateName)
  class IllegalStateProvidedError(state: State) : InvalidArgumentError(IllegalStateProvided, "$state is illegal state")

  class EmptyIdError(id: Id<*>) : InvalidArgumentError(EmptyId, "Required id $id is empty")
  class EmptyUidError(id: Id<*>) : InvalidArgumentError(EmptyId, "Required uid $id is empty")
  object InvalidConsumerError : InvalidArgumentError(InvalidClientId)
}

//domain error codes
object DomainErrorCodes {
  object InvalidStateName : CommonErrorCode("invalid-state-name", "Invalid state name")
  object IllegalStateProvided : CommonErrorCode("invalid-state-provided", "Invalid state provided")

  object InvalidClientId : CommonErrorCode("invalid-client-id", "Invalid client id")

  object EmptyId : CommonErrorCode("empty-id", "Invalid client id")
}