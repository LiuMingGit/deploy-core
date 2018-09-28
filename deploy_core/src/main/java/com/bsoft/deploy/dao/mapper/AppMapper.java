package com.bsoft.deploy.dao.mapper;

import com.bsoft.deploy.dao.entity.App;
import com.bsoft.deploy.dao.entity.AppPackage;
import com.bsoft.deploy.dao.entity.FileDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 应用mapper
 * Created on 2018/8/23.
 *
 * @author yangl
 */
@Mapper
public interface AppMapper {

    /**
     * 保存基础应用
     * @param app
     */
    @Insert("insert into base_app(appName,path,describes) values(#{appName},#{path},#{describes})")
    @Options(useGeneratedKeys = true, keyProperty = "appId", keyColumn = "appId")
    void save(App app);

    @Update("update base_app set appName=#{appName},path=#{path},describes=#{describes} where appId=#{appId}")
    void update(App app);

    @Delete("delete from base_app where appId=#{appId}")
    void delete(int appId);

    @Select("select appId,appName,path,describes from base_app")
    List<App> loadApps();

    @Select("select appId,appName,path,describes from base_app where appId=#{appId}")
    App findApp(int appId);

    @Select("select id,appId,version,updateDesc,optime,type,locked from app_upd_pkg where appId=#{appId} order by id desc")
    List<AppPackage> loadAppPackages(int appId);

    @Select("select id,appId,version,updateDesc,optime,type,locked from app_upd_pkg where id=#{id}")
    AppPackage findAppPackageById(int id);

    @Select("select a.id,a.appId,a.version,a.updateDesc,a.optime,a.type,a.locked from app_upd_pkg a,slave_app b where a.id=b.pkgId and b.id=#{slaveAppId}")
    AppPackage findSlaveAppPackage(int slaveAppId);

    @Insert("insert into app_upd_pkg(appId,version,updateDesc,optime,type,locked) values(#{appId},#{version},#{updateDesc},#{optime, jdbcType=TIMESTAMP},#{type},#{locked})")
    @Options(useGeneratedKeys = true)
    void saveAppPackage(AppPackage appPackage);

    @Update("update app_upd_pkg set version=#{version},updateDesc=#{updateDesc},type=#{type},locked=#{locked} where id=#{id}")
    void updateAppPackage(AppPackage appPackage);

    @Select("select count(id) from app_upd_pkg where appId=#{appId} and id>#{pkgId}")
    int hasUpdateVersion(@Param("appId") int appId,@Param("pkgId") int pkgId);

    @Select("select ifNull(max(id),0) from app_upd_pkg where appId=#{appId} and type=1 and id<=#{id}")
    int findLastFullAppPackage(@Param("appId") int appId,@Param("id") int pkgId);

    /**
     * 获取需要部署的更新包
     * @param appId
     * @param startPkgId
     * @param endPkgId
     */
    @Select("select id,appId,version,updateDesc,optime,type,locked from app_upd_pkg where appId=#{appId} and id>=#{startPkgId} and id<=#{endPkgId} order by id")
    List<AppPackage> findUpdates(@Param("appId") int appId,@Param("startPkgId") int startPkgId,@Param("endPkgId") int endPkgId);

    @Select("select a.id,a.pkgId,a.filename,a.path,a.optime,a.mark,b.appId from app_upd_pkg_file a,app_upd_pkg b where a.pkgId=b.id and a.pkgId=#{pkgId} order by a.id")
    List<FileDTO> loadAppPackageFiles(int pkgId);

    /**
     * 获取可以更新的更新包信息
     * @param appId
     * @param pkgId
     */
    @Select("select id,appId,version,updateDesc,optime,type,locked from app_upd_pkg where appId=#{appId} and id>#{pkgId} order by id desc")
    List<AppPackage> getUpdates(@Param("appId") int appId,@Param("pkgId") int pkgId);

    @Select("select path from base_app_file where appId=#{appId} and sign=1")
    List<String> findAppIgnoreFiles(int appId);
}
