package com.bsoft.deploy.dao.mapper;

import com.bsoft.deploy.dao.entity.FileDTO;
import com.bsoft.deploy.dao.entity.FileLog;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 应用文件
 * Created on 2018/8/13.
 *
 * @author yangl
 */
@Mapper
public interface AppFileMapper {
    /**
     * 获取文件对象
     *
     * @param appId
     * @param path
     * @return
     */
    @Select("select id,mark from base_app_file where appId=#{appId} and path=#{path}")
    FileDTO loadAppFile(@Param("appId") int appId, @Param("path") String path);

    @Select("select id,filename,appId,path,mark from base_app_file where appId=#{appId}")
    List<FileDTO> loadAppFiles(int appId);

    /**
     * 插入文件对象
     *
     * @param fileDTO
     * @return
     */
    @Insert({"insert into base_app_file(appId, filename, path, mark, optime,sign) values(#{appId}, #{filename}, #{path},#{mark}, #{optime, jdbcType=TIMESTAMP},0)"})
    @Options(useGeneratedKeys = true)
    int saveAppFile(FileDTO fileDTO);

    /**
     * 文件同步日志
     *
     * @param fileLog
     * @return
     */
    @Insert({"insert into file_transfer_log(slaveId, appId, fileId, mark, message, optime, status) values(#{slaveId}, #{appId}, #{fileId}, #{mark}, #{message}, #{optime, jdbcType=TIMESTAMP},0)"})
    @Options(useGeneratedKeys = true, keyColumn = "id")
    int saveFileTransferLog(FileLog fileLog);

    @Update({"update file_transfer_log set status=#{status}, message=#{message} where id=#{id}"})
    void updateFileTransferLog(FileLog fileLog);


    /**
     * 更新文件标记和最后修改时间
     *
     * @param fileDTO
     * @return
     */
    @Update({"update base_app_file set optime=#{fileDTO.optime, jdbcType=TIMESTAMP},mark=#{fileDTO.mark} where id=#{fileDTO.id}"})
    int updateAppFile(FileDTO fileDTO);

    /**
     * 根据id删除文件
     *
     * @param id
     */
    @Delete({"delete base_app_file where id=#{id}"})
    void deleteById(int id);


}
