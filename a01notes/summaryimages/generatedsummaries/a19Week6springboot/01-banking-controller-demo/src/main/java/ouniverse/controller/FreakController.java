package ouniverse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ouniverse.kt.Mobile;

@RestController
public class FreakController {




     private Mobile mobile;


     @Autowired
     public void sethorrible(Mobile m)
     {
         System.out.println("mobile object wired inside FreakController");
         mobile = m;
     }



    public FreakController()
    {
        System.out.println("FreakController object created");
    }


   // one day if somebody makes  rqwuest in get mode using the uri whenindoubt then this function will get called

    @GetMapping("/whenindoubtpoc")
    public String scrap()
    {
        System.out.println("entertainment started");
        mobile.call();
        return "Junk";

    }
}
