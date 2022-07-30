package com.teste.parking.service;

import com.teste.parking.model.Parking;
import com.teste.parking.repository.ParkingRepository;
import com.teste.parking.service.exceptions.ParkingNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ParkingService {

    private final ParkingRepository parkingRepository;

    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    //private static Map<String, Parking> parkingMap = new HashMap();
//
//    static {
//        var id = getUUID();
//        Parking parking = new Parking(id, "ABC-1234", "SP", "Prisma", "Cinza");
//        parkingMap.put(id, parking);
////        var id1 = getUUID();
////        Parking parking1 = new Parking(id1, "DEF-9876", "BA", "VW GOL", "Azul");
////        parkingMap.put(id1, parking1);
//    }

    @Transactional(readOnly = true)
    public List<Parking> findAll() {
        //return parkingMap.values().stream().collect(Collectors.toList());
        return parkingRepository.findAll();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Parking findById(String id) {
//        Parking parking = parkingMap.get(id);
//        if (parking == null) {
//            throw new ParkingNotFoundException(id);
//        }
//        return parking;
        return parkingRepository.findById(id).orElseThrow(
                () -> new ParkingNotFoundException(id));
    }

    @Transactional
    public Parking create(Parking parkingCreate) {
//        String uuid = getUUID();
//        parkingCreate.setId(uuid);
//        parkingCreate.setEntryDate(LocalDateTime.now());
//        parkingMap.put(uuid, parkingCreate);
//        return parkingCreate;
        String uuid = getUUID();
        parkingCreate.setId(uuid);
        parkingCreate.setEntryDate(LocalDateTime.now());
        parkingRepository.save(parkingCreate);
        return parkingCreate;
    }

    @Transactional
    public void delete(String id) {
//        findById(id);
//        parkingMap.remove(id);
        findById(id);
        parkingRepository.deleteById(id);
    }

    @Transactional
    public Parking update(String id, Parking parkingCreate) {
//        Parking parking = findById(id);
//        parking.setColor(parkingCreate.getColor());
//        parkingMap.replace(id, parking);
//        return parking;
        Parking parking = findById(id);
        parking.setColor(parkingCreate.getColor());
        parking.setState(parkingCreate.getState());
        parking.setModel(parkingCreate.getModel());
        parking.setLicense(parkingCreate.getLicense());
        parkingRepository.save(parking);
        return parking;
    }

    @Transactional
    public Parking checkOut(String id) {
        Parking parking = findById(id);
        parking.setExitDate(LocalDateTime.now());
        parking.setBill(ParkingCheckOut.getBill(parking));
        return parkingRepository.save(parking);
    }

    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
