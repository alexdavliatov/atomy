package today.selfi.item.service.repo

import ru.adavliatov.atomy.common.service.repo.*
import today.selfi.item.domain.Item

interface ItemRepo : ModelRepo<Item>, WithFetchOrCreate<Item>