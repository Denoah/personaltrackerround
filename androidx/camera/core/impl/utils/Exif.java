package androidx.camera.core.impl.utils;

import android.location.Location;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class Exif
{
  private static final ThreadLocal<SimpleDateFormat> DATETIME_FORMAT = new ThreadLocal()
  {
    public SimpleDateFormat initialValue()
    {
      return new SimpleDateFormat("yyyy:MM:dd HH:mm:ss", Locale.US);
    }
  };
  private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT;
  public static final long INVALID_TIMESTAMP = -1L;
  private static final String KILOMETERS_PER_HOUR = "K";
  private static final String KNOTS = "N";
  private static final String MILES_PER_HOUR = "M";
  private static final String TAG = Exif.class.getSimpleName();
  private static final ThreadLocal<SimpleDateFormat> TIME_FORMAT;
  private final ExifInterface mExifInterface;
  private boolean mRemoveTimestamp = false;
  
  static
  {
    DATE_FORMAT = new ThreadLocal()
    {
      public SimpleDateFormat initialValue()
      {
        return new SimpleDateFormat("yyyy:MM:dd", Locale.US);
      }
    };
    TIME_FORMAT = new ThreadLocal()
    {
      public SimpleDateFormat initialValue()
      {
        return new SimpleDateFormat("HH:mm:ss", Locale.US);
      }
    };
  }
  
  private Exif(ExifInterface paramExifInterface)
  {
    this.mExifInterface = paramExifInterface;
  }
  
  private void attachLastModifiedTimestamp()
  {
    long l = System.currentTimeMillis();
    String str = convertToExifDateTime(l);
    this.mExifInterface.setAttribute("DateTime", str);
    try
    {
      str = Long.toString(l - convertFromExifDateTime(str).getTime());
      this.mExifInterface.setAttribute("SubSecTime", str);
      return;
    }
    catch (ParseException localParseException)
    {
      for (;;) {}
    }
  }
  
  private static Date convertFromExifDate(String paramString)
    throws ParseException
  {
    return ((SimpleDateFormat)DATE_FORMAT.get()).parse(paramString);
  }
  
  private static Date convertFromExifDateTime(String paramString)
    throws ParseException
  {
    return ((SimpleDateFormat)DATETIME_FORMAT.get()).parse(paramString);
  }
  
  private static Date convertFromExifTime(String paramString)
    throws ParseException
  {
    return ((SimpleDateFormat)TIME_FORMAT.get()).parse(paramString);
  }
  
  private static String convertToExifDateTime(long paramLong)
  {
    return ((SimpleDateFormat)DATETIME_FORMAT.get()).format(new Date(paramLong));
  }
  
  public static Exif createFromFile(File paramFile)
    throws IOException
  {
    return createFromFileString(paramFile.toString());
  }
  
  public static Exif createFromFileString(String paramString)
    throws IOException
  {
    return new Exif(new ExifInterface(paramString));
  }
  
  public static Exif createFromInputStream(InputStream paramInputStream)
    throws IOException
  {
    return new Exif(new ExifInterface(paramInputStream));
  }
  
  private long parseTimestamp(String paramString)
  {
    long l1 = -1L;
    if (paramString == null) {
      return -1L;
    }
    try
    {
      long l2 = convertFromExifDateTime(paramString).getTime();
      l1 = l2;
    }
    catch (ParseException paramString)
    {
      for (;;) {}
    }
    return l1;
  }
  
  private long parseTimestamp(String paramString1, String paramString2)
  {
    if ((paramString1 == null) && (paramString2 == null)) {
      return -1L;
    }
    long l;
    if (paramString2 == null) {
      try
      {
        l = convertFromExifDate(paramString1).getTime();
        return l;
      }
      catch (ParseException paramString1)
      {
        return -1L;
      }
    }
    if (paramString1 == null) {
      try
      {
        l = convertFromExifTime(paramString2).getTime();
        return l;
      }
      catch (ParseException paramString1)
      {
        return -1L;
      }
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString1);
    localStringBuilder.append(" ");
    localStringBuilder.append(paramString2);
    return parseTimestamp(localStringBuilder.toString());
  }
  
  public void attachLocation(Location paramLocation)
  {
    this.mExifInterface.setGpsInfo(paramLocation);
  }
  
  public void attachTimestamp()
  {
    long l = System.currentTimeMillis();
    String str = convertToExifDateTime(l);
    this.mExifInterface.setAttribute("DateTimeOriginal", str);
    this.mExifInterface.setAttribute("DateTimeDigitized", str);
    try
    {
      str = Long.toString(l - convertFromExifDateTime(str).getTime());
      this.mExifInterface.setAttribute("SubSecTimeOriginal", str);
      this.mExifInterface.setAttribute("SubSecTimeDigitized", str);
      this.mRemoveTimestamp = false;
      return;
    }
    catch (ParseException localParseException)
    {
      for (;;) {}
    }
  }
  
  public void flipHorizontally()
  {
    int i;
    switch (getOrientation())
    {
    default: 
      i = 2;
      break;
    case 8: 
      i = 7;
      break;
    case 7: 
      i = 8;
      break;
    case 6: 
      i = 5;
      break;
    case 5: 
      i = 6;
      break;
    case 4: 
      i = 3;
      break;
    case 3: 
      i = 4;
      break;
    case 2: 
      i = 1;
    }
    this.mExifInterface.setAttribute("Orientation", String.valueOf(i));
  }
  
  public void flipVertically()
  {
    int i;
    switch (getOrientation())
    {
    default: 
      i = 4;
      break;
    case 8: 
      i = 5;
      break;
    case 7: 
      i = 6;
      break;
    case 6: 
      i = 7;
      break;
    case 5: 
      i = 8;
      break;
    case 4: 
      i = 1;
      break;
    case 3: 
      i = 2;
      break;
    case 2: 
      i = 3;
    }
    this.mExifInterface.setAttribute("Orientation", String.valueOf(i));
  }
  
  public String getDescription()
  {
    return this.mExifInterface.getAttribute("ImageDescription");
  }
  
  public int getHeight()
  {
    return this.mExifInterface.getAttributeInt("ImageLength", 0);
  }
  
  public long getLastModifiedTimestamp()
  {
    l1 = parseTimestamp(this.mExifInterface.getAttribute("DateTime"));
    if (l1 == -1L) {
      return -1L;
    }
    String str = this.mExifInterface.getAttribute("SubSecTime");
    l2 = l1;
    if (str != null) {}
    try
    {
      for (l2 = Long.parseLong(str); l2 > 1000L; l2 /= 10L) {}
      l2 = l1 + l2;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      for (;;)
      {
        l2 = l1;
      }
    }
    return l2;
  }
  
  public Location getLocation()
  {
    String str = this.mExifInterface.getAttribute("GPSProcessingMethod");
    double[] arrayOfDouble = this.mExifInterface.getLatLong();
    double d1 = this.mExifInterface.getAltitude(0.0D);
    double d2 = this.mExifInterface.getAttributeDouble("GPSSpeed", 0.0D);
    Object localObject1 = this.mExifInterface.getAttribute("GPSSpeedRef");
    Object localObject2 = localObject1;
    if (localObject1 == null) {
      localObject2 = "K";
    }
    long l = parseTimestamp(this.mExifInterface.getAttribute("GPSDateStamp"), this.mExifInterface.getAttribute("GPSTimeStamp"));
    if (arrayOfDouble == null) {
      return null;
    }
    localObject1 = str;
    if (str == null) {
      localObject1 = TAG;
    }
    localObject1 = new Location((String)localObject1);
    ((Location)localObject1).setLatitude(arrayOfDouble[0]);
    ((Location)localObject1).setLongitude(arrayOfDouble[1]);
    if (d1 != 0.0D) {
      ((Location)localObject1).setAltitude(d1);
    }
    if (d2 != 0.0D)
    {
      int i = -1;
      int j = ((String)localObject2).hashCode();
      if (j != 75)
      {
        if (j != 77)
        {
          if ((j == 78) && (((String)localObject2).equals("N"))) {
            i = 1;
          }
        }
        else if (((String)localObject2).equals("M")) {
          i = 0;
        }
      }
      else if (((String)localObject2).equals("K")) {
        i = 2;
      }
      if (i != 0)
      {
        if (i != 1) {
          d1 = Speed.fromKilometersPerHour(d2).toMetersPerSecond();
        } else {
          d1 = Speed.fromKnots(d2).toMetersPerSecond();
        }
      }
      else {
        d1 = Speed.fromMilesPerHour(d2).toMetersPerSecond();
      }
      ((Location)localObject1).setSpeed((float)d1);
    }
    if (l != -1L) {
      ((Location)localObject1).setTime(l);
    }
    return localObject1;
  }
  
  public int getOrientation()
  {
    return this.mExifInterface.getAttributeInt("Orientation", 0);
  }
  
  public int getRotation()
  {
    switch (getOrientation())
    {
    default: 
      return 0;
    case 8: 
      return 270;
    case 6: 
    case 7: 
      return 90;
    case 5: 
      return 270;
    }
    return 180;
  }
  
  public long getTimestamp()
  {
    l1 = parseTimestamp(this.mExifInterface.getAttribute("DateTimeOriginal"));
    if (l1 == -1L) {
      return -1L;
    }
    String str = this.mExifInterface.getAttribute("SubSecTimeOriginal");
    l2 = l1;
    if (str != null) {}
    try
    {
      for (l2 = Long.parseLong(str); l2 > 1000L; l2 /= 10L) {}
      l2 = l1 + l2;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      for (;;)
      {
        l2 = l1;
      }
    }
    return l2;
  }
  
  public int getWidth()
  {
    return this.mExifInterface.getAttributeInt("ImageWidth", 0);
  }
  
  public boolean isFlippedHorizontally()
  {
    return getOrientation() == 2;
  }
  
  public boolean isFlippedVertically()
  {
    int i = getOrientation();
    return (i == 4) || (i == 5) || (i == 7);
  }
  
  public void removeLocation()
  {
    this.mExifInterface.setAttribute("GPSProcessingMethod", null);
    this.mExifInterface.setAttribute("GPSLatitude", null);
    this.mExifInterface.setAttribute("GPSLatitudeRef", null);
    this.mExifInterface.setAttribute("GPSLongitude", null);
    this.mExifInterface.setAttribute("GPSLongitudeRef", null);
    this.mExifInterface.setAttribute("GPSAltitude", null);
    this.mExifInterface.setAttribute("GPSAltitudeRef", null);
    this.mExifInterface.setAttribute("GPSSpeed", null);
    this.mExifInterface.setAttribute("GPSSpeedRef", null);
    this.mExifInterface.setAttribute("GPSDateStamp", null);
    this.mExifInterface.setAttribute("GPSTimeStamp", null);
  }
  
  public void removeTimestamp()
  {
    this.mExifInterface.setAttribute("DateTime", null);
    this.mExifInterface.setAttribute("DateTimeOriginal", null);
    this.mExifInterface.setAttribute("DateTimeDigitized", null);
    this.mExifInterface.setAttribute("SubSecTime", null);
    this.mExifInterface.setAttribute("SubSecTimeOriginal", null);
    this.mExifInterface.setAttribute("SubSecTimeDigitized", null);
    this.mRemoveTimestamp = true;
  }
  
  public void rotate(int paramInt)
  {
    if (paramInt % 90 != 0)
    {
      Log.w(TAG, String.format("Can only rotate in right angles (eg. 0, 90, 180, 270). %d is unsupported.", new Object[] { Integer.valueOf(paramInt) }));
      this.mExifInterface.setAttribute("Orientation", String.valueOf(0));
      return;
    }
    int i = paramInt % 360;
    int j = getOrientation();
    int k;
    for (;;)
    {
      paramInt = j;
      k = i;
      if (i >= 0) {
        break;
      }
      i += 90;
      switch (j)
      {
      default: 
        j = 8;
        break;
      case 7: 
        j = 2;
        break;
      case 6: 
        j = 1;
        break;
      case 5: 
        j = 4;
        break;
      case 4: 
        j = 7;
        break;
      case 3: 
      case 8: 
        j = 6;
        break;
      case 2: 
        j = 5;
      }
    }
    while (k > 0)
    {
      k -= 90;
      switch (paramInt)
      {
      default: 
        paramInt = 6;
        break;
      case 8: 
        paramInt = 1;
        break;
      case 7: 
        paramInt = 4;
        break;
      case 6: 
        paramInt = 3;
        break;
      case 5: 
        paramInt = 2;
        break;
      case 4: 
        paramInt = 5;
        break;
      case 3: 
        paramInt = 8;
        break;
      case 2: 
        paramInt = 7;
      }
    }
    this.mExifInterface.setAttribute("Orientation", String.valueOf(paramInt));
  }
  
  public void save()
    throws IOException
  {
    if (!this.mRemoveTimestamp) {
      attachLastModifiedTimestamp();
    }
    this.mExifInterface.saveAttributes();
  }
  
  public void setDescription(String paramString)
  {
    this.mExifInterface.setAttribute("ImageDescription", paramString);
  }
  
  public void setOrientation(int paramInt)
  {
    this.mExifInterface.setAttribute("Orientation", String.valueOf(paramInt));
  }
  
  public String toString()
  {
    return String.format(Locale.ENGLISH, "Exif{width=%s, height=%s, rotation=%d, isFlippedVertically=%s, isFlippedHorizontally=%s, location=%s, timestamp=%s, description=%s}", new Object[] { Integer.valueOf(getWidth()), Integer.valueOf(getHeight()), Integer.valueOf(getRotation()), Boolean.valueOf(isFlippedVertically()), Boolean.valueOf(isFlippedHorizontally()), getLocation(), Long.valueOf(getTimestamp()), getDescription() });
  }
  
  private static final class Speed
  {
    private Speed() {}
    
    static Converter fromKilometersPerHour(double paramDouble)
    {
      return new Converter(paramDouble * 0.621371D);
    }
    
    static Converter fromKnots(double paramDouble)
    {
      return new Converter(paramDouble * 1.15078D);
    }
    
    static Converter fromMetersPerSecond(double paramDouble)
    {
      return new Converter(paramDouble * 2.23694D);
    }
    
    static Converter fromMilesPerHour(double paramDouble)
    {
      return new Converter(paramDouble);
    }
    
    static final class Converter
    {
      final double mMph;
      
      Converter(double paramDouble)
      {
        this.mMph = paramDouble;
      }
      
      double toKilometersPerHour()
      {
        return this.mMph / 0.621371D;
      }
      
      double toKnots()
      {
        return this.mMph / 1.15078D;
      }
      
      double toMetersPerSecond()
      {
        return this.mMph / 2.23694D;
      }
      
      double toMilesPerHour()
      {
        return this.mMph;
      }
    }
  }
}
