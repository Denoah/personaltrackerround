package com.askgps.personaltrackercore;

import android.content.Context;
import androidx.room.Room;
import androidx.room.RoomDatabase.Builder;
import androidx.room.migration.Migration;
import com.askgps.personaltrackercore.database.DatabaseHelper;
import com.askgps.personaltrackercore.database.DatabaseHelper.Companion;
import com.askgps.personaltrackercore.extension.ContextExtensionKt;
import com.askgps.personaltrackercore.location.GoogleApiHelper;
import com.askgps.personaltrackercore.receiver.PowerConnectionCompanion;
import com.askgps.personaltrackercore.repository.Repository;
import com.askgps.personaltrackercore.utils.AvatarUtils;
import com.askgps.personaltrackercore.utils.NotificationHandler;
import com.askgps.personaltrackercore.utils.RemovalFromHandUtils;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import org.koin.android.ext.koin.ModuleExtKt;
import org.koin.core.definition.BeanDefinition;
import org.koin.core.definition.Definitions;
import org.koin.core.definition.Kind;
import org.koin.core.definition.Options;
import org.koin.core.module.Module;
import org.koin.core.parameter.DefinitionParameters;
import org.koin.core.qualifier.Qualifier;
import org.koin.core.qualifier.QualifierKt;
import org.koin.core.scope.Scope;
import org.koin.core.scope.ScopeDefinition;
import org.koin.dsl.ModuleKt;

@Metadata(bv={1, 0, 3}, d1={"\000\b\n\000\n\002\030\002\n\000\032\006\020\000\032\0020\001?\006\002"}, d2={"koinModule", "Lorg/koin/core/module/Module;", "personaltrackercore_release"}, k=2, mv={1, 1, 16})
public final class KoinModuleKt
{
  public static final Module koinModule()
  {
    return ModuleKt.module$default(false, false, (Function1)koinModule.1.INSTANCE, 3, null);
  }
}
