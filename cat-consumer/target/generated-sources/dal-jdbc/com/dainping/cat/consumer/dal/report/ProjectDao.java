package com.dainping.cat.consumer.dal.report;

import java.util.List;
import org.unidal.dal.jdbc.DalException;
import org.unidal.dal.jdbc.AbstractDao;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;

public class ProjectDao extends AbstractDao {
   public Project createLocal() {
      Project proto = new Project();

      return proto;
   }

   public int deleteByPK(Project proto) throws DalException {
      return getQueryEngine().deleteSingle(
            ProjectEntity.DELETE_BY_PK,
            proto);
   }
   
   public List<Project> findAll(Readset<Project> readset) throws DalException {
      Project proto = new Project();

      List<Project> result = getQueryEngine().queryMultiple(
            ProjectEntity.FIND_ALL, 
            proto,
            readset);
      
      return result;
   }
   
   public Project findByPK(int keyId, Readset<Project> readset) throws DalException {
      Project proto = new Project();

      proto.setKeyId(keyId);

      Project result = getQueryEngine().querySingle(
            ProjectEntity.FIND_BY_PK, 
            proto,
            readset);
      
      return result;
   }
   
   public Project findByDomain(String domain, Readset<Project> readset) throws DalException {
      Project proto = new Project();

      proto.setDomain(domain);

      Project result = getQueryEngine().querySingle(
            ProjectEntity.FIND_BY_DOMAIN, 
            proto,
            readset);
      
      return result;
   }
   
   @Override
   protected Class<?>[] getEntityClasses() {
      return new Class<?>[] { ProjectEntity.class };
   }

   public int insert(Project proto) throws DalException {
      return getQueryEngine().insertSingle(
            ProjectEntity.INSERT,
            proto);
   }
   
   public int updateByPK(Project proto, Updateset<Project> updateset) throws DalException {
      return getQueryEngine().updateSingle(
            ProjectEntity.UPDATE_BY_PK,
            proto,
            updateset);
   }
   
}
