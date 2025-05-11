package com.example.travelweb.conventer;

import com.example.travelweb.dto.request.PromotionRequest;
import com.example.travelweb.dto.response.PromotionResponse;
import com.example.travelweb.entity.Promotion;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-11T09:23:58+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class PromotionMapperImpl implements PromotionMapper {

    @Override
    public PromotionResponse toPromotionResponse(Promotion promotion) {
        if ( promotion == null ) {
            return null;
        }

        PromotionResponse.PromotionResponseBuilder promotionResponse = PromotionResponse.builder();

        promotionResponse.promotionID( promotion.getPromotionID() );
        promotionResponse.code( promotion.getCode() );
        promotionResponse.description( promotion.getDescription() );
        promotionResponse.discount( promotion.getDiscount() );
        promotionResponse.startDate( promotion.getStartDate() );
        promotionResponse.endDate( promotion.getEndDate() );
        promotionResponse.quantity( promotion.getQuantity() );

        return promotionResponse.build();
    }

    @Override
    public List<PromotionResponse> toPromotionResponseList(List<Promotion> promotions) {
        if ( promotions == null ) {
            return null;
        }

        List<PromotionResponse> list = new ArrayList<PromotionResponse>( promotions.size() );
        for ( Promotion promotion : promotions ) {
            list.add( toPromotionResponse( promotion ) );
        }

        return list;
    }

    @Override
    public Promotion toEntity(PromotionRequest request) {
        if ( request == null ) {
            return null;
        }

        Promotion promotion = new Promotion();

        promotion.setPromotionID( request.getPromotionID() );
        promotion.setCode( request.getCode() );
        promotion.setDescription( request.getDescription() );
        promotion.setDiscount( request.getDiscount() );
        promotion.setStartDate( request.getStartDate() );
        promotion.setEndDate( request.getEndDate() );
        promotion.setQuantity( request.getQuantity() );

        return promotion;
    }
}
