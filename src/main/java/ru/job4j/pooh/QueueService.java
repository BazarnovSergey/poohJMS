package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {

    private final ConcurrentHashMap<String,
            ConcurrentLinkedQueue<String>> queues = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        Resp resp = new Resp(req.httpRequestType(), "204");
        if (POST.equals(req.httpRequestType())) {
            queues.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
            queues.get(req.getSourceName()).offer(req.getParam());
                new Resp(req.getParam(), "200");
        } else if (GET.equals(req.httpRequestType())) {
            String result = queues.getOrDefault(req.getSourceName(),
                    new ConcurrentLinkedQueue<>()).poll();
            if (result == null) {
                resp = new Resp("", "404");
            } else {
                resp = new Resp(result, "200");
            }
        }
        return resp;
    }
}