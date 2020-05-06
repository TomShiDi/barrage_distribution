package com.barrage.webcontroller.controller;


import com.barrage.webcontroller.utils.BilibiliJsonUtil;
import com.barrage.webcontroller.utils.HttpUtil;
import com.barrage.api.dao.BilibiliControllerDao;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/bilibili")
public class BIliBiliVideoController {

    @Value("${bilibiliApiUrl}")
    private String bilibiliApiUrl;

    @Value("${filePath}")
    private String filePath;


    /**
     * 根据avId请求B站接口获取评论数据，并存入文件，高并发下崩溃率很高
     *
     * @param avId 视频的id号
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "采集评论信息", notes = "根据视频av号采集评论")
    @ApiImplicitParam(name = "avId", value = "待采集视频id", required = true, dataTypeClass = String.class)
    @GetMapping("/get-comment")
    public Object getComments(@RequestParam(name = "avId") String avId) throws Exception {
        int times;
        String requestUrl = bilibiliApiUrl + "?oid=" + avId + "&type=1&pn=";
        String requestData = null;
        BilibiliControllerDao bilibiliControllerDao = new BilibiliControllerDao();

        BilibiliJsonUtil.createFileDelete(avId);
        requestData = HttpUtil.doGet(requestUrl + "1");
        BilibiliJsonUtil.writeToFile(BilibiliJsonUtil.jsonParse(requestData));
        if (BilibiliJsonUtil.size <= 0) {
            bilibiliControllerDao.setCode(500);
            bilibiliControllerDao.setMessage("当前视频没有评论");
            return bilibiliControllerDao;
        }
        times = BilibiliJsonUtil.count / BilibiliJsonUtil.size + 1;
        for (int i = 2; i <= times - 1; i++) {
            //TODO 问题记录,循环中执行http请求,似乎不是阻塞执行，待验证
//            Thread.sleep(100);
            requestData = HttpUtil.doGet(requestUrl + i);
            BilibiliJsonUtil.writeToFile(BilibiliJsonUtil.jsonParse(requestData));
        }

        Thread.sleep(400);
        bilibiliControllerDao.setCode(200);
        bilibiliControllerDao.setMessage("/info/" + avId + ".txt");
        return bilibiliControllerDao;
    }
}
