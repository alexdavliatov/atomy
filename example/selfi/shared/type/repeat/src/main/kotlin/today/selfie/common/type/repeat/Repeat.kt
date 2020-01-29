package today.selfie.common.type.repeat

import ru.adavliatov.atomy.common.type.name.NameValue
import ru.adavliatov.atomy.common.type.name.ValueHolder

interface Content

data class RepeatType(val name: NameValue) : ValueHolder<NameValue>(name)
data class Repeat(val type: RepeatType, val content: Content)
