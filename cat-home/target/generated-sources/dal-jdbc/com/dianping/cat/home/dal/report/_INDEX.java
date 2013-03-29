package com.dianping.cat.home.dal.report;

public class _INDEX {
   public static Class<?>[] getEntityClasses() {
      return new Class<?>[] { DailygraphEntity.class, DailyreportEntity.class, GraphEntity.class, WeeklyreportEntity.class, MonthreportEntity.class, LocationEntity.class };
   }

   public static Class<?>[] getDaoClasses() {
      return new Class<?>[] { DailygraphDao.class, DailyreportDao.class, GraphDao.class, WeeklyreportDao.class, MonthreportDao.class, LocationDao.class };
   }

   public static Class<?>[] getDoClasses() {
      return new Class<?>[] { Dailygraph.class, Dailyreport.class, Graph.class, Weeklyreport.class, Monthreport.class, Location.class };
   }

}
