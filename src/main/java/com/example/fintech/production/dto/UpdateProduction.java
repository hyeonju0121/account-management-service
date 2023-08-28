package com.example.fintech.production.dto;

import lombok.*;

public class UpdateProduction {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {
        private String productionTitle;
        private String productionContents;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long productionId;
        private String productionTitle;
        private String productionContents;

        public static Response from(ProductionDto productionDto) {
            return Response.builder()
                    .productionId(productionDto.getProductionId())
                    .productionTitle(productionDto.getProductionTitle())
                    .productionContents(productionDto.getProductionContents())
                    .build();
        }
    }
}
