package androidx.core.widget;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(bv={1, 0, 3}, d1={"\0008\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\020\r\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\003\n\002\020\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\005\032?\002\020\000\032\0020\001*\0020\0022d\b\006\020\003\032^\022\025\022\023\030\0010\005?\006\f\b\006\022\b\b\007\022\004\b\b(\b\022\023\022\0210\t?\006\f\b\006\022\b\b\007\022\004\b\b(\n\022\023\022\0210\t?\006\f\b\006\022\b\b\007\022\004\b\b(\013\022\023\022\0210\t?\006\f\b\006\022\b\b\007\022\004\b\b(\f\022\004\022\0020\r0\0042d\b\006\020\016\032^\022\025\022\023\030\0010\005?\006\f\b\006\022\b\b\007\022\004\b\b(\b\022\023\022\0210\t?\006\f\b\006\022\b\b\007\022\004\b\b(\n\022\023\022\0210\t?\006\f\b\006\022\b\b\007\022\004\b\b(\013\022\023\022\0210\t?\006\f\b\006\022\b\b\007\022\004\b\b(\f\022\004\022\0020\r0\0042%\b\006\020\017\032\037\022\025\022\023\030\0010\021?\006\f\b\006\022\b\b\007\022\004\b\b(\b\022\004\022\0020\r0\020H?\b\0324\020\022\032\0020\001*\0020\0022%\b\004\020\023\032\037\022\025\022\023\030\0010\021?\006\f\b\006\022\b\b\007\022\004\b\b(\b\022\004\022\0020\r0\020H?\b\032s\020\024\032\0020\001*\0020\0022d\b\004\020\023\032^\022\025\022\023\030\0010\005?\006\f\b\006\022\b\b\007\022\004\b\b(\b\022\023\022\0210\t?\006\f\b\006\022\b\b\007\022\004\b\b(\n\022\023\022\0210\t?\006\f\b\006\022\b\b\007\022\004\b\b(\013\022\023\022\0210\t?\006\f\b\006\022\b\b\007\022\004\b\b(\f\022\004\022\0020\r0\004H?\b\032s\020\025\032\0020\001*\0020\0022d\b\004\020\023\032^\022\025\022\023\030\0010\005?\006\f\b\006\022\b\b\007\022\004\b\b(\b\022\023\022\0210\t?\006\f\b\006\022\b\b\007\022\004\b\b(\n\022\023\022\0210\t?\006\f\b\006\022\b\b\007\022\004\b\b(\013\022\023\022\0210\t?\006\f\b\006\022\b\b\007\022\004\b\b(\f\022\004\022\0020\r0\004H?\b?\006\026"}, d2={"addTextChangedListener", "Landroid/text/TextWatcher;", "Landroid/widget/TextView;", "beforeTextChanged", "Lkotlin/Function4;", "", "Lkotlin/ParameterName;", "name", "text", "", "start", "count", "after", "", "onTextChanged", "afterTextChanged", "Lkotlin/Function1;", "Landroid/text/Editable;", "doAfterTextChanged", "action", "doBeforeTextChanged", "doOnTextChanged", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class TextViewKt
{
  public static final TextWatcher addTextChangedListener(TextView paramTextView, final Function4<? super CharSequence, ? super Integer, ? super Integer, ? super Integer, Unit> paramFunction41, final Function4<? super CharSequence, ? super Integer, ? super Integer, ? super Integer, Unit> paramFunction42, Function1<? super Editable, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramTextView, "$this$addTextChangedListener");
    Intrinsics.checkParameterIsNotNull(paramFunction41, "beforeTextChanged");
    Intrinsics.checkParameterIsNotNull(paramFunction42, "onTextChanged");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "afterTextChanged");
    paramFunction41 = (TextWatcher)new TextWatcher()
    {
      public void afterTextChanged(Editable paramAnonymousEditable)
      {
        this.$afterTextChanged.invoke(paramAnonymousEditable);
      }
      
      public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        paramFunction41.invoke(paramAnonymousCharSequence, Integer.valueOf(paramAnonymousInt1), Integer.valueOf(paramAnonymousInt2), Integer.valueOf(paramAnonymousInt3));
      }
      
      public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        paramFunction42.invoke(paramAnonymousCharSequence, Integer.valueOf(paramAnonymousInt1), Integer.valueOf(paramAnonymousInt2), Integer.valueOf(paramAnonymousInt3));
      }
    };
    paramTextView.addTextChangedListener(paramFunction41);
    return paramFunction41;
  }
  
  public static final TextWatcher doAfterTextChanged(TextView paramTextView, Function1<? super Editable, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramTextView, "$this$doAfterTextChanged");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "action");
    paramFunction1 = (TextWatcher)new TextWatcher()
    {
      public void afterTextChanged(Editable paramAnonymousEditable)
      {
        this.$afterTextChanged.invoke(paramAnonymousEditable);
      }
      
      public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
      
      public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
    };
    paramTextView.addTextChangedListener(paramFunction1);
    return paramFunction1;
  }
  
  public static final TextWatcher doBeforeTextChanged(TextView paramTextView, Function4<? super CharSequence, ? super Integer, ? super Integer, ? super Integer, Unit> paramFunction4)
  {
    Intrinsics.checkParameterIsNotNull(paramTextView, "$this$doBeforeTextChanged");
    Intrinsics.checkParameterIsNotNull(paramFunction4, "action");
    paramFunction4 = (TextWatcher)new TextWatcher()
    {
      public void afterTextChanged(Editable paramAnonymousEditable) {}
      
      public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        this.$beforeTextChanged.invoke(paramAnonymousCharSequence, Integer.valueOf(paramAnonymousInt1), Integer.valueOf(paramAnonymousInt2), Integer.valueOf(paramAnonymousInt3));
      }
      
      public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
    };
    paramTextView.addTextChangedListener(paramFunction4);
    return paramFunction4;
  }
  
  public static final TextWatcher doOnTextChanged(TextView paramTextView, Function4<? super CharSequence, ? super Integer, ? super Integer, ? super Integer, Unit> paramFunction4)
  {
    Intrinsics.checkParameterIsNotNull(paramTextView, "$this$doOnTextChanged");
    Intrinsics.checkParameterIsNotNull(paramFunction4, "action");
    paramFunction4 = (TextWatcher)new TextWatcher()
    {
      public void afterTextChanged(Editable paramAnonymousEditable) {}
      
      public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
      
      public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        this.$onTextChanged.invoke(paramAnonymousCharSequence, Integer.valueOf(paramAnonymousInt1), Integer.valueOf(paramAnonymousInt2), Integer.valueOf(paramAnonymousInt3));
      }
    };
    paramTextView.addTextChangedListener(paramFunction4);
    return paramFunction4;
  }
}
