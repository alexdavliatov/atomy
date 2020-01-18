package today.selfi.profile.service.repo

import org.jooq.SQLDialect.POSTGRES_10
import ru.adavliatov.atomy.common.domain.*
import ru.adavliatov.atomy.common.type.name.*
import ru.adavliatov.atomy.common.type.ref.*
import ru.adavliatov.atomy.common.type.ref.impl.json.*
import ru.adavliatov.atomy.example.selfie.profile.service.repo.generated.profile.tables.daos.*
import ru.adavliatov.atomy.example.selfie.profile.service.repo.generated.profile.tables.pojos.*
import ru.adavliatov.atomy.example.selfie.profile.service.repo.generated.profile.tables.records.*
import ru.adavliatov.atomy.toolkit.jooq.ext.JooqExtensions.toJooqConfig
import ru.adavliatov.atomy.toolkit.jooq.service.*
import ru.adavliatov.common.type.json.impl.*
import today.selfie.profile.domain.Profile
import javax.sql.DataSource

open class ProfileJooqRepo(ds: DataSource) : ModelJooqDaoAdapter<Profile, ProfilesRecord, Profiles>(ds),
  ProfileRepo {
  override val dao: ProfilesDao = ProfilesDao(ds.toJooqConfig().set(POSTGRES_10))

  override val entityClass: Class<Profile> = Profile::class.java
  override val pojoClass: Class<Profiles> = Profiles::class.java

  override fun Profile.toPojo(): Profiles = Profiles(
    id.checkedId,
    id.uid,
    id.ref.consumer.let { it as JsonConsumerId<*> }.let { it.value as JacksonJson },
    id.ref.ref?.let { it as JsonConsumerId<*> }?.let { it.value as JacksonJson },
    state.name,
    createdAt,
    modifiedAt,
    name.value
  )

  override fun Profiles.toModel(): Profile = Profile(
    Id(
      id,
      uid,
      Ref(
        JsonConsumerId(consumer),
        JsonConsumerRef(ref)
      ),
      Profile::class
    ),
    State(state),
    createdAt,
    modifiedAt,
    NameValue(name)
  )
}