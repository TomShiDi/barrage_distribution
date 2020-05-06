//package com.barrage.webcontroller.controller;
//
//import barrage.demo.dao.CommonDto;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @Author TomShiDi
// * @Description
// * @Date 2020/3/18
// **/
//@RestController()
//@RequestMapping("/kafka")
//public class KafkaTestController {
//
//    private KafkaTemplate<Object, Object> kafkaTemplate;
//
//    private Logger logger = LoggerFactory.getLogger(KafkaTestController.class);
//
//    @Autowired
//    public KafkaTestController(KafkaTemplate<Object, Object> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//    @GetMapping("/send")
//    public void sendMessage(@RequestParam(name = "input") String input) {
//        logger.info("sending message is {}", input);
//        CommonDto commonDto = new CommonDto();
//        kafkaTemplate.send("topic_test", commonDto);
////        KafkaProducer<>
//    }
//
//    //    @KafkaListener(id = "consumer_1", topics = "topic_test")
//    public void consumeMessage(CommonDto input) {
//        logger.info("consume message is {}", input.toString());
//    }
//}
