package com.askgps.personaltrackercore.ui.phonebook;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import com.askgps.personaltrackercore.database.model.PhoneNumber;
import com.askgps.personaltrackercore.databinding.PhoneNumberBinding;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000:\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\020\002\n\002\b\003\n\002\020!\n\000\n\002\020\b\n\002\b\005\n\002\030\002\n\002\b\003\n\002\020 \n\000\030\0002\b\022\004\022\0020\0020\001B-\022\022\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0060\004\022\022\020\007\032\016\022\004\022\0020\005\022\004\022\0020\0060\004?\006\002\020\bJ\b\020\013\032\0020\fH\026J\030\020\r\032\0020\0062\006\020\016\032\0020\0022\006\020\017\032\0020\fH\026J\030\020\020\032\0020\0022\006\020\021\032\0020\0222\006\020\023\032\0020\fH\026J\024\020\024\032\0020\0062\f\020\025\032\b\022\004\022\0020\0050\026R\032\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0060\004X?\004?\006\002\n\000R\024\020\t\032\b\022\004\022\0020\0050\nX?\004?\006\002\n\000R\032\020\007\032\016\022\004\022\0020\005\022\004\022\0020\0060\004X?\004?\006\002\n\000?\006\027"}, d2={"Lcom/askgps/personaltrackercore/ui/phonebook/PhoneBookAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/askgps/personaltrackercore/ui/phonebook/PhoneBookViewHolder;", "clickListener", "Lkotlin/Function1;", "Lcom/askgps/personaltrackercore/database/model/PhoneNumber;", "", "longClickListener", "(Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "items", "", "getItemCount", "", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "submitList", "newList", "", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class PhoneBookAdapter
  extends RecyclerView.Adapter<PhoneBookViewHolder>
{
  private final Function1<PhoneNumber, Unit> clickListener;
  private final List<PhoneNumber> items;
  private final Function1<PhoneNumber, Unit> longClickListener;
  
  public PhoneBookAdapter(Function1<? super PhoneNumber, Unit> paramFunction11, Function1<? super PhoneNumber, Unit> paramFunction12)
  {
    this.clickListener = paramFunction11;
    this.longClickListener = paramFunction12;
    this.items = ((List)new ArrayList());
  }
  
  public int getItemCount()
  {
    return this.items.size();
  }
  
  public void onBindViewHolder(PhoneBookViewHolder paramPhoneBookViewHolder, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramPhoneBookViewHolder, "holder");
    paramPhoneBookViewHolder.bind((PhoneNumber)this.items.get(paramInt));
    paramPhoneBookViewHolder.setOnClickListener(this.clickListener);
    paramPhoneBookViewHolder.setOnLongClickListener(this.longClickListener);
  }
  
  public PhoneBookViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramViewGroup, "parent");
    paramViewGroup = PhoneNumberBinding.inflate(LayoutInflater.from(paramViewGroup.getContext()), paramViewGroup, false);
    Intrinsics.checkExpressionValueIsNotNull(paramViewGroup, "PhoneNumberBinding.infla….context), parent, false)");
    return new PhoneBookViewHolder(paramViewGroup);
  }
  
  public final void submitList(List<PhoneNumber> paramList)
  {
    Intrinsics.checkParameterIsNotNull(paramList, "newList");
    this.items.clear();
    this.items.addAll((Collection)paramList);
    notifyDataSetChanged();
  }
}
