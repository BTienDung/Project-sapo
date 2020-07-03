package com.example.demo;

import com.example.demo.controller.VariantDetailController;
import com.example.demo.model.Product;
import com.example.demo.model.Variant;
import com.example.demo.model.VariantDetail;
import com.example.demo.service.VariantDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(VariantDetailController.class)
public class VariantDetailRestTest {
    @MockBean
    private VariantDetailService variantDetailService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void givenVariantDetail_thenCreateOption() throws Exception {
        Variant variantA = new Variant();
        variantA.setId((long) 1);
        variantA.setProduct(new Product());
        variantA.setVariantName("Name A");

        Variant variantB = new Variant();
        variantB.setId((long) 1);
        variantB.setProduct(new Product());
        variantB.setVariantName("Name B");

        VariantDetail variantDetail = new VariantDetail();
        variantDetail.setVariantDetailName("Name A");
        variantDetail.setId((long) 1);
        variantDetail.setVariant(variantA);

        VariantDetail diffVariantDetail = new VariantDetail();
        variantDetail.setVariantDetailName("Name B");
        variantDetail.setId((long) 2);
        variantDetail.setVariant(variantB);

        given(variantDetailService.countAllByVariantId(Mockito.anyLong())).willReturn((long) 1);
        given(variantDetailService.findAllByVariantId(Mockito.anyLong())).willReturn(Collections.singletonList(variantDetail));
        given(variantDetailService.findByVariantDetailNameAndVariantId(Mockito.anyString(), Mockito.anyLong())).willReturn(Optional.of(diffVariantDetail));
        mockMvc.perform(post("/api/variantdetail")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(variantA)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.variantdetailname", is(variantA.getVariantName())));
        verify(variantDetailService, VerificationModeFactory.times(1)).save(Mockito.any());
        reset(variantDetailService);
    }

    @Test
    public void givenInvalidVariantDetail_thenCreateOption() throws Exception {
        Variant variantA = new Variant();
        variantA.setId((long) 1);
        variantA.setProduct(new Product());

        mockMvc.perform(post("/api/variantdetail")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(variantA)))
                .andExpect(status().isBadRequest());
        verify(variantDetailService, VerificationModeFactory.times(0)).save(Mockito.any());
        reset(variantDetailService);
    }

    @Test
    public void givenVariantDetailNameExisted_thenCreateOption() throws Exception {
        Variant variantA = new Variant();
        variantA.setId((long) 1);
        variantA.setProduct(new Product());
        variantA.setVariantName("Name A");

        Variant variantB = new Variant();
        variantB.setId((long) 1);
        variantB.setProduct(new Product());
        variantB.setVariantName("Name B");

        VariantDetail variantDetail = new VariantDetail();
        variantDetail.setVariantDetailName("Name A");
        variantDetail.setId((long) 1);
        variantDetail.setVariant(variantA);

        given(variantDetailService.countAllByVariantId(Mockito.anyLong())).willReturn((long) 1);
        given(variantDetailService.findAllByVariantId(Mockito.anyLong())).willReturn(Collections.singletonList(variantDetail));
        given(variantDetailService.findByVariantDetailNameAndVariantId(Mockito.anyString(), Mockito.anyLong())).willReturn(Optional.of(variantDetail));
        mockMvc.perform(post("/api/variantdetail")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(variantA)))
                .andExpect(status().is4xxClientError());
        verify(variantDetailService, VerificationModeFactory.times(0)).save(Mockito.any());
        reset(variantDetailService);
    }

    @Test
    public void givenVariantDetail_whenVariantDetailFindInDBIsNull_thenCreateOption() throws Exception {
        Variant variantA = new Variant();
        variantA.setId((long) 1);
        variantA.setProduct(new Product());
        variantA.setVariantName("Name A");

        Variant variantB = new Variant();
        variantB.setId((long) 1);
        variantB.setProduct(new Product());
        variantB.setVariantName("Name B");

        VariantDetail variantDetail = new VariantDetail();
        variantDetail.setVariantDetailName("Name A");
        variantDetail.setId((long) 1);
        variantDetail.setVariant(variantA);

        given(variantDetailService.countAllByVariantId(Mockito.anyLong())).willReturn((long) 1);
        given(variantDetailService.findAllByVariantId(Mockito.anyLong())).willReturn(Collections.singletonList(variantDetail));
        given(variantDetailService.findByVariantDetailNameAndVariantId(Mockito.anyString(), Mockito.anyLong())).willReturn(Optional.empty());
        mockMvc.perform(post("/api/variantdetail")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(variantA)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.variantdetailname", is(variantA.getVariantName())));
        verify(variantDetailService, VerificationModeFactory.times(1)).save(Mockito.any());
        reset(variantDetailService);
    }
}
