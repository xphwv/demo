package com.xphwv.design.chain;
public class Client {  
    public static void main(String[] args){  
        Handler handler1 = new ConcreteHandler1();  
        Handler handler2 = new ConcreteHandler2();  
        Handler handler3 = new ConcreteHandler3();  
  
        handler1.setNextHander(handler2);  
        handler2.setNextHander(handler3);  
          
        Response response = handler1.handlerRequest(new Request(new Level(25)));  
    }  
}  