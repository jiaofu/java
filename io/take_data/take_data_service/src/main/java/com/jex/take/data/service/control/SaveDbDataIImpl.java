package com.jex.take.data.service.control;

import com.jex.take.data.service.dto.TickerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@Service
public class SaveDbDataIImpl implements SaveDbData {
    public static Queue<TickerDTO> queus = new ConcurrentLinkedQueue();
    public static long lastInsert = System.currentTimeMillis();
    public static int limitSize = 100;
    public static int limitSecond = 60000;

    @Override
    public void insertList(TickerDTO tickerDTO) {
        queus.add(tickerDTO);
        checkList();
    }


    @Override
    public void insertSave(List<TickerDTO> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        lastInsert = System.currentTimeMillis();
        log.info("我已经插入输入数据库,条数;" + list.size());

    }

    public void checkList() {
        if (queus.size() >= limitSize || lastInsert + limitSecond < System.currentTimeMillis()) {
            int count = queus.size() > limitSize ? limitSize : queus.size();
            List<TickerDTO> list = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                TickerDTO tickerDTO = queus.poll();
                if (tickerDTO != null) {
                    list.add(tickerDTO);
                }
            }
            if (list.size() > 0) {
                insertSave(list);
            }
        }
    }

    public static void main(String[] args) {
        Queue<String> queus = new ConcurrentLinkedQueue();
        queus.add("a");
        queus.add("b");
        queus.add("c");
        queus.add("d");
        System.out.println("取元素1:" + queus.poll());
        System.out.println("取元素2:" + queus.poll());
        System.out.println("元素个数:" + queus.size());
        queus.add("new");
        System.out.println("元素个数:" + queus.size());
        System.out.println("取元素3:" + queus.poll());
        System.out.println("取元素4:" + queus.poll());
        System.out.println("取元素5:" + queus.poll());
        System.out.println("取元素6:" + queus.poll());
        System.out.println("取元素7:" + queus.poll());
    }
}
