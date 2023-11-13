package com.example.cookblog.mapper;

import com.example.cookblog.dto.resources.ImageResource;
import com.example.cookblog.model.Image;

public interface ImageMapper {

    ImageResource mapImageToImageResource(Image image);

    Image mapImageResourceToImage(ImageResource imageResource);

}
