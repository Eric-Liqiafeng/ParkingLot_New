package com.thoughtworks.tdd;

import com.thoughtworks.exception.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;

public class ParkingBoyTest {
    @Test
    public void should_return_car_when_park_car_to_parking_lot_then_get_it_back() throws Exception {
        //given
        ParkingLot firstParkingLot = new ParkingLot(5);
        ParkingLot[] parkingLotArray = new ParkingLot[]{firstParkingLot};
        Car car = new Car();
        ParkingLots parkingLots = new ParkingLots(parkingLotArray);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots, "EL0315");

        //when
        Ticket ticket = parkingBoy.park(car).getTicket();
        Car fetchedCar = parkingBoy.fetch(ticket).getCar();

        //then
        assertSame(car, fetchedCar);
    }

    @Test
    public void should_multiple_cars_when_use_correspond_ticket() throws Exception {
        //given
        Car firstCar = new Car();
        Car secondCar = new Car();
        ParkingLot firstParkingLot = new ParkingLot(5);
        ParkingLot[] parkingLotArray = new ParkingLot[]{firstParkingLot};
        ParkingLots parkingLots = new ParkingLots(parkingLotArray);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots, "EL0315");

        //when
        Ticket firstTicket = parkingBoy.park(firstCar).getTicket();
        Ticket secondTicket = parkingBoy.park(secondCar).getTicket();
        Car fetchedFirstCar = parkingBoy.fetch(firstTicket).getCar();
        Car fetchedSecondCar = parkingBoy.fetch(secondTicket).getCar();

        //then
        assertSame(firstCar, fetchedFirstCar);
        assertSame(secondCar, fetchedSecondCar);
    }
//
    @Test
    public void should_not_fetch_car_when_ticket_is_wrong() throws Exception {
        ParkingLot firstParkingLot = new ParkingLot(5);
        ParkingLot[] parkingLotArray = new ParkingLot[]{firstParkingLot};
        Car car = new Car();
        ParkingLots parkingLots = new ParkingLots(parkingLotArray);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots, "EL0315");
        Ticket wrongTicket = new Ticket(0);

        //when
        parkingBoy.park(car);

        Assertions.assertThrows(WrongTicketException.class,()->parkingBoy.fetch(wrongTicket));
    }

    @Test
    public void should_not_fetch_when_ticket_has_been_used() throws Exception {
        //given
        ParkingLot firstParkingLot = new ParkingLot(5);
        ParkingLot[] parkingLotArray = new ParkingLot[]{firstParkingLot};
        Car car = new Car();
        ParkingLots parkingLots = new ParkingLots(parkingLotArray);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots, "EL0315");

        //when
        Ticket ticket = parkingBoy.park(car).getTicket();
        parkingBoy.fetch(ticket);

        Assertions.assertThrows(WrongTicketException.class,()->parkingBoy.fetch(ticket));
    }

    @Test
    public void should_not_park_car_when_parking_lot_capacity_is_full() throws Exception {
        ParkingLot firstParkingLot = new ParkingLot(5);
        ParkingLot[] parkingLotArray = new ParkingLot[]{firstParkingLot};
        ParkingLots parkingLots = new ParkingLots(parkingLotArray);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots, "EL0315");
        for(int i = 0; i < 5; i ++) {
            Car car = new Car();
            parkingBoy.park(car);
        }
        Car overflowCar = new Car();

        Exception ParkingLotNotPositionException = Assertions.assertThrows(ParkingLotNotPositionException.class,()->parkingBoy.park(overflowCar));
        assertSame("Not enough position.", ParkingLotNotPositionException.getMessage());
    }

    @Test
    public void should_not_park_car_when_park_a_parked_car() throws Exception {
        //given
        ParkingLot firstParkingLot = new ParkingLot(5);
        ParkingLot[] parkingLotArray = new ParkingLot[]{firstParkingLot};
        Car car = new Car();
        ParkingLots parkingLots = new ParkingLots(parkingLotArray);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots, "EL0315");

        //when
        parkingBoy.park(car);

        Exception parkErrorCarException = Assertions.assertThrows(ParkErrorCarException.class,()->parkingBoy.park(car));
        assertSame("Can not park a parked car or park a null car.", parkErrorCarException.getMessage());
    }

    @Test
    public void should_not_park_car_when_park_a_null_car() throws Exception{
        ParkingLot firstParkingLot = new ParkingLot(5);
        ParkingLot[] parkingLotArray = new ParkingLot[]{firstParkingLot};
        ParkingLots parkingLots = new ParkingLots(parkingLotArray);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots, "EL0315");

        Exception parkErrorCarException = Assertions.assertThrows(ParkErrorCarException.class,()->parkingBoy.park(null));
        assertSame("Can not park a parked car or park a null car.", parkErrorCarException.getMessage());
    }

    @Test
    public void should_return_error_message_when_ticket_is_wrong() throws Exception {
        ParkingLot firstParkingLot = new ParkingLot(5);
        ParkingLot[] parkingLotArray = new ParkingLot[]{firstParkingLot};
        Car car = new Car();
        ParkingLots parkingLots = new ParkingLots(parkingLotArray);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots, "EL0315");

        Ticket ticket = new Ticket(0);
        parkingBoy.park(car);

        Exception wrongTicketException = Assertions.assertThrows(WrongTicketException.class,()->parkingBoy.fetch(ticket));
        assertSame("Unrecognized parking ticket.", wrongTicketException.getMessage());
    }

    @Test
    public void should_return_error_message_when_ticket_has_been_used() throws Exception{
        ParkingLot firstParkingLot = new ParkingLot(5);
        ParkingLot[] parkingLotArray = new ParkingLot[]{firstParkingLot};
        Car car = new Car();
        ParkingLots parkingLots = new ParkingLots(parkingLotArray);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots, "EL0315");

        Ticket ticket = parkingBoy.park(car).getTicket();
        parkingBoy.fetch(ticket);

        Exception wrongTicketException = Assertions.assertThrows(WrongTicketException.class,()->parkingBoy.fetch(ticket));
        assertSame("Unrecognized parking ticket.", wrongTicketException.getMessage());
    }

    @Test
    public void should_return_error_message_when_not_provide_ticket() throws Exception{
        ParkingLot firstParkingLot = new ParkingLot(5);
        ParkingLot[] parkingLotArray = new ParkingLot[]{firstParkingLot};
        Car car = new Car();
        ParkingLots parkingLots = new ParkingLots(parkingLotArray);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots, "EL0315");

        parkingBoy.park(car);

        Exception nullTicketException = Assertions.assertThrows(NullTicketException.class,()->parkingBoy.fetch(null));
        assertSame("Please provide your parking ticket.", nullTicketException.getMessage());
    }

    @Test
    public void should_return_error_message_when_park_car_into_parking_lot_without_position() throws Exception{
        ParkingLot firstParkingLot = new ParkingLot(5);
        ParkingLot[] parkingLotArray = new ParkingLot[]{firstParkingLot};
        ParkingLots parkingLots = new ParkingLots(parkingLotArray);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots, "EL0315");
        for(int i = 0; i < 5; i ++) {
            Car car = new Car();
            parkingBoy.park(car);
        }
        Car overflowCar = new Car();

        Exception ParkingLotNotPositionException = Assertions.assertThrows(ParkingLotNotPositionException.class,()->parkingBoy.park(overflowCar));
        assertSame("Not enough position.", ParkingLotNotPositionException.getMessage());
    }

    @Test
    public void should_return_the_second_parking_lot_when_the_first_parking_lot_is_full() throws Exception{
        ParkingLot firstParkingLot = new ParkingLot(5);
        ParkingLot secondParkingLot = new ParkingLot(8);
        ParkingLot thirdParkingLot = new ParkingLot(10);
        ParkingLot[] parkingLotArray = new ParkingLot[]{firstParkingLot, secondParkingLot, thirdParkingLot};
        ParkingLots parkingLots = new ParkingLots(parkingLotArray);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLots, "EL0315");

        for(int i = 0; i < 5; i ++) {
            Car car = new Car();
            parkingBoy.park(car);
        }

        Car overflowCar = new Car();
        Ticket ticket = parkingBoy.park(overflowCar).getTicket();
        assertSame(1, ticket.getParkingLotId());
    }
}
