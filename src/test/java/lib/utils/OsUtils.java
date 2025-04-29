package lib.utils;

public final class OsUtils
{
   private static String OS = null;
   public static String getOsName()
   {
      if(OS == null) { OS = System.getProperty("os.name"); }
      return OS;
   }
   public static boolean isWindows()
   {
      return getOsName().startsWith("Windows");
   }
   public static String getDriverName(){
    if (isWindows()) {
        return "chromedriver.exe";
    }else{
        return "chromedriver";
    }
   }
}