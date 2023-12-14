package com.example.demo.constant;
/**
 * com.example.demo.constant.TrustScoreTypeIdentifier
 *
 * <p>신뢰점수타입 분류에 대한 상수 클래스 신뢰점수 증감 메서드 호출을 위한 파라미터 (static import 해서 사용할 것을 권장)
 *
 * @author joo
 * @version 1.0
 * @since 2023/11/27
 *     <pre>
 * << 개정 이력(Modification History) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------    ---------------------------
 *  2023/11/27     joo        최초 생성
 * </pre>
 */
public class TrustScoreTypeIdentifier {
    /** 업무 완수에 대한 상수 */
    public static final Long WORK_COMPLETE = 1L;
    /** 업무 미흡 및 미완에 대한 상수 */
    public static final Long WORK_INCOMPLETE = 2L;
    /** 신규 멤버에 대한 상수 */
    public static final Long NEW_MEMBER = 3L;
    /** 프로젝트 탈퇴에 대한 상수 */
    public static final Long SELF_WITHDRAWAL = 4L;
    /** 프로젝트 강제 탈퇴에 대한 상수 */
    public static final Long FORCE_WITHDRAWAL = 5L;
    /** 업무 지연에 대한 상수 */
    public static final Long LATE_WORK = 22L;
}
