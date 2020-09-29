package com.swordmaster.excalibur.controller;

import org.junit.jupiter.api.Test;
import com.swordmaster.excalibur.vo.DrowsniessVO;
import static org.junit.jupiter.api.Assertions.*;

public class DrowsinessControllerTest {

    @Test
    public void getDrowsniess() {
        DrowsniessVO vo = DrowsniessVO.builder()
                .id(100L)
                .rate(50.16)
                .build();

        Long id = 100L;

        assertEquals(id, vo.getId());

    }
}
