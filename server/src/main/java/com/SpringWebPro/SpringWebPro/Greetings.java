package com.SpringWebPro.SpringWebPro;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLOutput;
@RestController
public class Greetings
{
    @RequestMapping("/")
    public String GreetHomePage(){
        return  "This is the Greeting from the Greetings Class";
    }

    @RequestMapping("/about")
    public String About()
    {
        return "THis is about page";
    }
}
