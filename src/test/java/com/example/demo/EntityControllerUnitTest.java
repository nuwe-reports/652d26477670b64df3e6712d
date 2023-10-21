
package com.example.demo;

import static org.mockito.ArgumentMatchers.any;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import java.time.LocalDateTime;
import java.time.format.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.demo.controllers.*;
import com.example.demo.repositories.*;
import com.example.demo.entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;


/** TODO
 * Implement all the unit test in its corresponding class.
 * Make sure to be as exhaustive as possible. Coverage is checked ;)
 *
 *
 */

@WebMvcTest(DoctorController.class)
class DoctorControllerUnitTest{

    @MockBean
    private DoctorRepository doctorRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void this_is_a_test(){
        // DELETE ME
        assertThat(true).isEqualTo(false);
    }
}


@WebMvcTest(PatientController.class)
class PatientControllerUnitTest{

    @MockBean
    private PatientRepository patientRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void this_is_a_test(){
        // DELETE ME
        assertThat(true).isEqualTo(false);
    }

}

@WebMvcTest(RoomController.class)
class RoomControllerUnitTest{

    @MockBean
    private RoomRepository roomRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void shouldGetNoRooms(){

        List<Room> listsRooms=new ArrayList<>();
        when(roomRepository.findAll()).thenReturn(listsRooms);

        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/rooms")).andExpect(status().isNoContent());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shouldGetRoomByRoomName(){

        Optional<Room> room= Optional.of(new Room("Dermatology"));

        when(roomRepository.findByRoomName("Dermatology")).thenReturn(room);
        try {
            if(room.isPresent()){
                mockMvc.perform(MockMvcRequestBuilders.get("/api/rooms/".concat(room.get().getRoomName()))).andExpect(status().isOk());
            }else{
                mockMvc.perform(MockMvcRequestBuilders.get("/api/rooms/".concat(room.get().getRoomName()))).andExpect(status().isNotFound());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shouldCreateRoom() throws Exception {

        Room room=new Room("Dermatology");
        when(roomRepository.save(any())).thenReturn(room);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/room").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(room))).andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    void shouldGetAllRooms(){

    }

    @Test
    void shouldDeleteRoomByName(){
        List<Room> listRoom=roomRepository.findAll();
        Optional<Room> roomToDelete=roomRepository.findByRoomName("Dermatology");
        //doThrow(IllegalAccessError.class).when(roomRepository).deleteByRoomName(roomToDelete.get().getRoomName());

        System.out.println("Lista de rooms" +listRoom.size());
    }
}
