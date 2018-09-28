package com.bsoft.deploy.dao.mapper;

import com.bsoft.deploy.dao.entity.Slave;
import com.bsoft.deploy.dao.entity.SlaveApp;
import com.bsoft.deploy.dao.entity.UpdateLog;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 节点相关的
 * Created on 2018/8/17.
 *
 * @author yangl
 */
@Mapper
public interface SlaveMapper {

    /**
     * 新增目标节点
     *
     * @param slave
     * @return
     */
    @Insert("insert into base_slave(name,ip,describes) values(#{name},#{ip},#{describes})")
    @Options(useGeneratedKeys = true)
    int save(Slave slave);

    @Update("update base_slave set name=#{name},ip=#{ip},describes=#{describes} where id=#{id}")
    void update(Slave slave);

    @Delete("delete from base_slave where id=#{id}")
    void delete(int id);

    @Select("select id,name,ip,status,describes from base_slave")
    List<Slave> loadSlaves();

    @Select("select id,appId,pkgId,slaveId,app_target_path,app_backup_path,app_tomcat_home from slave_app where slaveId=#{slaveId}")
    List<SlaveApp> loadSlaveApps(int slaveId);

    @Select("select id,name,ip,describes from base_slave where id=#{id}")
    Slave findSlave(int id);

    @Select("select id,slaveId,appId,pkgId,app_target_path,app_backup_path,app_tomcat_home from slave_app where id=#{id}")
    SlaveApp findSlaveAppById(int id);

    @Select("select id,slaveId,appId,pkgId,app_target_path,app_backup_path,app_tomcat_home from slave_app where id=#{id}")
    SlaveApp findSlaveApp(@Param("slaveId") int slaveId, @Param("appId") int appId);

    @Select("select id,appId,slaveId,pkgId,app_target_path,app_backup_path,app_tomcat_home from slave_app where appId=#{appId}")
    List<SlaveApp> findSlaveApps(int appId);

    @Insert("insert into slave_app(slaveId,appId,app_target_path,app_backup_path,app_tomcat_home) values(#{slaveId},#{appId},#{appTargetPath},#{appBackupPath},#{appTomcatHome})")
    @Options(useGeneratedKeys = true)
    void saveSlaveApp(SlaveApp slaveApp);

    @Update("update slave_app set app_target_path=#{appTargetPath},app_backup_path=#{appBackupPath},app_tomcat_home=#{appTomcatHome} where id=#{id}")
    void updateSlaveApp(SlaveApp slaveApp);

    @Update("update slave_app set pkgId=#{pkgId} where id=#{id}")
    void updateSlaveAppVersion(@Param("id")int slaveAppId, @Param("pkgId") int pkgId);

    @Select("select id,name,ip,status,describes from base_slave where ip=#{ip}")
    Slave findSlaveByIp(String ip);

    @Insert("insert into slave_app_update(slaveAppId,oldPkgId,newPkgId,optime,opuser) values (#{slaveAppId},#{oldPkgId},#{newPkgId},#{optime},#{opuser})")
    @Options(useGeneratedKeys = true)
    void saveUpdateLog(UpdateLog log);
}
