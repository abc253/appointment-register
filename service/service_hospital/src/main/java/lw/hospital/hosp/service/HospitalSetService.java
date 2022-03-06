package lw.hospital.hosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import lw.hospital.model.hosp.HospitalSet;
import lw.hospital.vo.order.SignInfoVo;

public interface HospitalSetService extends IService<HospitalSet> {
    String getSignKey(String hoscode);

    SignInfoVo getSignInfoVo(String hoscode);
}
