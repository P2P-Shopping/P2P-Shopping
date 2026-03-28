package p2ps.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/routing")
public class RoutingController {

    @PostMapping("/calculate")
    public RoutingResponse calculateRoute(@RequestBody RoutingRequest request) {
        List<RoutePoint> mockRoute = new ArrayList<>();

        mockRoute.add(new RoutePoint("user_loc", "Punctul Albastru (Tu)", 47.151726, 27.587914));
        mockRoute.add(new RoutePoint("item_101", "Lapte", 47.151800, 27.588000));
        mockRoute.add(new RoutePoint("item_102", "Pâine", 47.151850, 27.588150));
        mockRoute.add(new RoutePoint("item_103", "Mere", 47.151900, 27.587950));

        return new RoutingResponse("success", mockRoute);
    }
}

class RoutingRequest {
    public double userLat;
    public double userLng;
    public List<String> productIds;
}

class RoutingResponse {
    public String status;
    public List<RoutePoint> route;

    public RoutingResponse(String status, List<RoutePoint> route) {
        this.status = status;
        this.route = route;
    }
}

class RoutePoint {
    public String itemId;
    public String name;
    public double lat;
    public double lng;

    public RoutePoint(String itemId, String name, double lat, double lng) {
        this.itemId = itemId;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }
}