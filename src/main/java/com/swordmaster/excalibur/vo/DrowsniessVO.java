package com.swordmaster.excalibur.vo;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
// TODO: 아직 스키마 설계 이전에 더미로 만드는 VO 이므로 추후 반드시 수정 필요
public class DrowsniessVO {
    private Long id;
    private Double rate;

}
