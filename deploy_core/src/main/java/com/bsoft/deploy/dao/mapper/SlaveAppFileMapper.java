package com.bsoft.deploy.dao.mapper;

import com.bsoft.deploy.dao.entity.SlaveApp;
import com.bsoft.deploy.dao.entity.UpdateLog;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * slave_app  slave_app_file
 * Created on 2018/8/18.
 *
 * @author yangl
 */
public interface SlaveAppFileMapper {

    /**
     * 保存节点app应用基础设置
     *
     * @param slaveApp
     * @return
     */
    @Insert("insert into slave_app(slaveId,appId,app_target_path,app_backup_path,app_tomcat_home) values(#{slaveId},#{appId},#{app_target_path},#{app_backup_path},#{app_tomcat_home})")
    int saveSlaveApp(SlaveApp slaveApp);

    @Update("update slave_app set app_target_path=#{app_target_path},app_backup_path=#{app_backup_path},app_tomcat_home=#{app_tomcat_home} where slaveId=#{slaveId} and appId=#{appId}")
    void updateSlaveApp(SlaveApp slaveApp);

    @Select("select id,appId,slaveId,app_target_path,app_backup_path,app_tomcat_home from slave_app")
    List<SlaveApp> loadSlaveApps();

    @Select("select id,appId,slaveId,app_target_path,app_backup_path,app_tomcat_home from slave_app where appId=#{appId}")
    List<SlaveApp> findSlaveApps(int appId);

    @Select("select id,appId,slaveId,app_target_path,app_backup_path,app_tomcat_home from slave_app where id=#{slaveAppId}")
    SlaveApp findSlaveApp(int slaveAppId);

    /**
     * 更新文件标记和最后修改时间
     *
     * @return
     */
    @Update({"update slave_app_file set mark=#{mark},optime=#{optime, jdbcType=TIMESTAMP} where id=#{id}"})
    int updateSlaveFile(@Param("mark") String mark, @Param("optime") Date optime,  @Param("id") int id);

    @Insert("insert into slave_app_update(slaveAppId,oldPkgId,newPkgId,optime,opuser) values (#{slaveAppId},#{oldPkgId},#{newPkgId},#{optime},#{opuser})")
    @Options(useGeneratedKeys = true)
    void saveUpdateLog(UpdateLog log);
}
