package p2ps;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Api {

    @GetMapping
    public String getAllProducts() {
        return "nothing to see here";
    }
}