package ru.adavliatov.atomy.common.domain.error

import ru.adavliatov.atome.common.type.error.Error
import ru.adavliatov.atome.common.type.error.code.CommonErrorCode
import ru.adavliatov.atomy.common.domain.Id
import ru.adavliatov.atomy.common.domain.error.DomainErrorCodes.EmptyId
import ru.adavliatov.atomy.common.domain.error.DomainErrorCodes.InvalidClientId
import ru.adavliatov.atomy.common.domain.error.DomainErrorCodes.InvalidStateName

@Suppress("unused")
object DomainErrors {
    object InvalidStateNameError : Error(InvalidStateName)

    class EmptyIdError(id: Id<*>) : Error(EmptyId, "Required id $id is empty")
    object InvalidConsumerError : Error(InvalidClientId)
}

//domain error codes
object DomainErrorCodes {
    object InvalidStateName : CommonErrorCode("invalid-state-name", "Invalid state name")

    object InvalidClientId : CommonErrorCode("invalid-client-id", "Invalid client id")

    object EmptyId : CommonErrorCode("empty-id", "Invalid client id")
}