package com.example.travelweb.conventer;

import com.example.travelweb.dto.request.ImageCreation;
import com.example.travelweb.dto.response.ImageResponse;
import com.example.travelweb.entity.Image;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-02T19:48:28+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class ImageMapperImpl implements ImageMapper {

    @Override
    public ImageResponse toImageResponseDTO(Image image) {
        if ( image == null ) {
            return null;
        }

        ImageResponse imageResponse = new ImageResponse();

        imageResponse.setImageURL( image.getImageURL() );
        imageResponse.setDescription( image.getDescription() );
        imageResponse.setImageID( image.getImageID() );

        return imageResponse;
    }

    @Override
    public List<ImageResponse> toImageResponseDTOList(List<Image> images) {
        if ( images == null ) {
            return null;
        }

        List<ImageResponse> list = new ArrayList<ImageResponse>( images.size() );
        for ( Image image : images ) {
            list.add( toImageResponseDTO( image ) );
        }

        return list;
    }

    @Override
    public Image toEntity(ImageCreation imageRequestDTO) {
        if ( imageRequestDTO == null ) {
            return null;
        }

        Image image = new Image();

        image.setImageURL( imageRequestDTO.getImageURL() );
        image.setDescription( imageRequestDTO.getDescription() );

        return image;
    }

    @Override
    public List<Image> toEntityList(List<ImageCreation> imageRequestDTOs) {
        if ( imageRequestDTOs == null ) {
            return null;
        }

        List<Image> list = new ArrayList<Image>( imageRequestDTOs.size() );
        for ( ImageCreation imageCreation : imageRequestDTOs ) {
            list.add( toEntity( imageCreation ) );
        }

        return list;
    }
}
