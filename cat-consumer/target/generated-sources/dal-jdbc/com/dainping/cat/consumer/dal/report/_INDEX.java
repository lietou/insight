package com.dainping.cat.consumer.dal.report;

public class _INDEX {
   public static Class<?>[] getEntityClasses() {
      return new Class<?>[] { ReportEntity.class, HostinfoEntity.class, SqltableEntity.class, TaskEntity.class, ProjectEntity.class };
   }

   public static Class<?>[] getDaoClasses() {
      return new Class<?>[] { ReportDao.class, HostinfoDao.class, SqltableDao.class, TaskDao.class, ProjectDao.class };
   }

   public static Class<?>[] getDoClasses() {
      return new Class<?>[] { Report.class, Hostinfo.class, Sqltable.class, Task.class, Project.class };
   }

}
