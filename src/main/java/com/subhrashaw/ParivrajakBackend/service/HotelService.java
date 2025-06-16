package com.subhrashaw.ParivrajakBackend.service;

import com.subhrashaw.ParivrajakBackend.dao.HotelRepo;
import com.subhrashaw.ParivrajakBackend.model.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public Hotel update(MultipartFile image1,MultipartFile image2,MultipartFile image3,MultipartFile image4, int hotelId) throws IOException {
        Hotel details=hotelRepo.findById(hotelId).orElse(null);
        if(details!=null)
        {
            if(image1!=null) {
                details.setImgData1(image1.getBytes());
                details.setImgName1(image1.getOriginalFilename());
                details.setImgType1(image1.getContentType());
            }
            if(image2!=null) {
                details.setImgData2(image2.getBytes());
                details.setImgName2(image2.getOriginalFilename());
                details.setImgType2(image2.getContentType());
            }
            if(image3!=null)
            {
            details.setImgData3(image3.getBytes());
            details.setImgName3(image3.getOriginalFilename());
            details.setImgType3(image3.getContentType());
            }
            if(image4!=null) {
                details.setImgData4(image4.getBytes());
                details.setImgName4(image4.getOriginalFilename());
                details.setImgType4(image4.getContentType());
            }
            return hotelRepo.save(details);
        }
        return details;
    }
}
