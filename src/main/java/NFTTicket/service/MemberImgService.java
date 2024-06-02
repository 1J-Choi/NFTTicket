package NFTTicket.service;

import NFTTicket.entity.MemberImg;
import NFTTicket.repository.MemberImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberImgService {
    @Value("${itemImgLocation}")  // application.properties에 써져있는 memberImgLocation
    private String memberImgLocation;

    private final MemberImgRepository memberImgRepository;
    private final FileService fileService;

    public void saveMemberImg(MemberImg memberImg, MultipartFile memberImgFile) throws Exception {
        String oriImgName = memberImgFile.getOriginalFilename();
        String imgName = "";
        String imgURL = "";

        // 파일 업로드
        if (oriImgName != null && !oriImgName.isEmpty()) {
            imgName = fileService.uploadFile(memberImgLocation, oriImgName, memberImgFile.getBytes());
            imgURL = "/images/item/" + imgName;
        }

        // 이미지 정보 저장
        memberImg.updateMemberImg(oriImgName, imgName, imgURL);
        memberImgRepository.save(memberImg);
    }

    public void updateMemberImg(Long memberImgId, MultipartFile memberImgFile) throws Exception{
        if(!memberImgFile.isEmpty()){
            MemberImg savedMemberImg = memberImgRepository.findById(memberImgId)
                    .orElseThrow(EntityNotFoundException::new);
            if(!StringUtils.isEmpty(savedMemberImg.getImgName())){
                fileService.deleteFile(memberImgLocation+"/"+savedMemberImg.getImgName());
            }
            String oriImgName = memberImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(memberImgLocation, oriImgName, memberImgFile.getBytes()); // 파일 업로드
            String imgUrl = "/images/item/"+imgName;

            savedMemberImg.updateMemberImg(oriImgName, imgName, imgUrl);
        }
    }
}
