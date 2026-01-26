package com.example.travelservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.travelservice.entity.StockDeductLog;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StockDeductLogMapper extends BaseMapper<StockDeductLog> {

    @Insert(" INSERT INTO stock_deduct_log \n" +
            "        (order_id, pay_no, item_type, item_id, quantity, \n" +
            "         status, fail_reason, retry_count, next_retry_at, created_at, updated_at) \n" +
            "        VALUES \n" +
            "        (#{orderId}, #{payNo}, #{itemType}, #{itemId}, #{quantity}, \n" +
            "         'FAIL', #{failReason}, 0, DATE_ADD(NOW(), INTERVAL 5 MINUTE), NOW(), NOW()) \n" +
            "        ON DUPLICATE KEY UPDATE \n" +
            "          status = IF(retry_count >= #{maxRetry}, 'GAVE_UP', 'FAIL'), \n" +
            "          fail_reason = VALUES(fail_reason), \n" +
            "          retry_count = retry_count + 1, \n" +
            "          next_retry_at = IF( \n" +
            "              retry_count >= #{maxRetry}, \n" +
            "              NULL, \n" +
            "              DATE_ADD( \n" +
            "                NOW(), \n" +
            "                INTERVAL LEAST(60, 5 * POW(2, retry_count)) MINUTE \n" +
            "              ) \n" +
            "          ), \n" +
            "          updated_at = NOW() ")
    int upsertFail(@Param("orderId") Long orderId,
                   @Param("payNo") String payNo,
                   @Param("itemType") String itemType,
                   @Param("itemId") Long itemId,
                   @Param("quantity") Integer quantity,
                   @Param("failReason") String failReason,
                   @Param("maxRetry") int maxRetry);

    @Update("UPDATE stock_deduct_log \n" +
            "        SET status = 'SUCCESS', fail_reason = NULL, next_retry_at = NULL, updated_at = NOW() \n" +
            "        WHERE order_id = #{orderId} AND item_type = #{itemType} AND item_id = #{itemId} ")
    int markSuccess(@Param("orderId") Long orderId,
                   @Param("itemType") String itemType,
                   @Param("itemId") Long itemId);

    @Select("SELECT * FROM stock_deduct_log \n" +
            "        WHERE status IN ('FAIL','RETRYING') \n" +
            "          AND next_retry_at IS NOT NULL \n" +
            "          AND next_retry_at <= NOW() \n" +
            "        ORDER BY next_retry_at ASC \n" +
            "        LIMIT #{limit} ")
    List<StockDeductLog> pickDue(@Param("limit") int limit);

    @Update(" UPDATE stock_deduct_log \n" +
            "        SET status='RETRYING', updated_at=NOW() \n" +
            "        WHERE id=#{id} AND status ='FAIL' ")
    int markRetrying(@Param("id") Long id);
}