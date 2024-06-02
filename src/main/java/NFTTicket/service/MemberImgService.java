package NFTTicket.service;

import NFTTicket.entity.MemberImg;
import NFTTicket.repository.MemberImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberImgService {
    @Value("${memberImgLocation}")  // application.properties에 써져있는 memberImgLocation
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
            imgURL = "images/member/" + imgName;
        }

        // 이미지 정보 저장
        memberImg.updateMemberImg(oriImgName, imgName, imgURL);
        memberImgRepository.save(memberImg);
    }
}
