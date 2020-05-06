package com.barrage.webcontroller.utils;

import com.barrage.api.entity.BilibiliCommentEntity;
import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BilibiliJsonUtil {

    public static int size = 0;

    public static int count = 0;

    private static long timePre = 10800000L;

    private static String filePath = "D:/bilibiliComment";

    @SuppressWarnings("unchecked")
    public static List<Map<String, String>> jsonParse(String jsonString) throws IOException {
        Gson gson = new Gson();
        List<Map<String, String>> returnData = new ArrayList<>(32);
        Map<String, String> item;
        BilibiliCommentEntity bilibiliCommentEntity = null;

        bilibiliCommentEntity = gson.getAdapter(BilibiliCommentEntity.class).fromJson(jsonString);
        if (bilibiliCommentEntity == null) {
            return null;
        }
        Map<String, Object> data = (Map<String, Object>) bilibiliCommentEntity.getData();
        if (data == null) {
            return null;
        }
        List<Map<String, Object>> replies = (List<Map<String, Object>>) bilibiliCommentEntity.getData().get("replies");
        Map<String, Double> pageInfo = (Map<String, Double>) bilibiliCommentEntity.getData().get("page");
        size = pageInfo.get("size").intValue();
        count = pageInfo.get("count").intValue();
        int size = replies.size();
        for (int i = 0; i < size; i++) {
            item = new HashMap<>();
            item.put("rpid", String.valueOf(((Double) replies.get(i).get("rpid")).longValue()));
            item.put("oid", String.valueOf(((Double) replies.get(i).get("oid")).longValue()));
            item.put("mid", String.valueOf(((Double) replies.get(i).get("mid")).longValue()));
            item.put("uname", ((Map<String, String>) replies.get(i).get("member")).get("uname"));
            item.put("sex", ((Map<String, String>) replies.get(i).get("member")).get("sex"));
            item.put("message", ((Map<String, String>) replies.get(i).get("content")).get("message"));
            returnData.add(item);
        }
        return returnData;
    }

    private static String DoubleString2LongString(String original) {
        int size = original.length();
        char[] buffer = new char[size];
        char temp;
        int flag = 0;
        for (int i = 0; i < size; i++) {
            if ((temp = original.charAt(i)) == '.') {
//                i++;
                flag = 1;
                continue;
            }
            if (temp == 'E') {
                break;
            }
            buffer[i - flag] = temp;
        }

        return new String(buffer);
    }

    public static void writeToFile(List<Map<String, String>> data) throws IOException {
        if (data == null) {
            return;
        }
        Gson gson = new Gson();
        String fileNameIndex = data.get(0).get("oid");
//        String filePath = "/usr/bilibiliComment";
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(filePath + "/" + fileNameIndex + ".txt");
        FileOutputStream fileOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;

        fileOutputStream = new FileOutputStream(file, true);
        bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        bufferedOutputStream.write(gson.toJson(data).getBytes());

        if (bufferedOutputStream != null) {
            bufferedOutputStream.close();
        }
        if (fileOutputStream != null) {
            fileOutputStream.close();
        }
    }

    public static void createFileDelete(String fileIndex) throws IOException {
        Gson gson = new Gson();
//        String filePath = "D:/bilibiliComment";
//        String filePath = "/usr/bilibiliComment";
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(filePath + "/" + fileIndex + ".txt");
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
    }

    public static boolean isFileValid(String avId) throws Exception {
        long currentTime = System.currentTimeMillis();

        File file = new File(filePath + "/" + avId + ".txt");
        if (file.exists()) {
            return (currentTime - file.lastModified() < timePre);
        }
        return false;
    }

    public static String getFilePath() {
        return filePath;
    }

    public static void setFilePath(String filePath) {
        BilibiliJsonUtil.filePath = filePath;
    }
}
