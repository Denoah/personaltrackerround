package com.askgps.personaltrackercore.ui.phonebook;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.askgps.personaltrackercore.database.model.PhoneNumber;
import com.askgps.personaltrackercore.databinding.PhoneNumberBinding;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000(\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\030\0002\0020\001B\r\022\006\020\002\032\0020\003?\006\002\020\004J\016\020\005\032\0020\0062\006\020\007\032\0020\bJ\032\020\t\032\0020\0062\022\020\n\032\016\022\004\022\0020\b\022\004\022\0020\0060\013J\032\020\f\032\0020\0062\022\020\r\032\016\022\004\022\0020\b\022\004\022\0020\0060\013R\016\020\002\032\0020\003X?\004?\006\002\n\000?\006\016"}, d2={"Lcom/askgps/personaltrackercore/ui/phonebook/PhoneBookViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/askgps/personaltrackercore/databinding/PhoneNumberBinding;", "(Lcom/askgps/personaltrackercore/databinding/PhoneNumberBinding;)V", "bind", "", "phoneNumber", "Lcom/askgps/personaltrackercore/database/model/PhoneNumber;", "setOnClickListener", "clickListener", "Lkotlin/Function1;", "setOnLongClickListener", "longClickListener", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class PhoneBookViewHolder
  extends RecyclerView.ViewHolder
{
  private final PhoneNumberBinding binding;
  
  public PhoneBookViewHolder(PhoneNumberBinding paramPhoneNumberBinding)
  {
    super(paramPhoneNumberBinding.getRoot());
    this.binding = paramPhoneNumberBinding;
  }
  
  public final void bind(PhoneNumber paramPhoneNumber)
  {
    Intrinsics.checkParameterIsNotNull(paramPhoneNumber, "phoneNumber");
    this.binding.setPhoneNumber(paramPhoneNumber);
    this.binding.executePendingBindings();
  }
  
  public final void setOnClickListener(final Function1<? super PhoneNumber, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction1, "clickListener");
    this.binding.getRoot().setOnClickListener((View.OnClickListener)new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        paramAnonymousView = paramFunction1;
        PhoneNumber localPhoneNumber = PhoneBookViewHolder.access$getBinding$p(this.this$0).getPhoneNumber();
        if (localPhoneNumber == null) {
          Intrinsics.throwNpe();
        }
        Intrinsics.checkExpressionValueIsNotNull(localPhoneNumber, "binding.phoneNumber!!");
        paramAnonymousView.invoke(localPhoneNumber);
      }
    });
  }
  
  public final void setOnLongClickListener(final Function1<? super PhoneNumber, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction1, "longClickListener");
    this.binding.getRoot().setOnLongClickListener((View.OnLongClickListener)new View.OnLongClickListener()
    {
      public final boolean onLongClick(View paramAnonymousView)
      {
        Function1 localFunction1 = paramFunction1;
        paramAnonymousView = PhoneBookViewHolder.access$getBinding$p(this.this$0).getPhoneNumber();
        if (paramAnonymousView == null) {
          Intrinsics.throwNpe();
        }
        Intrinsics.checkExpressionValueIsNotNull(paramAnonymousView, "binding.phoneNumber!!");
        localFunction1.invoke(paramAnonymousView);
        return true;
      }
    });
  }
}
