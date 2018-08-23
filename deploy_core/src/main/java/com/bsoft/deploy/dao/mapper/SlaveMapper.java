package com.bsoft.deploy.dao.mapper;

import com.bsoft.deploy.dao.entity.Slave;
import com.bsoft.deploy.dao.entity.SlaveApp;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * desc
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

    @Select("select id,name,ip,status,describes from base_slave")
    List<Slave> loadSlaves();

    @Select("select id,appId,slaveId,app_target_path,app_backup_path,app_tomcat_home from slave_app where slaveId=#{slaveId}")
    List<SlaveApp> loadSlaveApps(int slaveId);

    @Select("select id,name,ip,describes from base_slave where id=#{id}")
    Slave findSlave(int id);
}
