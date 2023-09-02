package com.example.fintech.production.dto;

import com.example.fintech.production.type.ProductionStatus;
import lombok.*;

public class StopProduction {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Request {
        private Long productionId;

        public Request(Long productionId) {
            this.productionId = productionId;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long productionId;
        private String productionTitle;
        private ProductionStatus productionStatus;

        public static Response from(ProductionDto productionDto) {
            return Response.builder()
                    .productionId(productionDto.getProductionId())
                    .productionTitle(productionDto.getProductionTitle())
                    .productionStatus(productionDto.getProductionStatus())
                    .build();
        }
    }
}
