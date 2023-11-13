package com.example.cookblog.mapper.internal;

import com.example.cookblog.dto.resources.ImageResource;
import com.example.cookblog.mapper.ImageMapper;
import com.example.cookblog.model.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ImageMapperService implements ImageMapper {

    @Override
    public ImageResource mapImageToImageResource(Image image) {
        return ImageResource.builder()
                .id(image.getId())
                .path(image.getPath())
                .build();
    }

    @Override
    public Image mapImageResourceToImage(ImageResource imageResource) {
        return Image.builder()
                .path(imageResource.path())
                .build();
    }

}
