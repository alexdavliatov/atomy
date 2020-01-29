package today.selfie.item.service.repo

import ru.adavliatov.atomy.common.service.repo.ModelRepo
import ru.adavliatov.atomy.common.service.repo.WithFetchOrCreate
import today.selfie.item.domain.Item

interface ItemRepo : ModelRepo<Item>, WithFetchOrCreate<Item>