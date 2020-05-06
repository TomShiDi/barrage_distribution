package com.barrage.webcontroller.controller;

import com.barrage.webcontroller.utils.HttpUtil;
import com.google.gson.Gson;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;


//@Api(value = "百度网盘资源搜索操作接口")
@RestController
@RequestMapping("/pan")
public class PanSearchController {

    /**
     * 请求网盘服务器获取数据
     *
     * @param q 查询的关键字
     * @param p 页码
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "请求其他服务器获取数据", notes = "请求其他服务器获取数据", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "q", value = "搜索字段", defaultValue = "11", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "p", value = "待查询数据页", defaultValue = "1", paramType = "query", dataTypeClass = Integer.class)
    })
    @ApiResponse(code = 200, message = "查询成功")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String panSearch(@RequestParam(name = "q", defaultValue = "11") String q,
                            @RequestParam(name = "p", defaultValue = "1") Integer p) throws Exception {
        Gson gson = new Gson();
        q = URLEncoder.encode(q);
        String url = "http://106.15.195.249:8011/search_new?q=" + q + "&p=" + p;
        return HttpUtil.doGet(url);
//        Map<String, Object> map = new HashMap<>();
//        map = gson.fromJson(result, map.getClass());
//        Object object = ((Map<String, Object>) (map.get("list"))).get("data");

//        return result;
    }
}
