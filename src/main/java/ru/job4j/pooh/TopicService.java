package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {

    private final ConcurrentHashMap<String,
            ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> topics = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        Resp resp = new Resp("", "501");
        if (POST.equals(req.httpRequestType())) {
            ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> topic = topics.get(req.getSourceName());
            if (topic != null) {
                for (ConcurrentLinkedQueue<String> s : topic.values()) {
                    s.offer(req.getParam());
                }
            }
            resp = new Resp("", "200");
        } else if (GET.equals(req.httpRequestType())) {
            topics.putIfAbsent(req.getSourceName(), new ConcurrentHashMap<>());
            topics.get(req.getSourceName()).putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
            String result = topics.get(req.getSourceName()).get(req.getParam()).poll();
            if (result == null) {
                resp = new Resp("", "204");
            } else {
                resp = new Resp(result, "200");
            }
        }
        return resp;
    }

}