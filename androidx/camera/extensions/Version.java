package androidx.camera.extensions;

import android.text.TextUtils;
import java.math.BigInteger;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class Version
  implements Comparable<Version>
{
  static final Version VERSION_1_0 = create(1, 0, 0, "");
  static final Version VERSION_1_1 = create(1, 1, 0, "");
  private static final Pattern VERSION_STRING_PATTERN = Pattern.compile("(\\d+)(?:\\.(\\d+))(?:\\.(\\d+))(?:\\-(.+))?");
  
  Version() {}
  
  public static Version create(int paramInt1, int paramInt2, int paramInt3, String paramString)
  {
    return new AutoValue_Version(paramInt1, paramInt2, paramInt3, paramString);
  }
  
  private static BigInteger createBigInteger(Version paramVersion)
  {
    return BigInteger.valueOf(paramVersion.getMajor()).shiftLeft(32).or(BigInteger.valueOf(paramVersion.getMinor())).shiftLeft(32).or(BigInteger.valueOf(paramVersion.getPatch()));
  }
  
  public static Version parse(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    paramString = VERSION_STRING_PATTERN.matcher(paramString);
    if (!paramString.matches()) {
      return null;
    }
    int i = Integer.parseInt(paramString.group(1));
    int j = Integer.parseInt(paramString.group(2));
    int k = Integer.parseInt(paramString.group(3));
    if (paramString.group(4) != null) {
      paramString = paramString.group(4);
    } else {
      paramString = "";
    }
    return create(i, j, k, paramString);
  }
  
  public int compareTo(int paramInt)
  {
    return compareTo(paramInt, 0);
  }
  
  public int compareTo(int paramInt1, int paramInt2)
  {
    if (getMajor() == paramInt1) {
      return Integer.compare(getMinor(), paramInt2);
    }
    return Integer.compare(getMajor(), paramInt1);
  }
  
  public int compareTo(Version paramVersion)
  {
    return createBigInteger(this).compareTo(createBigInteger(paramVersion));
  }
  
  public final boolean equals(Object paramObject)
  {
    boolean bool1 = paramObject instanceof Version;
    boolean bool2 = false;
    if (!bool1) {
      return false;
    }
    paramObject = (Version)paramObject;
    bool1 = bool2;
    if (Objects.equals(Integer.valueOf(getMajor()), Integer.valueOf(paramObject.getMajor())))
    {
      bool1 = bool2;
      if (Objects.equals(Integer.valueOf(getMinor()), Integer.valueOf(paramObject.getMinor())))
      {
        bool1 = bool2;
        if (Objects.equals(Integer.valueOf(getPatch()), Integer.valueOf(paramObject.getPatch()))) {
          bool1 = true;
        }
      }
    }
    return bool1;
  }
  
  abstract String getDescription();
  
  abstract int getMajor();
  
  abstract int getMinor();
  
  abstract int getPatch();
  
  public final int hashCode()
  {
    return Objects.hash(new Object[] { Integer.valueOf(getMajor()), Integer.valueOf(getMinor()), Integer.valueOf(getPatch()) });
  }
  
  public final String toString()
  {
    StringBuilder localStringBuilder1 = new StringBuilder();
    localStringBuilder1.append(getMajor());
    localStringBuilder1.append(".");
    localStringBuilder1.append(getMinor());
    localStringBuilder1.append(".");
    localStringBuilder1.append(getPatch());
    StringBuilder localStringBuilder2 = new StringBuilder(localStringBuilder1.toString());
    if (!TextUtils.isEmpty(getDescription()))
    {
      localStringBuilder1 = new StringBuilder();
      localStringBuilder1.append("-");
      localStringBuilder1.append(getDescription());
      localStringBuilder2.append(localStringBuilder1.toString());
    }
    return localStringBuilder2.toString();
  }
}
