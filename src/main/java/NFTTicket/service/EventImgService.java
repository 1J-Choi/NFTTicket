package NFTTicket.service;

import NFTTicket.entity.EventImg;
import NFTTicket.repository.EventImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class EventImgService {
    @Value("${itemImgLocation}") // application.properties에 써져있는 itemImgLocation
    private String eventImgLocation;

    private final EventImgRepository eventImgRepository;
    private final FileService fileService;

    public void saveEventImg(EventImg eventImg, MultipartFile eventImgFile) throws Exception{
        String oriImgName = eventImgFile.getOriginalFilename(); // 오리지날 이미지 경로
        String imgName="";
        String imgURL="";
        System.out.println(oriImgName);
        // 파일 업로드
        if (!StringUtils.isEmpty(oriImgName)){ // oriImgName 문자열로 비어 있지 않으면 실행
            imgName = fileService.uploadFile(eventImgLocation, oriImgName, eventImgFile.getBytes());
            System.out.println(imgName);
            imgURL = "/images/item/"+imgName;
        }
        // 이벤트 이미지 정보 저장
        // oriImgName : 이벤트 이미지 파일 원래 이름
        // imgName : 실제 로컬에 저장된 이벤트 이미지 파일의 이름
        // imgURL : 로컬에 저장된 이벤트 이미지 파일을 불러오는 경로
        eventImg.updateEventImg(oriImgName, imgName, imgURL);
        eventImgRepository.save(eventImg);
    }
}
