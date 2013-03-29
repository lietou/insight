package com.dainping.cat.consumer.dal.report;

import java.util.List;
import org.unidal.dal.jdbc.DalException;
import org.unidal.dal.jdbc.AbstractDao;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;

public class TaskDao extends AbstractDao {
   public Task createLocal() {
      Task proto = new Task();

      return proto;
   }

   public int deleteByPK(Task proto) throws DalException {
      return getQueryEngine().deleteSingle(
            TaskEntity.DELETE_BY_PK,
            proto);
   }
   
   public List<Task> findAllDistinct(java.util.Date startDate, java.util.Date endDate, Readset<Task> readset) throws DalException {
      Task proto = new Task();

      proto.setStartDate(startDate);
      proto.setEndDate(endDate);

      List<Task> result = getQueryEngine().queryMultiple(
            TaskEntity.FIND_ALL_DISTINCT, 
            proto,
            readset);
      
      return result;
   }
   
   public List<Task> findAll(int status, java.util.Date startDate, java.util.Date endDate, String reportName, String reportDomain, int taskType, Readset<Task> readset) throws DalException {
      Task proto = new Task();

      proto.setStatus(status);
      proto.setStartDate(startDate);
      proto.setEndDate(endDate);
      proto.setReportName(reportName);
      proto.setReportDomain(reportDomain);
      proto.setTaskType(taskType);

      List<Task> result = getQueryEngine().queryMultiple(
            TaskEntity.FIND_ALL, 
            proto,
            readset);
      
      return result;
   }
   
   public List<Task> findByStatusTypeName(int status, java.util.Date startDate, java.util.Date endDate, String reportName, String reportDomain, int taskType, int startLimit, int endLimit, Readset<Task> readset) throws DalException {
      Task proto = new Task();

      proto.setStatus(status);
      proto.setStartDate(startDate);
      proto.setEndDate(endDate);
      proto.setReportName(reportName);
      proto.setReportDomain(reportDomain);
      proto.setTaskType(taskType);
      proto.setStartLimit(startLimit);
      proto.setEndLimit(endLimit);

      List<Task> result = getQueryEngine().queryMultiple(
            TaskEntity.FIND_BY_STATUS_TYPE_NAME, 
            proto,
            readset);
      
      return result;
   }
   
   public Task findByPK(int keyId, Readset<Task> readset) throws DalException {
      Task proto = new Task();

      proto.setKeyId(keyId);

      Task result = getQueryEngine().querySingle(
            TaskEntity.FIND_BY_PK, 
            proto,
            readset);
      
      return result;
   }
   
   public Task findByStatusConsumer(int status, String consumer, Readset<Task> readset) throws DalException {
      Task proto = new Task();

      proto.setStatus(status);
      proto.setConsumer(consumer);

      Task result = getQueryEngine().querySingle(
            TaskEntity.FIND_BY_STATUS_CONSUMER, 
            proto,
            readset);
      
      return result;
   }
   
   public Task findByDomainNameTypePeriod(String reportName, String reportDomain, int taskType, java.util.Date reportPeriod, Readset<Task> readset) throws DalException {
      Task proto = new Task();

      proto.setReportName(reportName);
      proto.setReportDomain(reportDomain);
      proto.setTaskType(taskType);
      proto.setReportPeriod(reportPeriod);

      Task result = getQueryEngine().querySingle(
            TaskEntity.FIND_BY_DOMAIN_NAME_TYPE_PERIOD, 
            proto,
            readset);
      
      return result;
   }
   
   @Override
   protected Class<?>[] getEntityClasses() {
      return new Class<?>[] { TaskEntity.class };
   }

   public int insert(Task proto) throws DalException {
      return getQueryEngine().insertSingle(
            TaskEntity.INSERT,
            proto);
   }
   
   public int updateByPK(Task proto, Updateset<Task> updateset) throws DalException {
      return getQueryEngine().updateSingle(
            TaskEntity.UPDATE_BY_PK,
            proto,
            updateset);
   }
   
   public int updateTodoToDoing(Task proto, Updateset<Task> updateset) throws DalException {
      return getQueryEngine().updateSingle(
            TaskEntity.UPDATE_TODO_TO_DOING,
            proto,
            updateset);
   }
   
   public int updateDoingToDone(Task proto, Updateset<Task> updateset) throws DalException {
      return getQueryEngine().updateSingle(
            TaskEntity.UPDATE_DOING_TO_DONE,
            proto,
            updateset);
   }
   
   public int updateFailureToDone(Task proto, Updateset<Task> updateset) throws DalException {
      return getQueryEngine().updateSingle(
            TaskEntity.UPDATE_FAILURE_TO_DONE,
            proto,
            updateset);
   }
   
   public int updateStatusToTodo(Task proto, Updateset<Task> updateset) throws DalException {
      return getQueryEngine().updateSingle(
            TaskEntity.UPDATE_STATUS_TO_TODO,
            proto,
            updateset);
   }
   
   public int updateDoingToFail(Task proto, Updateset<Task> updateset) throws DalException {
      return getQueryEngine().updateSingle(
            TaskEntity.UPDATE_DOING_TO_FAIL,
            proto,
            updateset);
   }
   
}
