package cn.zm.trip.service;

import entity.BaseScenicSpot;
import entity.dto.BaseScenicSpotDTO;
import entity.vo.BaseScenicSpotVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IBaseScenicSpotService extends IService<BaseScenicSpot> {
    /**
    * 分页查询
    *
    * @param page   分页信息
    * @param BaseScenicSpot 基础景点入参
    * @return 分页结果
    */
    IPage<BaseScenicSpotVO> selectByPage(IPage<BaseScenicSpot> page, BaseScenicSpotDTO BaseScenicSpot);
}
