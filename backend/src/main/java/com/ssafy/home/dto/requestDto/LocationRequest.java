package com.ssafy.home.dto.requestDto;

import com.ssafy.home.dto.Point;
import lombok.*;


@Data
public class LocationRequest {

   private Point start;
   private Point end;

}
