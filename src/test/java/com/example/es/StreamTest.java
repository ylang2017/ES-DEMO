package com.example.es;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StreamTest {
    @Test
    public void testStream(){
        String[] strArr = new String[]{"safeaf","oijop","faefa5","a48efa9fe","safeaf"};
        List<String> list = Arrays.asList(strArr);

        list.forEach((cur)->{
            System.out.println("索引："+list.indexOf(cur)+",value:"+cur);
        });

        list.stream().forEach((cur)->{
            System.out.println("stream-索引："+list.indexOf(cur)+",value:"+cur);
        });

        list.stream().distinct().forEach((cur)->{
            System.out.println("stream-distinct-索引："+list.indexOf(cur)+",value:"+cur);
        });

        list.stream().map(i->i+"sss").forEach((cur)->{
            System.out.println("stream-map-索引："+list.indexOf(cur)+",value:"+cur);
        });
    }
}
