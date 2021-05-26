package com.nined.esportsota;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EsportsOtaApplicationTests {

    @Test
    void contextLoads() throws Exception{

    }

    /*public String getOrderId() {
        Date today = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyMMdd");
        String prefix=sdf.format(today);
        String lastNo=hotelOrderRepository.findByPrefix(prefix);
        if (lastNo == null || lastNo.startsWith(prefix) == false) {
            Integer seq = 1;
            String newNo = prefix + StringUtil.leftPad(seq.toString(), 5, "0");

            while (hotelOrderRepository.findByNewNo(newNo) != null) {
                seq += 1;
                newNo = prefix + StringUtil.leftPad(seq.toString(), 5, "0");
            }
            return newNo;
        } else {
            Integer seq = Integer.parseInt(lastNo.substring(prefix.length())) + 1;
            String newNo = prefix + StringUtil.leftPad(seq.toString(), 5, "0");
            while (hotelOrderRepository.findByNewNo(newNo) != null) {
                seq += 1;
                newNo = prefix + StringUtil.leftPad(seq.toString(), 5, "0");
            }
            return newNo;
        }
    }*/

}
