package com.example.demo;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.example.demo.entities.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class EntityUnitTest {

    /** TODO
     * Implement tests for each Entity class: Doctor, Patient, Room and Appointment.
     * Make sure you are as exhaustive as possible. Coverage is checked ;)
     *
     * En esta clase que corresponde al apartado 2 que se nos solicita testear las
     * entidades. Intuyo que se refiere a testear a los métodos correspondiente
     * de cada clase.
     */

	@Autowired
	private TestEntityManager entityManager;

	private Doctor d1;

	private Patient p1;

    private Room r1;

    private Appointment a1;
    private Appointment a2;
    private Appointment a3;

    private LocalDateTime startsAt;

    private LocalDateTime finishesAt;

    private DateTimeFormatter formatter;


    @BeforeEach
    void setUp(){
        d1=new Doctor("Perla","Amalia",24,"p.amalia@hospital.accwe");
        p1=new Patient("Jose Luis", "Olaya", 37, "j.olaya@email.com");
        r1 = new Room("Dermatology");
        formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        startsAt= LocalDateTime.parse("19:30 24/04/2023", formatter);
        finishesAt=LocalDateTime.parse("20:30 24/04/2023", formatter);

    }

    @Test
    void testGetterAndSetterPerson(){
        Person person=new Person();
        person.setFirstName("Perla");
        person.setLastName("Amalia");
        person.setAge(24);
        person.setEmail("p.amalia@hospital.accwe");
        assertEquals("Perla",person.getFirstName());
        assertEquals("Amalia",person.getLastName());
        assertEquals(24,person.getAge());
        assertEquals("p.amalia@hospital.accwe",person.getEmail());
    }

    @Test
    void testConstructorPerson(){
        Person person=new Person("Perla","Amalia",24,"p.amalia@hospital.accwe");
        assertEquals("Perla",person.getFirstName());
        assertEquals("Amalia",person.getLastName());
        assertEquals(24,person.getAge());
        assertEquals("p.amalia@hospital.accwe",person.getEmail());
    }

    @Test
    void testGetterAndSettersDoctor(){
        Doctor doctor=new Doctor();
        doctor.setId(1L);
        assertEquals(1L,doctor.getId());
    }

    @Test
    void testConstructorDoctor(){
        Doctor doctor=new Doctor("Perla","Amalia",24,"p.amalia@hospital.accwe");
        assertEquals("Perla",doctor.getFirstName());
        assertEquals("Amalia",doctor.getLastName());
        assertEquals(24,doctor.getAge());
        assertEquals("p.amalia@hospital.accwe",doctor.getEmail());
    }

    @Test
    void testGetterAndConstructorRoom(){
        Room room=new Room("Dermatology");
        assertEquals("Dermatology",room.getRoomName());
    }

    @Test
    void testGetterAndSetterPatient(){
        Patient patient=new Patient();
        patient.setId(1L);
        assertEquals(1L,patient.getId());
    }

    @Test
    void testGetterAndSetterAppointment(){
        a1=new Appointment();
        a1.setPatient(p1);
        a1.setDoctor(d1);
        a1.setRoom(r1);
        a1.setStartsAt(startsAt);
        a1.setFinishesAt(finishesAt);
        assertEquals(p1,a1.getPatient());
        assertEquals(d1,a1.getDoctor());
        assertEquals(r1,a1.getRoom());
        assertEquals(startsAt,a1.getStartsAt());
        assertEquals(finishesAt,a1.getFinishesAt());
    }

    @Test
    void testAppointmentConstructor(){
        a1=new Appointment(p1,d1,r1,startsAt,finishesAt);
        assertEquals(p1,a1.getPatient());
        assertEquals(d1,a1.getDoctor());
        assertEquals(r1,a1.getRoom());
        assertEquals(startsAt,a1.getStartsAt());
        assertEquals(finishesAt,a1.getFinishesAt());
    }

    /**
     * Método encargado de testar si se superponen las citas con el caso 1.
     * */
    @Test
    void shouldOverlapsAppointment(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

        LocalDateTime startsAt= LocalDateTime.parse("19:30 24/04/2023", formatter);
        LocalDateTime finishesAt = LocalDateTime.parse("20:30 24/04/2023", formatter);

        LocalDateTime startsAt2= LocalDateTime.parse("19:30 24/04/2023", formatter);
        LocalDateTime finishesAt2 = LocalDateTime.parse("20:30 24/04/2023", formatter);

        a1=new Appointment(p1,d1,r1,startsAt,finishesAt);
        a2=new Appointment(p1,d1,r1,startsAt2,finishesAt2);

        //Testeamos si devuelve true cuando ambas citas comienzan a la misma hora
        //Case 1: A.starts == B.starts
        assertEquals(true,a1.overlaps(a2));

    }

    /**
     * Método encargado de testar si se superponen las citas con el caso 2.
     * */
    @Test
    void shouldOverlapsAppointment2(){
        p1=new Patient("Jose Luis", "Olaya", 37, "j.olaya@email.com");
        d1=new Doctor("Perla","Amalia",24,"p.amalia@hospital.accwe");
        r1 = new Room("Dermatology");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

        LocalDateTime startsAt= LocalDateTime.parse("19:30 24/04/2023", formatter);
        LocalDateTime finishesAt = LocalDateTime.parse("20:30 24/04/2023", formatter);

        LocalDateTime startsAt2= LocalDateTime.parse("19:30 24/04/2023", formatter);
        LocalDateTime finishesAt2 = LocalDateTime.parse("20:30 24/04/2023", formatter);

        a1=new Appointment(p1,d1,r1,startsAt,finishesAt);
        a2=new Appointment(p1,d1,r1,startsAt2,finishesAt2);

        //Testeamos si devuelve true cuando ambas citas comienzan a la misma hora
        //Case 1: A.finishes == B.finishes
        assertEquals(true,a1.overlaps(a2));

    }

    @Test
    void shouldOverlapsAppointment3(){
        p1=new Patient("Jose Luis", "Olaya", 37, "j.olaya@email.com");
        d1=new Doctor("Perla","Amalia",24,"p.amalia@hospital.accwe");
        r1 = new Room("Dermatology");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

        LocalDateTime startsAt= LocalDateTime.parse("19:35 24/04/2023", formatter);
        LocalDateTime finishesAt = LocalDateTime.parse("22:30 24/04/2023", formatter);

        LocalDateTime startsAt2= LocalDateTime.parse("19:30 24/04/2023", formatter);
        LocalDateTime finishesAt2 = LocalDateTime.parse("21:30 24/04/2023", formatter);

        a1=new Appointment(p1,d1,r1,startsAt,finishesAt);
        a2=new Appointment(p1,d1,r1,startsAt2,finishesAt2);

        //Testeamos si devuelve true con el caso 3
        //Case 3: A.starts < B.finishes && B.finishes < A.finishes
        assertEquals(true,a1.overlaps(a2));

    }

    @Test
    void shouldOverlapsAppointment4(){
        p1=new Patient("Jose Luis", "Olaya", 37, "j.olaya@email.com");
        d1=new Doctor("Perla","Amalia",24,"p.amalia@hospital.accwe");
        r1 = new Room("Dermatology");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

        LocalDateTime startsAt= LocalDateTime.parse("19:35 24/04/2023", formatter);
        LocalDateTime finishesAt = LocalDateTime.parse("22:30 24/04/2023", formatter);

        LocalDateTime startsAt2= LocalDateTime.parse("19:30 24/04/2023", formatter);
        LocalDateTime finishesAt2 = LocalDateTime.parse("23:30 24/04/2023", formatter);

        a1=new Appointment(p1,d1,r1,startsAt,finishesAt);
        a2=new Appointment(p1,d1,r1,startsAt2,finishesAt2);

        //Testeamos si devuelve false con el caso 4
        //Case 4: B.starts < A.starts && A.finishes < B.finishes
        assertEquals(false,a1.overlaps(a2));

    }
}
