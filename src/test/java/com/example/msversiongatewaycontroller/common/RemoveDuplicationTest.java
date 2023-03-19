package com.example.msversiongatewaycontroller.common;

import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

class RemoveDuplicationTest {

    @Test
    void mergePrefix() {
        RemoveDuplication removeDuplication = new RemoveDuplication();
        String paths = "/carts/{version:v[0-9]+\\.[0-9]+\\.[0-9]+}/carts/{customerId}, /carts/{version:v[0-9]+\\.[0-9]" +
                "+\\.[0-9]+}/carts/{customerId}/merge, /carts/{version:v[0-9]+\\.[0-9]+\\.[0-9]+}/carts/{customerId}/" +
                "items/{itemId}, /carts/{version:v[0-9]+\\.[0-9]+\\.[0-9]+}/carts/{customerId}/items, " +
                "/carts/{version:v[0-9]+\\.[0-9]+\\.[0-9]+}/**";
        String res = removeDuplication.mergePrefix(paths);
        System.out.println(res);
    }
}