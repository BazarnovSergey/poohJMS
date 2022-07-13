package ru.job4j.pooh;

public interface Service {

    String GET = "GET";
    String POST = "POST";

    Resp process(Req req);

}