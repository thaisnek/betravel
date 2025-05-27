package com.example.travelweb.service.implement;

import com.example.travelweb.dto.response.TopCustomerResponse;
import com.example.travelweb.dto.response.TopTourResponse;
import com.example.travelweb.repository.BookingRepository;
import com.example.travelweb.repository.CheckoutRepository;
import com.example.travelweb.repository.TourRepository;
import com.example.travelweb.repository.UserRepository;
import com.example.travelweb.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private CheckoutRepository checkoutRepository;

    @Override
    public List<TopTourResponse> getTop5Tours() {
        Pageable topFive = PageRequest.of(0, 5);
        return bookingRepository.findTop5ToursByConfirmedBookings(topFive)
                .stream()
                .map(p -> {
                    TopTourResponse dto = new TopTourResponse();
                    dto.setId(p.getId());
                    dto.setName(p.getName());
                    dto.setBooked(p.getBooked());
                    dto.setAvailable(p.getAvailable());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<TopCustomerResponse> getTop5Customers() {
        Pageable topFive = PageRequest.of(0, 5);
        return bookingRepository.findTop5CustomersByConfirmedBookings(topFive)
                .stream()
                .map(p -> {
                    TopCustomerResponse dto = new TopCustomerResponse();
                    dto.setId(p.getId());
                    dto.setFullName(p.getName());
                    dto.setTotalPurchases(p.getTotalPurchases());
                    dto.setTotalAmount(p.getTotalAmount());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public long getTotalActiveTours() {
        return tourRepository.countByAvailabilityTrue();
    }

    public long getTotalBookings() {
        return bookingRepository.count();
    }

    public long getTotalUsers() {
        return userRepository.count();
    }

    public double getTotalRevenue() {
        Double revenue = checkoutRepository.sumTotalRevenue();
        return revenue != null ? revenue : 0.0;
    }
}
