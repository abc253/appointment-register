package lw.hospital.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import lw.hospital.model.order.PaymentInfo;
import lw.hospital.model.order.RefundInfo;

public interface RefundInfoService extends IService<RefundInfo> {

    /**
     * 保存退款记录
     * @param paymentInfo
     */
    RefundInfo saveRefundInfo(PaymentInfo paymentInfo);

}