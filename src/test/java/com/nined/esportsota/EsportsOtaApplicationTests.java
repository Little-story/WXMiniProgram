package com.nined.esportsota;

import com.nined.esportsota.utils.WXUtil;
import com.sun.org.apache.xpath.internal.operations.String;
import org.junit.jupiter.api.Test;
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
