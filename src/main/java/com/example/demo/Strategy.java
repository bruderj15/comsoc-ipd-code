package com.example.demo;

public interface Strategy {

    Choice initial();

    Choice move(Choice myLastChoice, Choice oppenentLastChoice);
}
