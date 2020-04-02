package com.askgps.personaltrackercore.binding;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\0000\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\016\n\000\032\030\020\000\032\0020\0012\006\020\002\032\0020\0032\006\020\004\032\0020\005H\007\032\034\020\006\032\0020\0012\006\020\007\032\0020\b2\n\020\t\032\006\022\002\b\0030\nH\007\032\030\020\013\032\0020\0012\006\020\f\032\0020\r2\006\020\016\032\0020\017H\007?\006\020"}, d2={"loadImage", "", "iv", "Landroid/widget/ImageView;", "bitmap", "Landroid/graphics/Bitmap;", "setAdapter", "rv", "Landroidx/recyclerview/widget/RecyclerView;", "adapter", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "setLastChar", "tv", "Landroid/widget/TextView;", "item", "", "personaltrackercore_release"}, k=2, mv={1, 1, 16})
public final class BindingHelperKt
{
  @BindingAdapter({"bind:imageBitmap"})
  public static final void loadImage(ImageView paramImageView, Bitmap paramBitmap)
  {
    Intrinsics.checkParameterIsNotNull(paramImageView, "iv");
    Intrinsics.checkParameterIsNotNull(paramBitmap, "bitmap");
    paramImageView.setImageBitmap(paramBitmap);
  }
  
  @BindingAdapter({"adapter"})
  public static final void setAdapter(RecyclerView paramRecyclerView, RecyclerView.Adapter<?> paramAdapter)
  {
    Intrinsics.checkParameterIsNotNull(paramRecyclerView, "rv");
    Intrinsics.checkParameterIsNotNull(paramAdapter, "adapter");
    paramRecyclerView.setAdapter(paramAdapter);
  }
  
  @BindingAdapter({"last_char"})
  public static final void setLastChar(TextView paramTextView, String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramTextView, "tv");
    Intrinsics.checkParameterIsNotNull(paramString, "item");
    paramTextView.setText((CharSequence)String.valueOf(paramString.charAt(0)));
  }
}
