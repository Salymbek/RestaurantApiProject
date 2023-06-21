package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.response.*;
import peaksoft.exception.NotFoundException;
import peaksoft.model.Cheque;
import peaksoft.model.MenuItem;
import peaksoft.model.User;
import peaksoft.repository.ChequeRepository;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.ChequeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class ChequeServiceImpl implements ChequeService {

    private final ChequeRepository chequeRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;

    public ChequeServiceImpl(ChequeRepository chequeRepository, UserRepository userRepository, MenuItemRepository menuItemRepository) {
        this.chequeRepository = chequeRepository;
        this.userRepository = userRepository;
        this.menuItemRepository = menuItemRepository;
    }



    @Override
    public SimpleResponse save(Long userId, ChequeRequest request) {
        List<MenuItem> menuItems = new ArrayList<>();
        Cheque cheque = new Cheque();
        cheque.setCreatedAt(LocalDate.now());
        for (Long foodId : request.foodsId()) {
            MenuItem menuItem = menuItemRepository.findById(foodId)
                    .orElseThrow(() -> new NotFoundException(String.format("Meal with id - %s is not found!", foodId)));
            menuItems.add(menuItem);
        }
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(String.format("User with id - %S not found!",userId)));
        cheque.setUser(user);
        for (MenuItem menuItem : menuItems) {
            menuItem.getCheques().add(cheque);
        }
        chequeRepository.save(cheque);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Your cheque has been accepted!")
                .build();
    }

    @Override
    public List<ChequeResponse> findAll(Long userId) {
        List<ChequeResponse> cheques = new ArrayList<>();
        for (ChequeResponse ch : chequeRepository.findAllChequeByUserId(userId)) {
            BigDecimal total = ch.getAveragePrice().multiply(new BigDecimal(ch.getService())).divide(new BigDecimal(100)).add(ch.getAveragePrice());
            ch.setGrandTotal(total);
            ch.setMeals(chequeRepository.getFoods(ch.getId()));
            cheques.add(ch);
        }
        return cheques;
    }

    @Override
    public SimpleResponse update(Long id, ChequeRequest request) {
        List<MenuItem> menuItems;
        Cheque cheque = chequeRepository.findById(id).orElseThrow(() -> new NotFoundException("This id:" + id + " does not exist!!"));

        menuItems = request.foodsId().stream().map(foodId -> menuItemRepository.findById(foodId)
                .orElseThrow(() -> new NotFoundException("This id:" + id + " does not exist!!"))).collect(Collectors.toList());
        for (MenuItem menuItem : cheque.getMenuItems()) {
            menuItem.getCheques().remove(cheque);
        }
        for (MenuItem menuItem : menuItems) {
            menuItem.getCheques().add(cheque);
        }

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Cheque with id: %s successfully updated", id))
                .build();
    }


    @Override
    public SimpleResponse delete(Long id) {
        if (!chequeRepository.existsById(id)) {
            return SimpleResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message(String.format("Cheque with id: %s not found", id))
                    .build();
        }

        Cheque cheque = chequeRepository.findById(id).orElseThrow(()->
                new NotFoundException("Cheque with id - " + id +" doesn't exists!"));
        cheque.getMenuItems().forEach(menuItem -> menuItem.getCheques().remove(cheque));
        chequeRepository.deleteById(id);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Cheque with id: %s successfully deleted", id))
                .build();
    }

    @Override
    public AllChequesSum totalSum(Long userId, LocalDate date) {

        List<Cheque> cheques = chequeRepository.findAll();
        AllChequesSum allChequesSum = new AllChequesSum();
        Long count = 0L;
        BigDecimal totalSum = new BigDecimal(0);
        int ser = 1;
        for (Cheque cheque : cheques) {
            if (cheque.getUser().getId().equals(userId) && cheque.getCreatedAt().equals(date)){
                allChequesSum.setWaiter(cheque.getUser().getFirstName()+" "+cheque.getUser().getLastName());
                allChequesSum.setDate(cheque.getCreatedAt());
                count++;
                for (MenuItem menuItem : cheque.getMenuItems()) {
                    totalSum = new BigDecimal(totalSum.intValue() + menuItem.getPrice().intValue());
                    ser = menuItem.getRestaurant().getService();
                }
            }

        }
        BigDecimal service = totalSum.multiply(new BigDecimal(ser)).divide(new BigDecimal(100));
        allChequesSum.setCounterOfCheque(count);
        allChequesSum.setTotalSumma(totalSum.add(service));
        return allChequesSum;

    }


    @Override
    public SimpleResponse avg(LocalDate date) {
        int total = 0;
        int count = 0;
        int ser = 1;
        for (Cheque cheque : chequeRepository.findAll()) {
            if (cheque.getCreatedAt().equals(date)) {
                for (MenuItem menuItem : cheque.getMenuItems()) {
                    total += menuItem.getPrice().intValue();
                    ser = menuItem.getRestaurant().getService();
                }
                count++;
            }
        }
        total += total * ser / 100;
        int avg = total / count;
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Average check total : %s",avg))
                .build();
    }

}