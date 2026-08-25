package com.example.rectangle.service;

import com.example.rectangle.dto.PAResponseDto;
import com.example.rectangle.dto.RectangleRequestDto;

/**
 * Service interface for calculating Rectangle PA (Perimeter & Area).
 */
public interface RectangleService {

    PAResponseDto calculatePA(RectangleRequestDto rectangleDto);
}
