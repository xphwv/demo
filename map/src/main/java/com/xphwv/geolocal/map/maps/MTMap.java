package com.xphwv.geolocal.map.maps;

import com.alibaba.fastjson.JSON;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by xupan on 2018/3/15
 */
public class MTMap {
    static Logger logger = Logger.getLogger("");
    private static HttpClient client = new HttpClient();
    final static String tempUrl = "http://1900-usdgk-sl-eb.hotel.test.sankuai.com/api/v1/ebooking/district/children?id=%s";

    public static void main(String[] args) {
        Map province = getData(0);
//        handle(data);
        if (province != null && province.containsKey("data")) {
            List<Map<String, Object>> provinceList = (List) province.get("data");
            for (Map<String, Object> provinceMap : provinceList) {

                Map city = getData((Integer) provinceMap.get("id"));
                if (city != null && city.containsKey("data")) {
                    List<Map<String, Object>> cityList = (List) city.get("data");
                    for (Map<String, Object> cityMap : cityList) {
                        Map area = getData((Integer) cityMap.get("id"));
                        if (area != null && area.containsKey("data")) {
                            List<Map<String, Object>> areaList = (List) area.get("data");
                            for (Map<String, Object> areaMap : areaList) {
                                logger.error(provinceMap.get("name") + "," + provinceMap.get("id") + "," + cityMap.get("name") + "," + cityMap.get("id") + "," + areaMap.get("name") + "," + areaMap.get("id"));
                            }
                        }

                    }
                }

            }
        }
    }

//    private static void handle(Map data) {
//        if(data!=null&&data.containsKey("data")){
//            List<Map<String,Object>> list= (List) data.get("data");
//            for (Map<String, Object> map : list) {
//                logger.info(map.get("name")+"--------"+map.get("id"));
//                handle(getData((Integer) map.get("id")));
//            }
//        }
//    }

    public static Map getData(Integer id) {

        HttpMethod method = new GetMethod(String.format(tempUrl, new Object[]{id}));
        try {
            client.executeMethod(method);
            String baseJson = method.getResponseBodyAsString();
            return JSON.parseObject(baseJson, Map.class);
        } catch (IOException e) {
            return null;
        }

    }
}
