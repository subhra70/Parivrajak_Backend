package com.subhrashaw.ParivrajakBackend.service;

import com.subhrashaw.ParivrajakBackend.dao.HotelRepo;
import com.subhrashaw.ParivrajakBackend.model.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class HotelService {
    @Autowired
    private HotelRepo hotelRepo;
    public Hotel addHotel(MultipartFile image1, MultipartFile image2, MultipartFile image3, MultipartFile image4) throws IOException {
        Hotel hotelObj=new Hotel();
        hotelObj.setRatings(0);
        hotelObj.setImgData1(image1.getBytes());
        hotelObj.setImgData2(image2.getBytes());
        hotelObj.setImgData3(image3.getBytes());
        hotelObj.setImgData4(image4.getBytes());
        hotelObj.setImgType1(image1.getOriginalFilename());
        hotelObj.setImgName2(image2.getOriginalFilename());
        hotelObj.setImgName3(image3.getOriginalFilename());
        hotelObj.setImgName4(image4.getOriginalFilename());
        hotelObj.setImgType1(image1.getContentType());
        hotelObj.setImgType2(image2.getContentType());
        hotelObj.setImgType3(image3.getContentType());
        hotelObj.setImgType4(image4.getContentType());
        return hotelRepo.save(hotelObj);
    }

    public Optional<Hotel> findById(int hotelId) {
        return hotelRepo.findById(hotelId);
    }
}
