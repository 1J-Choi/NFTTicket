package NFTTicket.repository;

import NFTTicket.entity.Member;
import NFTTicket.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import NFTTicket.dto.MypageShowDto;
import NFTTicket.dto.QMypageShowDto;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private JPAQueryFactory queryFactory;

    @Override
    public MypageShowDto findMypageShowDtoByEmail(String email) {
        QMember member = QMember.member;

        return queryFactory.select(new QMypageShowDto(
                member.nick,
                member.email,
                member.metaAddress))
                .from(member)
                .where(member.email.eq(email))
                .fetchOne();
    }
}