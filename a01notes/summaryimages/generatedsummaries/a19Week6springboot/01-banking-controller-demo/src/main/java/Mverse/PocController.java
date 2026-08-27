package mverse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PocController {

    public PocController()
    {
        System.out.println("PocController object created");
    }

    @GetMapping("/freak123")
    public String freak()
    {

        return "freak functiona at work";
    }
}
