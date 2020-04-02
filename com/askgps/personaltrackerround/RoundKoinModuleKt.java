package com.askgps.personaltrackerround;

import androidx.fragment.app.Fragment;
import com.askgps.personaltrackercore.BaseMainActivity;
import com.askgps.personaltrackercore.BaseMainActivity.Companion;
import com.askgps.personaltrackercore.config.CustomerCategory;
import com.askgps.personaltrackerround.ui.main.MainFragmentPhone;
import com.askgps.personaltrackerround.ui.main.MainFragmentPhone.Companion;
import com.askgps.personaltrackerround.ui.main.MainFragmentRound;
import com.askgps.personaltrackerround.ui.main.MainFragmentRound.Companion;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
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

@Metadata(bv={1, 0, 3}, d1={"\000\b\n\000\n\002\030\002\n\000\032\006\020\000\032\0020\001?\006\002"}, d2={"roundKoinModule", "Lorg/koin/core/module/Module;", "round_for_patient_release"}, k=2, mv={1, 1, 16})
public final class RoundKoinModuleKt
{
  public static final Module roundKoinModule()
  {
    return ModuleKt.module$default(false, false, (Function1)roundKoinModule.1.INSTANCE, 3, null);
  }
}
